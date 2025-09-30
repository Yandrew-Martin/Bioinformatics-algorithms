README

// @author Andrew Martin 
// for Algorithms in Bioinformatics
// @JDK Version 13.0.2
// EDITOR: SublimeText3 and Java code was ran in console
// This project hashes values from an input file and outputs the corresponding 
// hash tables. Contains three methods of collision handling, Linear, Quadratic, 
// and Chaining. 
// REQUIREMENTS: Minumum RAM, a working computer, JDK 13.0.2+, minimal storage capacity for output files

INPUT files should just be a set of numbers on each new line.


EXAMPLE INPUT:

12501
84763
22599
02698
55555
72501
.
.
.

Command lines used to generate files output files:

>java hash
Enter input file name: LabHashingInput.txt
Enter output file name: LabHashingOutput_mod120_myHash.txt
Enter bucket size: 1
Enter division modulo: 120

>java hash
Enter input file name: LabHashingInput.txt
Enter output file name: LabHashingOutput_mod113_myHash.txt
Enter bucket size: 1
Enter division modulo: 113

>java hash
Enter input file name: LabHashingInput.txt
Enter output file name: LabHashingOutput_mod41_bucket3_myHash.txt
Enter bucket size: 3
Enter division modulo: 41

>java hash
Enter input file name: testhash_120Entries.txt
Enter output file name: testhash_120Entries_output.txt
Enter bucket size: 1
Enter division modulo: 120

>java hash
Enter input file name: testhash_150entries.txt
Enter output file name: testhash_150entries_output.txt
Enter bucket size: 1
Enter division modulo: 120

>java hash
Enter input file name: testhash_360entries.txt
Enter output file name: testhash_360entries_output.txt
Enter bucket size: 3
Enter division modulo: 120

>java hash
Enter input file name: testhash_360entries.txt
Enter output file name: testhash_360entries_360slots_bucket3_output.txt
Enter bucket size: 3
Enter division modulo: 120

EXAMPLE OUTPUT:

LINEAR PROBING HASH TABLE

Index	Value
0	19439 
1	1081 
2	39842 
3	11282 
.
.
.
115	9954 
116	19196 
117	36596 
118	38397 
119	44516 


 Slots used:		120
 Collisions occured:	361 
 Values not stored:	30
 Load factor:		1.0