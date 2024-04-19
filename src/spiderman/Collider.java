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
    private ArrayList<DimensionNode> adj; 
    private DimensionNode[] clusters; // private array of nodes of dimensions
    private int size; 
    private double capacity; // private variable of total threshold

    public Collider() { //constructor
        this.numOfDimensions = 0; 
        this.adj = null; 
        this.clusters = null;
        this.size = 0; 
        this.capacity = 0; 
    }

    public void adjacencyList(String inputFile) {

        StdIn.setFile(inputFile);
        numOfDimensions = StdIn.readInt(); 
        size = StdIn.readInt(); 
        capacity = StdIn.readDouble(); 
        clusters = new DimensionNode[size]; 
        adj = new ArrayList<>(); 
        int index = 0; 
        while(index < numOfDimensions) {
            DimensionNode ptr = clusters[index]; 
            adj.add(ptr); 
            DimensionNode edge = ptr.getNextDimensionNode(); 
            while(edge!=null) {
                adj.get(index).setNextDimensionNode(edge);
                System.out.print(adj.get(index).getDimensionNum());
                edge = edge.getNextDimensionNode(); 
            }
            index++; 
        }
        
    }

    public void addPeople() {
        int numOfPeople = StdIn.readInt(); 
        int dimension = StdIn.readInt();
        String name = StdIn.readString(); 
        int signature = StdIn.readInt(); 
        int index = 0; 

        while(index < numOfPeople) {
            DimensionNode ptr = clusters[index]; 
            while(ptr!=null) {
                if(ptr.getDimensionNum()==dimension) {
                    DimensionNode node = new DimensionNode(dimension, name, signature); 
                    adj.get(index).setNextDimensionNode(node);
                }
                ptr = ptr.getNextDimensionNode(); 
            }
            index++; 
        }
    }


    public void printCollider() { // to actually print the dimension  Numbers 
        for(int i = 0; i < adj.size(); i++) { 
            DimensionNode temp = adj.get(i); 
            while(temp!=null) { 
                StdOut.print(temp.getDimensionNum() + " "); 
                temp=temp.getNextDimensionNode(); 
            }
            
                StdOut.println(); 
        }
    }

    public static void main(String[] args) { // main method 

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        Clusters obj1 = new Clusters(); 
        Collider obj2 = new Collider(); 
        obj1.createTable(args[0]);
        obj2.adjacencyList(args[1]); 
        obj2.addPeople();
        StdOut.setFile(args[2]);
        obj2.printCollider(); // calls method to print dimensions 
    }
}
