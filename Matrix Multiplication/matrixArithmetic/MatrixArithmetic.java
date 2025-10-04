package matrixArithmetic;

/**
 * MatrixArithmetic
 * 
 * Basic arithmetic operations for square matrices.
 * Includes addition, subtraction, partitioning, joining, 
 * and standard (O(n^3)) matrix multiplication.
 * 
 * @author Andrew Martin
 * @version 1.1
 * For Algorithms in Bioinformatics
 * 
 * Updates:
 *   - Added standardMultiply() to the package
 */
public class MatrixArithmetic {

    /**
     * Subtracts matrix B from matrix A (C = A - B).
     *
     * @param A first input matrix
     * @param B second input matrix
     * @return resulting matrix C after subtraction
     */
    public static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

    /**
     * Adds two matrices (C = A + B).
     *
     * @param A first input matrix
     * @param B second input matrix
     * @return resulting matrix C after addition
     */
    public static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    /**
     * Partitions a submatrix from A into B.
     * Works best with matrix sizes that are powers of 2.
     *
     * @param A the larger source matrix
     * @param B the smaller matrix to fill with values
     * @param n row offset in A
     * @param m column offset in A
     */
    public static void partition(int[][] A, int[][] B, int n, int m) {
        for (int i = 0, j = n; i < B.length; i++, j++) {
            for (int k = 0, l = m; k < B.length; k++, l++) {
                B[i][k] = A[j][l];
            }
        }
    }

    /**
     * Joins matrix A into matrix B at the specified offset.
     *
     * @param A the smaller matrix to insert
     * @param B the larger matrix to update
     * @param n row offset in B
     * @param m column offset in B
     */
    public static void join(int[][] A, int[][] B, int n, int m) {
        for (int i = 0, j = n; i < A.length; i++, j++) {
            for (int k = 0, l = m; k < A.length; k++, l++) {
                B[j][l] = A[i][k];
            }
        }
    }

    /**
     * Performs standard O(n^3) matrix multiplication.
     *
     * @param A first input matrix
     * @param B second input matrix
     * @return resulting matrix C = A × B
     */
    public static int[][] standardMultiply(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        // Standard triple-nested loop for multiplication
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