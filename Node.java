package assignment_3;

public class Node{
	
	private int weight; //used for both huffman and splay tree problems 
	private char aChar; //used only for huffman
	private int position; //only for huffman 
	private String encoded = ""; //only for huffman 
    private Node left;	//for both
    private Node right; //for both 
    private Node lefthuff;	//when building the huffman tree off the binary tree, I wanted to differentiate the left/right children of the nodes
    private Node righthuff;	//of the original BST from left/right children of the nodes of the huffman tree that is being built simulteanously (this is the "priority queue" that i used)
    private Node parent; //only for splay tree (need parent for rotations), the concept of parent is not used for huffman problem 
	
	public Node (int weight, char aChar, int position) {
		this.weight = weight;
		this.aChar = aChar;
		this.position = position;
	}
	
	// All the "getters and setters" for the node 
	public int getWeight() {
		return this.weight;
	}
	
	public int getPosition() {
		return this.position;
	}
	
	public char getChar() {
		return this.aChar;
	}
	
	public String getEncoded() {
		return this.encoded;
	}
	
	public void setEncoded(String s) {
		this.encoded = s;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public void setLefthuff(Node lefthuff) {
		this.lefthuff = lefthuff;
	}
	
	public void setRighthuff(Node righthuff) {
		this.righthuff = righthuff;
	}
	
	public Node getLeft() {
		return this.left;
	}
	
	public Node getRight() {
		return this.right;
	}
	
	public Node getLefthuff() {
		return this.lefthuff;
	}
	
	public Node getRighthuff() {
		return this.righthuff;
	}
	
	public Node getParent() {
		return this.parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public String toString() {
		
		return "Char:"+aChar + "     freq:" + weight+"     pos:"+position;
	}
	
}