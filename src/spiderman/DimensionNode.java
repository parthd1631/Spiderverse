package spiderman;

public class DimensionNode {
        private int dimensionNum; 
        private int canonEvents; 
        private int weight; 
        private DimensionNode next; // link to next node in the list
        private String name; 
        private int signature; 
    
        /*
         * Constructor
         * @param dimensionNum the DimensionNum
         * @param next link to the next node in the list
         */
        public DimensionNode (int dimensionNum, int canonEvents, int weight, DimensionNode next) {
            this.dimensionNum = dimensionNum;
            this.canonEvents = canonEvents; 
            this.weight = weight; 
            this.next = next;
        }

        public DimensionNode(int dimensionNum, String name, int signature) {
            this.dimensionNum = dimensionNum; 
            this.name = name; 
            this.signature = signature; 
        }

       
        
        // Getter and Setter methods
        public int getDimensionNum() { return dimensionNum; }
        public void setDimensionNum(int dimensionNum) { this.dimensionNum = dimensionNum; }

        public int getCanonEvents() { return canonEvents; }
        public void setCanonEvents(int canonEvents) {this.canonEvents = canonEvents;}

        public int getWeight() { return weight; }
        public void setWeight(int weight) {this.weight = weight; }

        public String getName() { return name; }
        public void setName(String name) {this.name=name;}

        public int getSignature() {return signature;}
        public void setSignature(int signature) {this.signature=signature;}

        public DimensionNode getNextDimensionNode() { return next; }
        public void setNextDimensionNode(DimensionNode next) { this.next = next; }   


        public DimensionNode (DimensionNode node) {
            this.dimensionNum = node.getDimensionNum(); 
            this.canonEvents = node.getCanonEvents(); 
            this.weight = node.getWeight(); 
            this.next = null; 
        }
} 
