import java.lang.Math;
import java.io.*;
import java.util.*;

// @author Andrew Martin 
// for Algorithms in Bioinformatics
// @JDK Version 13.0.2

// This class hashes values from an input file and outputs the corresponding 
// hash table. Contains three methods of collision handling, Linear, Quadratic, 
// and Chaining. 
public class Hash {  

    // Main function: reads input given by user and stores the values accordingly.
    // Reads the provided file and outputs five separate hash tables, three using
    // modular hashing, two using 
    public static void main(String[] args) {
        
        // initialize scanners for user input and printstream for file output
        Scanner scanner = null;
        Scanner bucketSize = null;
        Scanner moduloInput = null;
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
            return;
        }
        System.out.print("Enter output file name: ");
        try 
        {
            output = new PrintStream(new File(userInput.next()));
        } 
        catch (IOException e) 
        {
            System.out.println(e.toString());
            return;
        }
        System.out.print("Enter bucket size: ");
        try 
        {
            bucketSize = new Scanner(userInput.next());
        } 
        catch (NumberFormatException nfe) 
        {
            System.out.println("Not an integer");
            return;
        }
        System.out.print("Enter division modulo: ");
        try 
        {
            moduloInput = new Scanner(userInput.next());
        } 
        catch (NumberFormatException nfe) 
        {
            System.out.println("Not an integer");
            return;
        }

        // assign scanned integer into respective objects
        Integer bucketDepth = bucketSize.nextInt();
        Integer modulo = moduloInput.nextInt();


        // Instantiate 6 different hash table classes
        // 3 for modular hashing, 3 for my hashing algorithm
        LinearHash linearHash = new LinearHash();
        LinearHash mylinearHash = new LinearHash();
        QuadraticHash quadraticHash = new QuadraticHash();
        QuadraticHash myquadraticHash = new QuadraticHash();
        ChainingHash chainingHash = new ChainingHash();
        ChainingHash mychainingHash = new ChainingHash();

        // set bucket size and modulo number based on input
        LinearHash.setBucketSize(bucketDepth);
        LinearHash.setModulo(modulo);
        QuadraticHash.setBucketSize(bucketDepth);
        QuadraticHash.setModulo(modulo);
        ChainingHash.setModulo(modulo);

