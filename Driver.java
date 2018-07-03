package assignment_3;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


import java.io.BufferedReader;
import java.io.FileReader;

public class Driver {
	
	  public static void main(String[] args) throws Exception{
	    	
		  Scanner input  = new Scanner(System.in);
		  System.out.println("Whats the option");
		  String option = input.nextLine();
		  
		  
		  
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//												huffman
		
	    	if(args[0].equals("Huffman")) {
   		

		  
		    String str= "";
		    String encodedString = "";
			Scanner sc = null;		
		  
			try
			{
				sc = new Scanner(new FileInputStream(args[1].substring(1, args[1].length()-1)));				     
			}
			catch(FileNotFoundException e) 
			{							   
				System.out.println("File coule not be opened.\nProgram will exit");
				System.exit(0);			   
			}
			
			while(sc.hasNextLine())
			{
				str = str + sc.nextLine();		
			}
			

	    	Huffman aTree = new Huffman();
	      
	        aTree = Huffman.createHuffmanTree(str);	//creating a huffman tree 
	        
	        System.out.println("Here is the table of the encoded characters from the given file (From the left most leaf node to the right most leaf node): \n");
	        Huffman.printHuff(aTree.getRoot()); //print out the table 
	        
	        System.out.println("\n");
	        
	        System.out.println("What string would you like to encode?");
	        encodedString = input.nextLine();
	        
	        String aString = aTree.encodingString(encodedString, aTree); //encoded the given string based of the encoded characters in the huffman tree 
	        
	        System.out.println(aString);
	        
	    	}
	        
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
														//splay tree 
	    	
	    	else if(args[0].equals("SplayTree")) {
	    		
	    	String s; //holds each line of the text 
	    	Scanner inputt = new Scanner(System.in);
			Scanner sc = null;		
			int id; //weight of the node 
			char c;	//the code for operation; either a,f or r 
			int count=  Integer.parseInt(args[2]); //the # line of the code
			int numberOfOperations = 0;
			
			SplayTree tree = new SplayTree();
	    	
			try
			{
				sc = new Scanner(new FileInputStream(args[1].substring(1, args[1].length()-1)));				     
			}
			catch(FileNotFoundException e) 
			{							   
				System.out.println("File coule not be opened.\nProgram will exit.");
				System.exit(0);			   
			}
			
			System.out.println("#?");
			numberOfOperations = inputt.nextInt();
			
			
			while(sc.hasNextLine() && count<=numberOfOperations)
			{
				
				s = sc.nextLine();		
				c = s.charAt(0);
				id = Integer.parseInt(s.substring(1));
				System.out.println("OPERATION: " + c+ "   WEIGHT: " + id + "    LINE CODE: " + count);
		
				if(c == 'a') {
					Node aNode = new Node(id,'\0',0);
					tree.addNode(aNode);
					count++;

				}
				
				else if(c == 'f') {
					tree.find(id);
					count++;

				}
				
				else if(c =='r') {
					tree.remove(id);
					count++;

				}
				else {
					System.out.println("Invalid operation. Program will exit");
					System.exit(0);
				}
				
			}
			System.out.println("\n\nPost-order traversal:\n");
			tree.postOrder(tree.root);
			System.out.println("\n\nNumber of compares: "+SplayTree.compare + "\nNumber of zig-zig: "+SplayTree.zigzigCount+"\nNumber of zig-zag: " + SplayTree.zigzagCount + "\n\n");


			
			
			
			
	    	}
	    	
	   


	        
	        
	        
	    }
	
	
	

}
