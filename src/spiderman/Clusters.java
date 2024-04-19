package spiderman;
import java.util.*;

import org.w3c.dom.Node;


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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {

    private DimensionNode[] clusters; // private array of nodes of dimensions
    private double capacity; // private variable of total threshold

    public Clusters() { //constructor
        this.clusters = null;
        this.capacity = 0; 
    }

    private int getTotalNodes(DimensionNode[] cluster) { // method to count total Nodes 
        int count = 0; // counter 
        for(int i = 0; i < cluster.length; i++) { // for loop to go through array
            DimensionNode ptr = cluster[i]; // go through linked list at specific index 
            while(ptr!=null) { // inner while loop
                count++; // keep counting 
                ptr = ptr.getNextDimensionNode(); // traverse 
            }
        }
        return count; // return val
    }

    private DimensionNode[] rehash(DimensionNode[] cluster) { // rehash method w a parameter of a array of dimensions
        DimensionNode [] temp = new DimensionNode[cluster.length * 2]; // create a temp array of a new length doubled of before
        for(int i = 0; i < cluster.length; i++) { // for loop 
            DimensionNode ptr = cluster[i]; // ptr so it stays on specific index and iterates after every linked list is gone through 
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
        int numOfDimensions = StdIn.readInt(); // num of Dimensions 
        //System.out.println(numOfDimensions);
        int tableSize = StdIn.readInt(); // length of the Clusters array
        //System.out.println(tableSize);
        capacity = StdIn.readDouble(); // capacity of each index
        //System.out.println(capacity);
        clusters = new DimensionNode[tableSize]; // clusters array initialized 
        int count = 0; // counter for while loop
        while(count < numOfDimensions) { // while loop
            int dimensionNum = StdIn.readInt(); // specific dimension number 
            int canonEvents = StdIn.readInt(); // number of canon events 
            int weight = StdIn.readInt(); // actual weight 
            DimensionNode node = new DimensionNode(dimensionNum, canonEvents, weight, null); // create a new dimension with specific parameters 
            int index = node.getDimensionNum() % tableSize; // hash function 
            node.setNextDimensionNode(clusters[index]); // makes the first current node placed right after it 
            clusters[index] = node; // first node replaced by new node now 
            if(getTotalNodes(clusters) / tableSize >= capacity) { // if statement to check if it is pass capacity 
                clusters = rehash(clusters); // new array is sent using rehash function 
                tableSize = tableSize * 2; // table size is now doubled if the if statment is ran 
            } 
            count++; // add counter for while loop 
        } 
    }

    public void wrapAroundDimensions() { // wrapping method 
        int count = 0; // counter 
        while(count < clusters.length) { // while loop 
            if(count == 0) { // one case if the i is 0 
                DimensionNode node1 = new DimensionNode(clusters[clusters.length-1]);  // node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(clusters[clusters.length-2]);  // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = clusters[0]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode();  // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }

            else if(count == 1) { // second case if i is 1 
                DimensionNode node1 = new DimensionNode(clusters[0]); // node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(clusters[clusters.length-1]); // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = clusters[1]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode(); // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }

            else { // if both cases are not true 
                DimensionNode node1 = new DimensionNode(clusters[count-1]); //node 1 is the one that is going to be the second to last node
                DimensionNode node2 = new DimensionNode(clusters[count-2]); // node 2 is the one that is going to be the last node
                node2.setNextDimensionNode(null); // node 2 next is null 
                node1.setNextDimensionNode(node2); // node 1 next is node 2 
                DimensionNode temp = clusters[count]; // get specific index for the specifc linked list 
                while(temp.getNextDimensionNode()!=null) { // traverse to end of linked list 
                    temp = temp.getNextDimensionNode(); // get to end 
                }
                temp.setNextDimensionNode(node1); // set the old last node to the second to last node now 
            }
            count++; // counter for while loop 
        }
    }

    

    public void printCluster() { // to actually print the dimension  Numbers 
        for(int i = 0; i < clusters.length; i++) { // for loop for the array 
            DimensionNode temp = clusters[i]; // temp node 
            while(temp!=null) { // while loop 
                StdOut.print(temp.getDimensionNum() + " "); // print it 
                temp=temp.getNextDimensionNode(); // traverse 
            }
            
                StdOut.println(); // new line 
        }
    }

    public static void main(String[] args) { // main method 

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        Clusters obj = new Clusters(); // new clusters object 
        obj.createTable(args[0]); // calls createTable method 
        obj.wrapAroundDimensions(); // calls wrap method 
        StdOut.setFile(args[1]); // output file to show result code 
        obj.printCluster(); // calls method to print dimensions 
    }
}