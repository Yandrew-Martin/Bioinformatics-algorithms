LONGEST COMMON SUBSEQUENCE (LCS) README

// @author Andrew Martin 
// for Algorithms in Bioinformatics
// @JDK Version 13.0.2
// This class is designed to read an input of strings by line
// and find the Longest Common Subsequence between two strings, comparing
// each combination of strings.
// The LCS is output, along with its length and the LCS-length table
// created in order to find the LCS.
// Based off the LCS algorithm found in Introductions to Algorithms, 3rd Ed.
// REQUIREMENTS: Minumum RAM, a working computer, JDK 13.0.2+, minimal storage capacity for output files

INPUT files will take each line as a string.

EXAMPLE INPUT:

ACCGGTCGACTGCGCGGAAGCCGGCCGAA
GTCGTTCGGAATGCCGTTGCTCTGTAAA
ATTGCATTGCATGGGCGCGATGCATTTGGTTAATTCCTCG
CTTGCTTAAATGTGCA

72316600856636193379355707512399589781743534045
83055422150470145420084622597329457058768352588

.;>><L::L#@!#!$%M^%&*&***:@#$%#^@P%@%
}}@$%#%<<:<?""@:@$:@L%L%^SDF%^{#P#$L@:

Command line example used to run LCS and generate outputs:

>java LCS
Enter input file name: HemoglobinExon_Tortoise_HomoSapien.txt
Enter output file name: HemoglobinExon_Tortoise_HomoSapien_output.txt

>java LCS
Enter input file name: Hb2_Pan_Macaca_Ovis_HomoSapien.txt
Enter output file name: Hb2_Pan_Macaca_Ovis_HomoSapien_output.txt

EXAMPLE OUTPUT:

INPUT:
Sequence X: ACTCTTCTGGTCCCCACAGACTCAGAGAGAACCCACCATGGTGCTGTCTCCTGCCGACAAGACCAACGTCAAGGCCGCCT
Sequence Y: ACTCTTCTGGTCCCCACAGACTCAGAAAGAACCCACCATGGTGCTGTCTCCTGCCGACAAGACCAACGTCAAGGCCGCCT

OUTPUT:
Longest Common Subsequence:  ACTCTTCTGGTCCCCACAGACTCAGAAGAACCCACCATGGTGCTGTCTCCTGCCGACAAGACCAACGTCAAGGCCGCCT
L.C.S. Size: 79

LCS-TABLE:
   y  A  C  T  C  T  T  C  T  G  G  T  ...
x  0  0  0  0  0  0  0  0  0  0  0  0  
A  0  1  1  1  1  1  1  1  1  1  1  1   
C  0  1  2  2  2  2  2  2  2  2  2  2  
.  					
.
.