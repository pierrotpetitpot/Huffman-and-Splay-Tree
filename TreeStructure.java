package assignment_3;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class TreeStructure {
	
	public static int compare = 0;
	protected Node root; //each tree has a nodde
	
	public void setRoot(Node aRoot) { 
		this.root = aRoot;
	}
	
	public Node getRoot() {
		return this.root;
	}
		
	public void addNode(Node aNode) { //this method takes as argument as new initialized Node. although it is not very efficient for the Splay Tree problem,
		//I had to implement it this way to use my huffman methods. 
		
		if(root ==null) {
			root = aNode;  //first node that gets added is always the root 
		}
		
		else {
			
			Node currentNode = root;
			Node parent = null;
			
			while(1==1) { //infinite loop 
				
				 parent = currentNode;
	//---------------------------------------------------------------------------------------------------------------
				 //normal cases, there shound'nt be duplicate nodes with same weight in a BST
				 if(aNode.getWeight() < currentNode.getWeight()) {
					 currentNode = currentNode.getLeft();
					 compare++;
					 if(currentNode==null) {
						 parent.setLeft(aNode);
						 aNode.setParent(parent);
						 return;
					 }
				 }
				 else if (aNode.getWeight() > currentNode.getWeight()) {
					 currentNode = currentNode.getRight();
					 compare++;
					 if(currentNode==null) {
						 parent.setRight(aNode);
						 aNode.setParent(parent);
						 return;
					 }
				 }
//---------------------------------------------------------------------------------------------------------------
				 
				 else {	//if both the current node and the new node have the same weight, the program compares their positions. 
					 if(aNode.getPosition() > currentNode.getPosition()) {	//bigger position means less weight, therefore node goes to the left 
						 currentNode = currentNode.getLeft();
						 if(currentNode==null) {
							 parent.setLeft(aNode);
							 aNode.setParent(parent);
							 return;
						 }
					 }
					 else {
						 currentNode = currentNode.getRight();
						 if(currentNode==null){
							 parent.setRight(aNode);
							 aNode.setParent(parent);
							 return;
						 }
					 }
				 }
				 
			}
			
		}
	}
	
	public Node deleteMin() {  //only used for huffman tree. (used in the priority queue). However, deleteMin() method is a general method for any BST so i put it in my TreeStructure class
		if(this.root == null) {
			System.out.println("The tree is empty. Program will exit."); 
			System.exit(0);
		}
		Node current = this.root;
		Node parent = this.root;
		if(this.root.getLeft() == null) {	//if all the minimum nodes got deleted, then the smallest node at this point is the root itself, then shift the current root to its right child
			Node temp1 = this.root;	//pop off the current root and select the right child of the current root as the new root 
			this.root = this.root.getRight();
			return temp1;

		}
		while(current.getLeft() != null) { //go all the way left for smallest node 
			parent = current;
			current = current.getLeft();
		}
	 Node temp = current;
		
		if(current.getRight() == null) {	//if its a minimum leaf node 
			parent.setLeft(null);
		}
		else {
			parent.setLeft(current.getRight());	//if its a minimum node with a right child 
		}
		return temp;
	}
	
	public void rightRotation(Node aNode) { //the argument is the focus node that needs to be rotated 
		
		Node parent = aNode.getParent();
		Node child = aNode.getRight();
		
		if(aNode != null) {

			parent.setLeft(child);  //set the left child of the parent of the current node to the right child of the current node. 
									//since the right child (subtree) has to be lesser than the parent, but greater than the current node 
			if(child !=null)
				child.setParent(parent); //setting the parent of the right child of the current node to the parent of the current node 
			
			aNode.setParent(parent.getParent());  //setting the grandparent of the current node as the new parent of the current node 
		}
		
		if(parent.getParent() == null)
			this.root = aNode;		//after updating the new parent of the current node, if it turns out that its parent is null then the current node has to be the root 
		
		else if (parent == parent.getParent().getLeft()) {	//if the current node is not the root, we need to verify which child the current node is 
			parent.getParent().setLeft(aNode);
			compare++;
		}
		
		else {				//if the current node is the right child
			parent.getParent().setRight(aNode);	
			compare++;
		}
		
		if(aNode!=null)
			aNode.setRight(parent);
		
		parent.setParent(aNode);

		
	}
	
	public void leftRotation(Node aNode) { //same as right rotation, just need to swap everything thats right to left and everything thats left to right 
		
		Node parent = aNode.getParent();
		Node child = aNode.getLeft();
		
		if(aNode != null) {
			parent.setRight(child);
			
			if(child !=null)
				child.setParent(parent);
		
			aNode.setParent(parent.getParent());
		}
		
		if(parent.getParent() == null)
			this.root = aNode;
		
		else if (parent == parent.getParent().getRight()) {
			compare++;
			parent.getParent().setRight(aNode);
		}
		
		else {
			compare++;
			parent.getParent().setLeft(aNode);
		}
	
		if(aNode!=null)
			aNode.setLeft(parent);
		
		parent.setParent(aNode);
		
	}
    
	public Node find(int weight) { //this method takes an integer (the weight of the node) as argument. I implemented it this way for the Splay Tree problem, so that I do not need to create a New Node to initialize it 
		
		Node current = this.root;
		
		if(current == null) //if tree is empty 
			return null;
		
		while(current.getWeight() != weight) {  //if the tree is well sorted, it will eventually reach the end when current becomes null 
			
			
			if(weight < current.getWeight()) //if weight is lesser, go left 
				current = current.getLeft();
			else								//else, go right 
				current = current.getRight();
			
			if(current == null) {			//exit function if the current node could not be found 
				System.out.println("Node cannot be found");
				return null;
			}
		}
		return current;
	}

	public Node getRightMost(Node aNode) { //this could either return a leaf node, or a parent node with no right child 
		
		Node current = aNode;

		if(current == null)
			return null;
		
		while(current.getRight() !=null) {
			current = current.getRight();
		}
	
		
		return current;
	}
	
	public void postOrder(Node root) { //takes argument as root 
		Node current = root;
		if(current != null) {
			postOrder(current.getLeft()); //takes left first, right then with parent (so root is always printed at the end) 
			postOrder(current.getRight());
			System.out.println(current.getWeight());

			
		}
	}
	
	
    
    
    
 
 

}
	

