package spiderman;

import java.util.ArrayList;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 *       all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 */

public class Collider {

    private int numOfDimensions; 
    private int size; 
    private double capacity; 
    private int dimensionNum; 
    private int canonEvents; 
    private int weight; 
    @SuppressWarnings("unchecked")
    private ArrayList<Integer>[] adj;  

    private int numOfPeople; 
    private String name; 
    private int signature; 
    private DimensionNode[] aList;

    public Collider() {
        numOfDimensions = 0;
        size = 0;
        capacity = 0;
        dimensionNum = 0; 
        canonEvents = 0; 
        weight = 0;
        numOfPeople = 0; 
        name = null;
        signature = 0; 
        aList = null; 
    }

    private int getTotalNodes(DimensionNode [] aList) { // method to count total Nodes 
        int count = 0; // counter 
        for(int i = 0; i < aList.length; i++) { // for loop to go through array
            DimensionNode ptr = aList[i]; // go through linked list at specific index 
            while(ptr!=null) { // inner while loop
                count++; // keep counting 
                ptr = ptr.getNextDimensionNode(); // traverse 
            }
        }
        return count; // return val
    }

    private DimensionNode[] rehash(DimensionNode[] aList) { // rehash method w a parameter of a array of dimensions
        DimensionNode [] temp = new DimensionNode[aList.length * 2]; // create a temp array of a new length doubled of before
        for(int i = 0; i < aList.length; i++) { // for loop 
            DimensionNode ptr = aList[i]; // ptr so it stays on specific index and iterates after every linked list is gone through 
            while(ptr!=null) { // inner while loop to iterate through linked list inside each index 
                DimensionNode a = ptr; // temp var 
                ptr = ptr.getNextDimensionNode(); // keep traversing to next node
                int index = a.getDimensionNum() % temp.length; // hash function 
                a.setNextDimensionNode(temp[index]); // sets next dimension to the current first node at the specific index 
                temp[index] = a; // first node is now replaced 
            } 
        }

        return temp; // returns new array w double the size 
    }

    public void createTable(String inputFile) { // create the actual table with an input file parameter
        StdIn.setFile(inputFile); // sets the file as the input file, actual file is shown below in another method
        numOfDimensions = StdIn.readInt(); // num of Dimensions 
        //System.out.println(numOfDimensions);
        size = StdIn.readInt(); // length of the Clusters array
        //System.out.println(tableSize);
        capacity = StdIn.readDouble(); // capacity of each index
        //System.out.println(capacity);
        aList = new DimensionNode[size]; // clusters array initialized 
        int count = 0; // counter for while loop
        while(count < numOfDimensions) { // while loop
            int dimensionNum = StdIn.readInt(); // specific dimension number 
            int canonEvents = StdIn.readInt(); // number of canon events 
            int weight = StdIn.readInt(); // actual weight 
            DimensionNode node = new DimensionNode(dimensionNum, canonEvents, weight, null); // create a new dimension with specific parameters 
            int index = node.getDimensionNum() % size; // hash function 
            node.setNextDimensionNode(aList[index]); // makes the first current node placed right after it 
            aList[index] = node; // first node replaced by new node now 
            if(getTotalNodes(aList) / size >= capacity) { // if statement to check if it is pass capacity 
                aList = rehash(aList); // new array is sent using rehash function 
                size = size * 2; // table size is now doubled if the if statment is ran 
            } 
            count++; // add counter for while loop 
        } 
    }

    public void wrapAroundDimensions() { // wrapping method 
        int count = 0; // counter 
        while(count < aList.length) { // while loop 
            if(count == 0) { // one case if the i is 0 
                DimensionNode node1 = new DimensionNode(aList[aList.length-1]);  // node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(aList[aList.length-2]);  // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = aList[0]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode();  // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }

            else if(count == 1) { // second case if i is 1 
                DimensionNode node1 = new DimensionNode(aList[0]); // node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(aList[aList.length-1]); // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = aList[1]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode(); // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }

            else { // if both cases are not true 
                DimensionNode node1 = new DimensionNode(aList[count-1]); //node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(aList[count-2]); // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = aList[count]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode(); // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }
            count++; // counter for while loop 
        }
    }


    @SuppressWarnings("unchecked")
    public void addCurrentValues() {

        adj = (ArrayList<Integer>[]) new ArrayList<?>[numOfDimensions]; 
        
        for(int j = 0; j<numOfDimensions; j++) {
            adj[j] = new ArrayList<Integer>();
        }

        for(int i = 0; i < aList.length; i++) {
            DimensionNode ptr = aList[i];
            //System.out.println(ptr.getDimensionNum()); 
            while(ptr!=null) {
                adj[i].add(ptr.getDimensionNum());  
                ptr = ptr.getNextDimensionNode();
            }
            //System.out.println();        
        }
    }


    private boolean getFirstNum(int edge) {
        for(int i = 0; i<aList.length; i++) {
            if(adj[i].get(0) == edge) {
                return true;
            }
        }
        return false; 
    }

    private int getFirstIndex(int n) {
        int index = 0; 
        for(int i = 0; i<aList.length; i++) {
            if(adj[i].get(0) == n) {
                index = i;
            }
        }
        return index; 
    }

    private int indexOfEdge(int n) {
        int index = 0; 
        for(int i = 0; i < aList.length; i++) {
            for(int j = 0; j<adj[i].size(); j++) {
                if(adj[i].get(j) == n) {
                    index = i;
                }
            }
        }
        return index; 
    }
    
 
    public void addNewEdges() {
        int count = 0; 
        for(int i = 0; i<aList.length; i++) {
            int first = adj[i].get(0); 
            //System.out.println(first);
            for(int j = 1; j<adj[i].size(); j++) {
                int edge = adj[i].get(j); 
                boolean checker = getFirstNum(edge); 
                if(checker || !adj[i].contains(first)) {
                    adj[getFirstIndex(edge)].add(first);
                    System.out.println(adj[getFirstIndex(edge)]);
                }


                else if(aList.length+count<numOfDimensions && adj[i].contains(first)) {
                    adj[aList.length+count].add(edge); 
                    adj[aList.length+count].add(first);
                    count++;
                }
            }
        }
    }


    public void printCollider() { // to actually print the dimension  Numbers 
        for(int i = 0; i < adj.length; i++) { // for loop for the array 
            for(int j = 0; j<adj[i].size(); j++) {
                StdOut.print(adj[i].get(j) + " ");
            }
            StdOut.println(); // new line 
        }
    }

    public static void main(String[] args) { // main method 

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

        Collider obj2 = new Collider(); 
        obj2.createTable(args[0]);
        obj2.wrapAroundDimensions();
        obj2.addCurrentValues();
        obj2.addNewEdges();
        StdOut.setFile(args[2]);
        obj2.printCollider(); 
    }
}
