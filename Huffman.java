package assignment_3;



public class Huffman extends TreeStructure {
	
	
	public int[] frequency; //both arrays are used to identify node in the bst, since each node (ie char) has an unique weight and position
	public 	int[] position;	
    public int theMaxPosition; //used to merge nodes for Huffman tree; to ensure that inner nodes (merged nodes) have higher position than rest of the other nodes 
    					//that havent been merged yet by incrementing the max position that already exists 
	
	public static Huffman createHuffmanTree(String data) {	//creates nodes, store them in a huffman tree, and encode each leaf node depending on path
		
		Huffman BST = new Huffman();  
		BST.frequency =  findFrequency(data);
		BST.position = findPosition(data);

		int size = BST.position.length; //can be size of both position or frequency since those arrays have the same length 
		int max = 0;
		
		for(int i = 0; i < size-1; i++) { 	//finding the highest position using the position array 
			if(BST.position[i+1] > BST.position[i]) {
				max = BST.position[i+1];
			}
		}
		
		BST.theMaxPosition = max;		//setting theMaxPosition 
		
		for(int i = 0; i<size; i++) {	//adding nodes in the tree 
			if(BST.frequency[i]!=0) {		//could've used position array instead, doesn't matter, since position index starts at 1 
				Node newNode = new Node(BST.frequency[i], (char) i, BST.position[i]);
				BST.addNode(newNode);
			}
		}
		if(BST.getRoot() == null) { //if the tree is empty, then the file was empty 
			System.out.println("The file was empty, therefore nothing is encoded. Program will exit.");
			System.exit(0);
		}
		BST.huffTree(BST);  //transforming the BST to a Huffman Tree 
		BST.encodedChar();	//encoding each leaf node of the Huffman tree depending on path 
		return BST;
	}
	
	public void huffTree(Huffman bst) {  // helper method for createHuffmanTree; creates huffman tree from the BST created in createHuffmanTree
		
		if((bst.getRoot().getLeft()==null) && (bst.getRoot().getRight()==null)) { //if the tree has only 1 node (the root)
			return;
		}
		
		if(bst.getRoot() == null) { //if the tree is empty...
			System.out.println("The binary tree is null");
			System.exit(0);
		}
		
		while((bst.getRoot().getLeft() !=null)|| (bst.getRoot().getRight() !=null)) {  //only checking if the root still has a right child; if there is no right child, then there is no left child.
			Node node1 = bst.deleteMin();
			Node node2 = bst.deleteMin();
			Node merged = new Node(node1.getWeight() + node2.getWeight(),'\0', bst.theMaxPosition++);
			merged.setLefthuff(node1);	//smallest node between the 2, so it is added to the left
			merged.setRighthuff(node2);	//biggest node between the 2, so it is added to the right 
			
			bst.addNode(merged);
			
		}
	}
	
	public void encodedChar() { 	//helper method for createHuffmanTree; this method is used to pass a string (encoded code, depending on path) to the node 
		Node aNode = this.getRoot();
		String s = "";
		if((aNode.getLefthuff() == null) && (aNode.getRighthuff() == null)) { //if the tree only has 1 element, then attribute an arbitrate code "0"
			aNode.setEncoded("0");
			return;
		}
		encodedChar(aNode,s);
	}
	
	public static void encodedChar(Node root, String s) { //helper function for encodedChar()
		Node current = root;
		
		if((current.getLefthuff() == null)&& (current.getRighthuff() == null)) { //when reaching a leaf node, set the encoded string to the encoded member of Node class 
			current.setEncoded(s);		
			return;
		}
		if(current.getLefthuff() !=null) {	//add a '0' when going left 
			
			encodedChar(current.getLefthuff(), s + "0");
		}
		
		if(current.getRighthuff() != null) {	//add a '1' when going right 
			
			encodedChar(current.getRighthuff(), s+"1");
		}
	}
	
	public static void printHuff(Node root) {  //used to print explicitly the content of huffman tree (only the leaf nodes), prints the table with all the chars and encoded codes 
												//goes from min node to max node 
		Node current = root;
		if(current ==null) {
			return;
		}
		if((current.getLefthuff() == null) && (current.getRighthuff() == null)) {		//when reaching a a leaf node, print the content 
			System.out.println(current.toString()+ "     Encoded code: "+ current.getEncoded());
			return;
		}
		if(current.getLefthuff() !=null) {		//go left first
			printHuff(current.getLefthuff());
		}
		
		if(current.getRighthuff() != null) {		//go right after 
			printHuff(current.getRighthuff());
		}
	}
	
    public String encodingString(String data, Huffman bst) { 	//method to encode given string by user 
    	String encodedString = "";
    		for(char c: data.toCharArray()) {
    			int ASCII = (int) c;
    			int weight = this.frequency[ASCII];   //get unique combination of weight and position that each char has 
    			int position = this.position[ASCII];
    			Node aNode = searchLeafHuff(bst.getRoot(), weight, position); //search BST and store result in aNode 
    			encodedString = encodedString + aNode.getEncoded(); //store the encoded code of each char in string 
    			
    		}
    		return encodedString;
    }
    
	public static Node searchLeafHuff(Node root, int weight, int position) { //helper method; created this method to use the array thats created inside to store the searched node , so no need to create an external array 
		Node [] list = new Node[1]; //contains the searched node 
		searchLeafHuffHelper(root, weight,position, list);
		if(list[0] == null) {
			System.out.println("One or more characters in the input string did not appear in the source file.\nTherefore no encoding was made for those specific characters. Program will exit."); //if array is empty, then invalid bugg...(but this should not happen though)
			System.exit(0);
		}
		return list[0]; //return the searched node 
	}
	
	public static void searchLeafHuffHelper(Node root, int weight, int position, Node[] list) { //this method does not return anything as the base case has nothing to do with returning something, it modifies (i.e adds the searched node) the array given to it 
		Node current = root;
		
		if((current.getLefthuff() == null)&& (current.getRighthuff() == null)) {
			
			if((current.getWeight() == weight) && (current.getPosition() == position)) {
				list[0] = current; //if we are at a leaf node, and if the leaf node's position and frequency correspond to the search node, then return this node 
				return;
			}
			return;
		}
		if(current.getLefthuff() !=null) {
			
			searchLeafHuffHelper(current.getLefthuff(), weight, position, list);
		}
		
		if(current.getRighthuff() != null) {
			
			searchLeafHuffHelper(current.getRighthuff(), weight, position,list);
		}
		
	}
    
	
    
    
//----------------------------------------------------------------------------------------------------------------------------------------------
		//OTHER FUNCTIONS
		
    	
    
	public static int[] findPosition (String aString) { //method to find first occurence of a char in given data 
			int[] ASCIIs = new int [256];
			for(int i = 0; i<ASCIIs.length; i++) {
				for(int j = 0; j < aString.length(); j++) {
					char aChar = aString.charAt(j);
					int ASCII = (int)aChar;
					if(ASCII == i) {
						ASCIIs[i] = (j + 1); //position starts at index 1, otherwise the first CHAR's position value will be 0 and we need to differentiate that from the other that has value 0 also. 
						break;
					}
				}
			}
			
			return ASCIIs;
		}
		
		
	public static int[] findFrequency (String aString) {//method to find the frequency of each char in give data 
			int[] freq = new int[256];
			
			  for(int i = 0; i < aString.length(); i++) {
		        	char aChar = aString.charAt(i);
		        	int ASCII = (int)aChar;
		        	freq[ASCII]++;
		        }
			return freq;
		}
		
		
		
		

	

}
