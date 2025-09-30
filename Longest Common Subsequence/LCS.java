import java.io.*;
import java.util.*;

// @author Andrew Martin 
// for Algorithms in Bioinformatics
// @JDK Version 13.0.2

// This class is designed to read an input of strings by line
// and find the Longest Common Subsequence between two strings, comparing
// each combination of strings.
// The LCS is output, along with its length and the LCS-length table
// created in order to find the LCS.
// Based off the LCS algorithm found in Introductions to Algorithms, 3rd Ed.

public class LCS
{ 
    // The following function constructs a 2D int array that is necessary
    // for determining the Longest Common Subsequence between two 
    // strings X and Y. This function returns the 2D array.
    //
    // requires two string inputs of X and Y.

    public static int[][] lcsTable(String X, String Y) 
    { 
        // assign local variables and create new 2D array
        int m = X.length();
        int n = Y.length();
        int[][] C = new int[m+1][n+1];

        // set the first row and column to 0
        for (int k = 1; k < m; k++)
            C[k][0] = 0;
        for (int l = 0; l < n; l++)
            C[0][l] = 0;
        // fill table
        for (int i = 1; i <= m; i++) 
        { 
            for (int j = 1; j <= n; j++) 
            { 
                // if characters match, add 1 from the diagonal entry
                if (X.charAt(i-1) == Y.charAt(j-1)) 
                    C[i][j] = C[i-1][j-1] + 1;
                // take which value is more from above or the left
                // take from above if they are equal
                else if (C[i-1][j] >= C[i][j-1])
                    C[i][j] = C[i-1][j];
                else
                    C[i][j] = C[i][j-1];
            }
            
        }
        return C;
    }
    // The following function takes the input 2D array and formats
    // its contents into a string that is returned. The format lines
    // the rows and columns with the appropriate characters from X and
    // Y.
    //
    // requires a 2D array input and the two strings required to construct
    // the 2D array: LCS. 
    // 
    // ##TEMPLATE##
    //
    // LCS-TABLE:
    //    y  N  N  N  N  N  N  N  N  N
    // x  0  0  0  0  0  0  0  0  0  0  
    // N  0  .  .  .  .  .  .  .  .  . 
    // N  0

    public static String lcsTablePrint(int[][] table, String X, String Y)
    {
        int[][] C = table;
        int m = X.length();
        int n = Y.length();
        String result = "LCS-TABLE:\n" + 
        String.format("%-2s ", "") + 
        String.format("%-2s ", "y");
        for (int k = 0; k < n-1; k++)
            result += String.format("%-2s ", Y.charAt(k));
        result += "\n";
        for (int i = 0; i <= m; i++)
        {
            if (i == 0)
                result += String.format("%-2s ", "x");
            else 
                result += String.format("%-2s ", X.charAt(i-1));
            for (int j = 0; j < n; j++)
            {
                result += String.format("%-2s ", C[i][j]);
            }
            result += "\n"; 
        }       
        return result;
    }
    // The following function starts at the bottom-right most corner of
    // the LCS table and finds the longest common subsequence and stores it
    // in the array 'lcs'. The function returns the input and output 
    // (LCS and LCS length) in a specific string format shown below.
    // 
    // requires a 2D array input and the two strings required to construct
    // the 2D array: LCS. 
    // 
    // ##TEMPLATE##
    //
    // INPUT:
    // Sequence X: NNNNNNNNNNNN
    // Sequence Y: NNNNNNNNNNNNNNNN
    // OUTPUT:
    // Longest Common Subsequence:  NNNNNNNNNNNNNN
    // L.C.S. Size: 14

    public static String lcsPrint(int[][] table, String X, String Y)
    {
        int[][] C = table;
        int m = X.length();
        int n = Y.length();
        int index = C[m][n];
        int length = index; 
   
        // array to store the solution
        char[] lcs = new char[index+1]; 

        // starting at the bottom-right of C
        int i = m;
        int j = n; 
        while (i > 0 && j > 0 && index >= 0) 
        { 
            // check if the characters are equal
            if (X.charAt(i-1) == Y.charAt(j-1)) 
            { 
                // move diagonally
                lcs[index] = X.charAt(i-1);
                i--;  
                j--;  
                index--;      
            } 
   
            // move up on C 
            else if (C[i-1][j] >= C[i][j-1]) 
                i--;
            // move left on C
            else
                j--; 
        } 
   
        // Print INPUT and OUTPUT format
        String result = "INPUT: \nSequence X: " + 
        X + "\nSequence Y: " + 
        Y + "\n\nOUTPUT:\nLongest Common Subsequence: ";
        for(int k = 0; k <= length; k++) 
            result += lcs[k];
        result += "\nL.C.S. Size: " + length + "\n";
        return result; 
    }  
    // main

    public static void main (String[] args)  
    { 
        Scanner userInput = new Scanner(System.in);
        // put input lines into a list
        List<String> sequences = new ArrayList<String>();
        try 
        {
            System.out.print("Enter input file name: ");
            BufferedReader input = new BufferedReader(new 
            FileReader(userInput.next()));
            while(input.ready())
            {
                sequences.add(input.readLine());
            }
            input.close();
        } catch (IOException exception) {
            System.out.println("Error: " + exception);
            System.out.println("Please make sure the file exists");
            System.exit(1);
        }
        try {
            System.out.print("Enter output file name: ");
            PrintWriter output = new PrintWriter(new
            File(userInput.next()), "UTF16");
            for (int i=0; i < sequences.size(); i++) 
            { 
                for (int j = i+1; j < sequences.size(); j++) 
                { 
                    String X = sequences.get(i);
                    String Y = sequences.get(j);
                    int[][] C = lcsTable(X, Y);
                    String result = lcsPrint(C, X, Y);
                    String table = lcsTablePrint(C, X, Y);
                    System.out.println(result);
                    System.out.println(table);
                    output.println(result);
                    output.println(table);
                } 
            }
            output.close();
        } catch(IOException exception) {
            System.out.print("Error: " + exception);
            System.exit(1);
        }
    } 
} 