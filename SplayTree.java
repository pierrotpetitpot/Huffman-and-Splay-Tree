package assignment_3;

public class SplayTree extends TreeStructure {
	
	public static int zigzigCount = 0;
	public static int zigzagCount = 0;
	
	public void addNode(Node aNode) {	//using addNode of TreeStructure Class, but splaying the added node 
		super.addNode(aNode);
		splaying(aNode);
	}
	
	public Node find(int weight) {	//using the same find Node of TreeStructure Class, but splaying the found node 
		Node found = super.find(weight);
		
		if(found == null) 
			return null;
		
		splaying(found);
		return found;
	}

	
	public void remove(int toDelete) {	//find the node to delete, then splay it(now becomes the root). then find the right most child. delete the found node, splay the right most child 
										//connect the right subtree of the recently deleted root to the new root (right most child) 
		
		Node deleted = find(toDelete); //deleted splayed to the root 
		if(deleted == null)
			return;
		Node rightMost = getRightMost(deleted.getLeft()); //find the right most of the left child of root 
		Node rightChildRoot = deleted.getRight(); //right subtree 
		Node leftChildRoot = deleted.getLeft();//left subtree

		if(rightMost == leftChildRoot) { //if it turs out that there is only 1 node in the left subtree of the root, then new root becomes first node in the right subtree 
			this.root = rightChildRoot;
		}
		else if(leftChildRoot == null) //if there is no left subtree
			this.root = rightChildRoot;
		
		
		
		else {
			
		this.root = leftChildRoot;		
		Node splayedRightMost = find(rightMost.getWeight()); //splaying the right most 
		splayedRightMost.setRight(rightChildRoot);
		rightChildRoot.setParent(splayedRightMost);
		}
	
	}
	
	
	
	public void splaying(Node focus) {
		
		while(focus.getParent() != null || focus.getParent() == this.root) {  //if the focus node has no parent, it means that the focus node is the root 
			
			if(focus.getParent().getParent() ==null) {  //for zig; if the parent is the root, 1 simple left or right rotation 
				if(focus == focus.getParent().getRight()) {	//if the focus node is the right child of the root, then do left rotation 
					leftRotation(focus);	
					compare++;
				}
				else {										//if the focus node is the left child of the root, then do  rotation 
					rightRotation(focus);
					compare++;
				}
			}
			
			else if((focus == focus.getParent().getRight()) && (focus.getParent() == focus.getParent().getParent().getRight())) { //for zig zig;if the focus node is the right child of the parent, and if the parent is the right child of its grand parent
				zigzigCount++;
				leftRotation(focus.getParent());	//make the rotation of the parent first 
				leftRotation(focus);		//make the rotation to the focus node after 
			}
			else if((focus == focus.getParent().getLeft()) && (focus.getParent() == focus.getParent().getParent().getLeft())) { //for zig zig; if the focus node is the left child of its parent, and if its parent is the left child of the grandparent 
				zigzigCount++;
				rightRotation(focus.getParent());
				rightRotation(focus);
			}
			
			else if((focus == focus.getParent().getRight()) && (focus.getParent() == focus.getParent().getParent().getLeft())) { //for zig zag; if the focus node is the left child of the parent, and if the parent if the RIGHT child of the grand parent 
				zigzagCount++;
				leftRotation(focus); //make left rotation for the focus then make right rotation for the focus 
				rightRotation(focus);
			}
			else {					//if the focus node is the left child of its parent, and its parent is the right child of its grand parent 
				zigzagCount++;
				rightRotation(focus); 
				leftRotation(focus);
			}
				
			
		}
	}



	
}







