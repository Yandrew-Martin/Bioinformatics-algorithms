import java.io.*;
import java.util.*;
import matrixArithmetic.*;

// @author Andrew Martin 
// for Algorithms in Bioinformatics
// @version 1.2
// -- added readMatrix and displayMatrix 
// -- 1.1 - added file I/O
// @JDK Version 13.0.2

// This algorithm multiples two matrices using the Strassen method alongside normal 
// matrix multiplication


public class MatrixMultiplication
{

    // strassenMultiply two NxN sized matrices 
    // STRASSENS METHOD - ONLY 7 MULTIPLICATIONS vs 8 (standard)
    // p1 = (A11 + A22)(B11 + B22)
    // p2 = (A21 + A22) B11
    // p3 = A11 (B12 - B22)
    // p4 = A22 (B21 - B11)
    // p5 = (A11 + A12) B22
    // p6 = (A21 - A11) (B11 + B12)
    // p7 = (A12 - A22) (B21 + B22)

    // C11 = p1 + p4 - p5 + p7
    // C12 = p3 + p5
    // C21 = p2 + p4
    // C22 = p1 - p2 + p3 + p6

    // @input two equal sized NxN matrices
    // @output returns the product of the two matrices multiplied
    public static int[][] strassenMultiply(int[][] A, int[][] B)
    {        
        int n = A.length;
        int[][] R = new int[n][n];
        // base case
        if (n == 1)
            R = MatrixArithmetic.standardMultiply(A, B);
        else
        {
            // initialize new partition matrices of size n/2 x n/2
            int[][] A11 = new int[n/2][n/2];
            int[][] A12 = new int[n/2][n/2];
            int[][] A21 = new int[n/2][n/2];
            int[][] A22 = new int[n/2][n/2];
            int[][] B11 = new int[n/2][n/2];
            int[][] B12 = new int[n/2][n/2];
            int[][] B21 = new int[n/2][n/2];
            int[][] B22 = new int[n/2][n/2];
 
            // Divide matrix A and B into 4 quarters 
            MatrixArithmetic.partition(A, A11, 0 , 0);
            MatrixArithmetic.partition(A, A12, 0 , n/2);
            MatrixArithmetic.partition(A, A21, n/2, 0);
            MatrixArithmetic.partition(A, A22, n/2, n/2);
            MatrixArithmetic.partition(B, B11, 0 , 0);
            MatrixArithmetic.partition(B, B12, 0 , n/2);
            MatrixArithmetic.partition(B, B21, n/2, 0);
            MatrixArithmetic.partition(B, B22, n/2, n/2);

            // Implement Strassen equations/recursion
            int [][] p1 = strassenMultiply(MatrixArithmetic.add(A11, A22), MatrixArithmetic.add(B11, B22));
            int [][] p2 = strassenMultiply(MatrixArithmetic.add(A21, A22), B11);
            int [][] p3 = strassenMultiply(A11, MatrixArithmetic.subtract(B12, B22));
            int [][] p4 = strassenMultiply(A22, MatrixArithmetic.subtract(B21, B11));
            int [][] p5 = strassenMultiply(MatrixArithmetic.add(A11, A12), B22);
            int [][] p6 = strassenMultiply(MatrixArithmetic.subtract(A21, A11), MatrixArithmetic.add(B11, B12));
            int [][] p7 = strassenMultiply(MatrixArithmetic.subtract(A12, A22), MatrixArithmetic.add(B21, B22));
            
            int [][] C11 = MatrixArithmetic.add(MatrixArithmetic.subtract(MatrixArithmetic.add(p1, p4), p5), p7);
            int [][] C12 = MatrixArithmetic.add(p3, p5);
            int [][] C21 = MatrixArithmetic.add(p2, p4);
            int [][] C22 = MatrixArithmetic.add(MatrixArithmetic.subtract(MatrixArithmetic.add(p1, p3), p2), p6);
 
            // join 4 quarters into one matrix 
            MatrixArithmetic.join(C11, R, 0 , 0);
            MatrixArithmetic.join(C12, R, 0 , n/2);
            MatrixArithmetic.join(C21, R, n/2, 0);
            MatrixArithmetic.join(C22, R, n/2, n/2);
        }
        // return resulting matrix    
        return R;
    }

    // Reads A file and inputs integers into each row/column
    // Since the matrices are extended to the next power of 2, 
    // This uses the value of the order of the matrix read instead
    // Of the length of the "padded" matrix
    // @input scanner object, matrix object, order of the matrix
    // @output - none - fills in the empty matrix with input values from scanner
    private static void readMatrix(Scanner scanner, int[][] matrix, int n) {
        for (int i = 0; i < n; ++i) 
            for (int j = 0; j < n; ++j) 
                if (scanner.hasNextInt()) 
                    matrix[i][j] = scanner.nextInt();        
    }

    // Simple function for printing a matrix
    // @input matrix object
    // @output - none - prints matrix
    private static void displayMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix[i].length; j++) 
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    // Round up to the next power of 2
    // @input order of the input matrices
    // @output returns the next power of 2
    private static int nextPowerOfTwo(int n) 
    {
        double nextPower = Math.ceil(Math.log(n)/Math.log(2));
        double p = Math.pow(2.0, nextPower);
        return (int) p;
    }

    // Reads input file name given by user
    // File must include the following format
    // Order of matrix
    // Matrix A of size(order)
    // Matrix B of size(order)
    // EXAMPLE:
    // 2
    // 1 2   <--Matrix one
    // 3 4
    // 5 6   <--Matrix two
    // 7 8

    public static void main (String[] args)
    {
        Scanner scanner = null;
        PrintStream output = null;
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter input file name: ");
        try 
        {
            scanner = new Scanner(new File(userInput.next()));
        } 
        catch (Exception e) 
        {
            System.out.println(e.toString());
        }
        System.out.print("Enter output file name: ");
        try 
        {
            output = new PrintStream(new File(userInput.next()));
        } 
        catch (IOException e) 
        {
            System.out.println(e.toString());
        }
        MatrixMultiplication m = new MatrixMultiplication();
        System.setOut(output);
        // iterate through each Order/Matrix combination 
        while(scanner.hasNextInt())
        {
            // initialize n, p, A, and B; matrices must be power of 2 for Strassen method to work properly
            int n = scanner.nextInt();
            if (n == 0)
                continue;
            else
            {
                int p = nextPowerOfTwo(n);
                int[][] A = new int[p][p];
                int[][] B = new int[p][p];

                readMatrix(scanner, A, n);
                readMatrix(scanner, B, n);

                int[][] C = m.strassenMultiply(A, B);
                int[][] D = MatrixArithmetic.standardMultiply(A, B);
                System.out.println("Order of Matrices:" + n);
                System.out.println("Input:");
                System.out.println("Matrix A");
                displayMatrix(A);
                System.out.println("Matrix B");
                displayMatrix(B);
                System.out.println();
                System.out.println("Output:");
                System.out.println("Strassen's Method:");
                displayMatrix(C);
                System.out.println();
                System.out.println("standard matrix multiplication:");
                displayMatrix(D);
                System.out.println();
            }
        }
        output.close();
    }
}