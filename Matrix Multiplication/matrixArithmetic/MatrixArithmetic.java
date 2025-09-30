package matrixArithmetic;

// @author Andrew Martin
// for Algorithms in Bioinformatics
// version 1.1
// -- added standardMultiply() to the package
// 
// Arithmetic class for basic matrix operations

public class MatrixArithmetic
{
    // subtract values of matrices A and B and place into new matrix C
    // return the new matrix C
	public static int[][] subtract(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }
    // add values of matrices A and B and place into new matrix C
    // return the new matrix C
    // @input matrix objects A and B
    // @output resulting matrix of matrix addition operation
    public static int[][] add(int[][] A, int[][] B)
    {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }
    // Partition matrix into two matrices, A and B
    // NOTE: works best with powers of 2
    // @input matrix objects A and B, start and ending size for partitioned matrix
    // @output - none - fills matrix B with partitioned values
    public static void partition(int[][] A, int[][] B, int n, int m) 
    {
        for(int i = 0, j = n; i < B.length; i++, j++)
            for(int k = 0, l = m; k < B.length; k++, l++)
                B[i][k] = A[j][l];
    }
    // join matrix A with matrix B
    // @input matrix objects A and B, start and ending values where the joined
    // matrix inputs its values into the final matrix
    // @output - none - fills final matrix 
    public static void join(int[][] A, int[][] B, int n, int m) 
    {
        for(int i = 0, j = n; i < A.length; i++, j++)
            for(int k = 0, l = m; k < A.length; k++, l++)
                B[j][l] = A[i][k];
    }
    // Standard matrix Multiplication using three for loops
    // @input matrix objects A and B
    // @output returns product of matrix A and B
    public static int[][] standardMultiply(int[][] A, int[][] B)
    {
        int n = A.length;

        // initialise new matrix C
        int[][] C = new int[n][n];

        // O(n^3) standard matrix multiplication
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < n; j++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
}