        // check that an integer exists in file
        while(scanner.hasNextInt()) {
            String input = scanner.next();

            // insert input value into hash table
            // 0 signifies modular hashing
            // 1 signifies my hashing algorithm
            linearHash.insert(Integer.parseInt(input), 0);
            quadraticHash.insert(Integer.parseInt(input), 0);
            chainingHash.insert(Integer.parseInt(input), 0);
    
            mylinearHash.insert(Integer.parseInt(input), 1);
            myquadraticHash.insert(Integer.parseInt(input), 1);
            mychainingHash.insert(Integer.parseInt(input), 1);

        }
        // print each hash table and their corresponding information
        System.setOut(output);
        System.out.println(linearHash.print());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("My Algorithm:");
        System.out.println(mylinearHash.print());
        System.out.println("\n");
        System.out.println(quadraticHash.print());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("My Algorithm:");
        System.out.println(myquadraticHash.print());
        System.out.println("\n");
        System.out.println(chainingHash.print());
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("My Algorithm:");
        System.out.println(mychainingHash.print());
        
    }
    // create a bucket node as a linked list
    // Initialize next, data, and bucketcount
    // bucketcount is used to limit the size of the bucketNode
    public static class BucketNode {
            public BucketNode next = null;
            public Integer data = null;
            public Integer bucketcount = 0;

            //assign the input value to the data object
            public BucketNode(Integer value) {
                data = value;
            }
        }
    // Linear hash class:
    // Initialize a new table of size 120, bucketSize, and modulo objects
    // create an arraylist for overflow if there are too many input values
    // collision, count, and overflow track collisions, slots used, and amount of values left out.
    // bucketSize and modulo are set using setBucketSize and setModulo
    public static class LinearHash {
        //The table to store data
        BucketNode table[] = new BucketNode[360];
        ArrayList<Integer> over = new ArrayList<Integer>();             //overflow list
        public Integer collision = 0;
        public static Integer bucketSize = 1;
        public static Integer modulo = 0;
        public Integer count = 0;
        public Integer overflow = 0;

        // standard division modulo hashing using the value divided by the modulo number.
        private Integer hashFunction(Integer value) {
            return (value) % modulo;
        }

        //My own hash function:
        //Hashes based on the input of the digits - starts with the full integer
        //and removes the first digit until the resulting integer is less than 
        //the length of the table to ensure it will be mapped.
        private Integer myHashFunction(Integer value) {
            String input = String.valueOf(value);                           //sets input to string for splitting
            char[] digits = input.toCharArray();                            //splits value by digits into array
            Integer hash = 0;
            do {
                hash = Integer.parseInt(String.valueOf(digits));
                char[] temp = new char[digits.length - 1];                  //temp array 1 index smaller than digits
                System.arraycopy(digits, 1, temp, 0, digits.length -1);     //copies and removes first digit
                digits = temp;
            }
            while(hash >= table.length);
            return hash;
        }

        //allows for altering the bucketSize based on user input
        private static void setBucketSize(Integer value) {
            bucketSize = value;
        }

        //allows for altering the division modulo number based on user input
        private static void setModulo(Integer value) {
            modulo = value;
        }
        
        // returns the amount of slots used by the hashing method divided by the storage space.
        private Float loadFactor() {
            return (float) count / table.length;
        }
        
        // linear probing algorithm:
        // This function determines the hash value depending on whether modulo hashing
        // or my hashing algorithm was requested. 
        // scans for next available storage slot or bucket slot given by the linear probing equation
        // records collisions for analytic use
        // returns the first hash value where the index is null
        private Integer probe(Integer value, int a) {
            //Calculate the hash
            Integer hash = 0;
            if (a == 0){
                hash = hashFunction(value);         //modulo division hash
            }
            else {
                hash = myHashFunction(value);       //my hashing algorithm
            }
            //If we're deleting then we're looking for the table entry value exists in
            //Else we're looking for an empty space
            //While we haven't found what we're looking for
            while(table[hash] != null && table[hash].bucketcount >= bucketSize) {           //compares bucketCount variable to userinput bucketSize

                hash = (hash + 1) % table.length;       //liner hashing
                collision ++;                           //index was full or no space, therefore it is a collision
            }
            return hash;
        }
        
        // insert function:
        // uses the probe function to calculate the hash index
        // Inserts into the first available bucket slot
        // 

        public void insert(Integer value, int a) {
            //check if there is an empty storage slot, else add to the overflow list
            if (count > table.length) {
                over.add(value);
                overflow ++;
            }
            else {
                Integer hash = probe(value, a);
                //Insert the value at the location found
                if(table[hash] == null) {                   //slot is empty
                    table[hash] = new BucketNode(value);    //create new node
                    table[hash].bucketcount ++;             //add to bucket value, used later in probing
                    count ++;                               // One more slot is used
                } 
                else {
                    //The index isn't empty so we need to insert the value
                    //to the last node
                    BucketNode currentNode = table[hash];

                    while(currentNode.next != null) {       //scan for next open node position
                        currentNode = currentNode.next;     
                        collision ++;                       //record collision
                    }
                    currentNode.next = new BucketNode(value);       //sets 'next' parameter as new node of given value
                    table[hash].bucketcount++;                      //add to bucket variable of the hashed index
                    collision ++;                                   //record collision
                }
            }
        }
        
        //prints the hash table
        public String print() {
            String result = "LINEAR PROBING HASH TABLE\n\nIndex\tValue\n";
            for(Integer i = 0; i < table.length; i++) {
                result += i + "\t";
                if(table[i] != null) {
                    BucketNode currentNode = table[i];
                    while(currentNode != null) {
                        result += currentNode.data + " ";
                        currentNode = currentNode.next;
                    }
                }
                result += "\n";
            }
            return result + "\n\n Slots used:\t\t" + count + "\n Collisions occured:\t" + collision 
            + " \n Values not stored:\t" + overflow + "\n Load factor:\t\t" + loadFactor() + "\n";
        }
    }

    // Quadratic hash class:
    // Initialize a new table of size 120, bucketSize, and modulo objects
    // create an arraylist for overflow if there are too many input values
    // collision, count, and overflow track collisions, slots used, and amount of values left out.
    // bucketSize and modulo are set using setBucketSize and setModulo
    public static class QuadraticHash {
        //The table of buckets
        private BucketNode table[] = new BucketNode[360];
        ArrayList<Integer> over = new ArrayList<Integer>();         //overflow list
        public Integer collision = 0;
        public static Integer bucketSize = 1;
        public static Integer modulo = 0;
        public Integer count = 0;  
        public Integer overflow = 0;
             
        // standard division modulo hashing using the value divided by the modulo number.
        private Integer hashFunction(Integer value) {
            return (value) % modulo;
        }

        //My own hash function
        //Hashes based on the input of the digits - starts with the full integer
        //and removes the first digit until the resulting integer is less than 
        //the length of the table to ensure it will be mapped.
        private Integer myHashFunction(Integer value) {
            String input = String.valueOf(value);
            char[] digits = input.toCharArray();
            Integer hash = 0;
            do {
                hash = Integer.parseInt(String.valueOf(digits));
                char[] temp = new char[digits.length - 1];
                System.arraycopy(digits, 1, temp, 0, digits.length -1);
                digits = temp;
            }
            while(hash >= table.length);
            return hash;
        }
        
        //allows for altering the bucketSize based on user input
        private static void setBucketSize(Integer value) {
            bucketSize = value;
        }

        //allows for altering the division modulo number based on user input
        private static void setModulo(Integer value) {
            modulo = value;
        }
        
        // returns the amount of slots used by the hashing method divided by the storage space.
        private Float loadFactor() {
            return (float) count / table.length;
        }
        
        // quadtratic probing algorithm:
        // This function determines the hash value depending on whether modulo hashing
        // or my hashing algorithm was requested. 
        // scans for next available storage slot or bucket slot according to
        // the quadratic probing equation 
        // records collisions for analytic use
        // returns the first hash value where the index is null
        // c1 = c2 = 1/2
        private Integer probe(Integer value, int a) {
            //Calculate the hash
            double c1 = 0.5;
            double c2 = 0.5;
            Integer hash = 0;
            if (a == 0){
                hash = hashFunction(value);         //modulo division hash
            }
            else {
                hash = myHashFunction(value);       //my hashing algorithm
            }
            //Set the index = 0 for the quadratic traversal
            Integer index = 0;
            //Calculate the next key to check using quadratic probing c1 = c2 = 1/2
            //h(k, i) = (h(k) + i/2 + i^2 / 2) % (length of table)
            while(table[hash] != null && table[hash].bucketcount >= bucketSize) {
                hash = (int) ((hash + (float)index * c1 + (float)(index * index) * c2) % table.length);
                index ++;                           //increment index for quadratic algorithm
                collision ++;                       //record collision
            }
            return hash;
        }
        

        // insert function:
        // uses the probe function to calculate the hash index
        // Inserts into the first available bucket slot
        public void insert(Integer value, int a) {
            //check if there is an empty storage slot, else add to the overflow list
            if (count > table.length) {
                over.add(value);
                overflow ++;
            }
            else {
                Integer hash = probe(value, a);
                //Insert the value at the location found
                if(table[hash] == null) {                   //slot is empty
                    table[hash] = new BucketNode(value);    //create new node
                    table[hash].bucketcount ++;             //add to bucket value, used later in probing
                    count ++;                               // One more slot is used
                } 
                else {
                    //The index isn't empty so we need to insert the value
                    //to the last node
                    BucketNode currentNode = table[hash];
                    while(currentNode.next != null) {       //scan for next open node position
                        currentNode = currentNode.next;     
                        collision ++;                       //record collision
                    }
                    currentNode.next = new BucketNode(value);       //sets 'next' parameter as new node of given value
                    table[hash].bucketcount++;                      //add to bucket variable of the hashed index
                    collision ++;                                   //record collision
                }
            }
        }
        
        //prints the hash table
        public String print() {
            String result = "QUADRATIC PROBING HASH TABLE\n\nIndex\tValue\n";
            for(Integer i = 0; i < table.length; i++) {
                result += i + "\t";
                if(table[i] != null) {
                    BucketNode currentNode = table[i];
                    while(currentNode != null) {
                        result += currentNode.data + " ";
                        currentNode = currentNode.next;
                    }
                }
                result += "\n";
            }
            return result + "\n\n Slots used:\t\t" + count + "\n Collisions occured:\t" + collision 
            + " \n Values not stored:\t" + overflow + "\n Load factor:\t\t" + loadFactor() + "\n";
        }
    }
    
    // Chaining hash class:
    // Initialize a new table of size 120, bucketSize, and modulo objects
    // create an arraylist for overflow if there are too many input values
    // collision, and count track collisions and slots used
    // bucketSize and modulo are set using setBucketSize and setModulo
    // No need for overflow with chaining
    public static class ChainingHash {
        private ChainNode table[] = new ChainNode[360];
        public Integer count = 0;
        public Integer overflow = 0;
        public Integer collision = 0;
        public static Integer modulo = 0;

        //create a chainNode for semantics sake
        //uses the same linked list setup as the bucketNodes minus the bucket count
        public static class ChainNode {
            public ChainNode next = null;
            public Integer data = null;
            
            //assign the input value to the data object
            public ChainNode(Integer value) {
                data = value;
            }
        }
        // standard division modulo hashing using the value divided by the modulo number.
        private Integer hashFunction(Integer value) {
            return (value) % modulo;
        }

        //My own hash function
        //Hashes based on the input of the digits - starts with the full integer
        //and removes the first digit until the resulting integer is less than 
        //the length of the table to ensure it will be mapped.
        private Integer myHashFunction(Integer value) {
            String input = String.valueOf(value);
            char[] digits = input.toCharArray();
            Integer hash = 0;
            do {
                hash = Integer.parseInt(String.valueOf(digits));
                char[] temp = new char[digits.length - 1];
                System.arraycopy(digits, 1, temp, 0, digits.length -1);
                digits = temp;
            }
            while(hash >= table.length);
            return hash;
        }

        //allows for altering the division modulo number based on user input
        private static void setModulo(Integer value) {
            modulo = value;
        }
        
        // returns the amount of slots used by the hashing method divided by the storage space.
        private Float loadFactor() {
            return (float) count / table.length;
        }

        // insert function:
        // uses the probe function to calculate the hash index
        // Inserts into the first available node slot
        public void insert(Integer value, int a) {
            //Calculate the hash of the value
            Integer hash = 0;
            if (a == 0){
                hash = hashFunction(value);         //modular hash function
            }
            else {
                hash = myHashFunction(value);       //my hash function
            }
            //Insert the value at the location found
            if(table[hash] == null) {                   //slot is empty
                table[hash] = new ChainNode(value);     //insert new chain node with value
                count ++;                               //increment slots used
            } 
            else {
                //The index isn't empty so we need to insert the value
                //to the last node 
                ChainNode currentNode = table[hash];
                while(currentNode.next != null) {       //scan for next open node position
                    currentNode = currentNode.next;     
                    collision ++;                       //record collision
                }
                currentNode.next = new ChainNode(value);       //sets 'next' parameter as new node of given value
                collision ++;                                   //record collision
            }
        }
        
        ///prints the hash table
        public String print() {
            String result = "CHAINING HASH TABLE\n\nIndex\tValue\n";
            for(Integer i = 0; i < table.length; i++) {
                result += i + "\t";
                if(table[i] != null) {
                    ChainNode currentNode = table[i];
                    while(currentNode != null) {
                        result += currentNode.data + " ";
                        currentNode = currentNode.next;
                    }
                }
                result += "\n";
            }
            return result + "\n\n Slots used:\t" + count + "\n Collisions occured:\t" + collision + "\n Load factor:\t" + loadFactor() + "\n";
        }
    }
}