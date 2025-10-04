import java.io.*;
import java.util.*;
import matrixArithmetic.*;

// Author: Andrew Martin
// For Algorithms in Bioinformatics
// Version 1.3
//   - Added runtime comparison between Strassen’s method and standard multiplication
//   - 1.2: Added readMatrix and displayMatrix
//   - 1.1: Added file I/O
// JDK Version: 13.0.2
//
// This program multiplies two matrices using both Strassen’s algorithm
// and the standard matrix multiplication approach, and compares runtime.

public class MatrixMultiplication {

/**
 * Strassen’s algorithm for multiplying two square matrices.
 * Works only on matrices whose dimensions are a power of two.
 *
 * @param A first matrix
 * @param B second matrix
 * @return resulting matrix product
 */
public static int[][] strassenMultiply(int[][] A, int[][] B) {
    int n = A.length;
    int[][] R = new int[n][n];

    // Base case: 1x1 matrix
    if (n == 1) {
        R = MatrixArithmetic.standardMultiply(A, B);
    } else {
        // Initialize submatrices (quadrants)
        int[][] A11 = new int[n/2][n/2];
        int[][] A12 = new int[n/2][n/2];
        int[][] A21 = new int[n/2][n/2];
        int[][] A22 = new int[n/2][n/2];
        int[][] B11 = new int[n/2][n/2];
        int[][] B12 = new int[n/2][n/2];
        int[][] B21 = new int[n/2][n/2];
        int[][] B22 = new int[n/2][n/2];

        // Partition matrices into quadrants
        MatrixArithmetic.partition(A, A11, 0, 0);
        MatrixArithmetic.partition(A, A12, 0, n/2);
        MatrixArithmetic.partition(A, A21, n/2, 0);
        MatrixArithmetic.partition(A, A22, n/2, n/2);
        MatrixArithmetic.partition(B, B11, 0, 0);
        MatrixArithmetic.partition(B, B12, 0, n/2);
        MatrixArithmetic.partition(B, B21, n/2, 0);
        MatrixArithmetic.partition(B, B22, n/2, n/2);

        // Strassen’s seven recursive multiplications
        int[][] p1 = strassenMultiply(MatrixArithmetic.add(A11, A22), MatrixArithmetic.add(B11, B22));
        int[][] p2 = strassenMultiply(MatrixArithmetic.add(A21, A22), B11);
        int[][] p3 = strassenMultiply(A11, MatrixArithmetic.subtract(B12, B22));
        int[][] p4 = strassenMultiply(A22, MatrixArithmetic.subtract(B21, B11));
        int[][] p5 = strassenMultiply(MatrixArithmetic.add(A11, A12), B22);
        int[][] p6 = strassenMultiply(MatrixArithmetic.subtract(A21, A11), MatrixArithmetic.add(B11, B12));
        int[][] p7 = strassenMultiply(MatrixArithmetic.subtract(A12, A22), MatrixArithmetic.add(B21, B22));

        // Combine results into quadrants of final matrix
        int[][] C11 = MatrixArithmetic.add(MatrixArithmetic.subtract(MatrixArithmetic.add(p1, p4), p5), p7);
        int[][] C12 = MatrixArithmetic.add(p3, p5);
        int[][] C21 = MatrixArithmetic.add(p2, p4);
        int[][] C22 = MatrixArithmetic.add(MatrixArithmetic.subtract(MatrixArithmetic.add(p1, p3), p2), p6);

        // Join quadrants back into full matrix
        MatrixArithmetic.join(C11, R, 0, 0);
        MatrixArithmetic.join(C12, R, 0, n/2);
        MatrixArithmetic.join(C21, R, n/2, 0);
        MatrixArithmetic.join(C22, R, n/2, n/2);
    }

    return R;
}

/** 
 * Reads values into a matrix from a Scanner object. 
 */
private static void readMatrix(Scanner scanner, int[][] matrix, int n) {
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            if (scanner.hasNextInt()) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }
}

/**
 * Prints a matrix to stdout.
 */
private static void displayMatrix(int[][] matrix) {
    for (int[] row : matrix) {
        for (int val : row) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}

/**
 * Rounds up an integer to the nearest power of two.
 */
private static int nextPowerOfTwo(int n) {
    double exp = Math.ceil(Math.log(n) / Math.log(2));
    return (int) Math.pow(2.0, exp);
}

/**
 * Main method: Reads matrices from a file, applies both 
 * Strassen’s and standard multiplication, and compares runtime.
 */
public static void main(String[] args) {
    Scanner scanner = null;
    PrintStream output = null;
    Scanner userInput = new Scanner(System.in);

    System.out.print("Enter input file name: ");
    try {
        scanner = new Scanner(new File(userInput.next()));
    } catch (Exception e) {
        System.out.println("Error reading input file: " + e);
    }

    System.out.print("Enter output file name: ");
    try {
        output = new PrintStream(new File(userInput.next()));
    } catch (IOException e) {
        System.out.println("Error creating output file: " + e);
    }

    System.setOut(output);

    MatrixMultiplication m = new MatrixMultiplication();

    // Process all matrix pairs from input
    while (scanner.hasNextInt()) {
        int n = scanner.nextInt();
        if (n == 0) continue;

        int p = nextPowerOfTwo(n);
        int[][] A = new int[p][p];
        int[][] B = new int[p][p];

        readMatrix(scanner, A, n);
        readMatrix(scanner, B, n);

        // Measure Strassen runtime
        long startStrassen = System.nanoTime();
        int[][] C = m.strassenMultiply(A, B);
        long endStrassen = System.nanoTime();
        long timeStrassen = endStrassen - startStrassen;

        // Measure standard runtime
        long startStandard = System.nanoTime();
        int[][] D = MatrixArithmetic.standardMultiply(A, B);
        long endStandard = System.nanoTime();
        long timeStandard = endStandard - startStandard;

        // Print results
        System.out.println("Order of Matrices: " + n);
        System.out.println("Input:");
        System.out.println("Matrix A:");
        displayMatrix(A);
        System.out.println("Matrix B:");
        displayMatrix(B);

        System.out.println("\nOutput:");
        System.out.println("Strassen’s Method:");
        displayMatrix(C);
        System.out.println("Runtime (ns): " + timeStrassen);

        System.out.println("\nStandard Matrix Multiplication:");
        displayMatrix(D);
        System.out.println("Runtime (ns): " + timeStandard);
        System.out.println();
    }

    output.close();
}
}
