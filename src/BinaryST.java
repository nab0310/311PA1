// LEAVE THIS FILE IN THE DEFAULT PACKAGE
//  (i.e., DO NOT add 'package cs311.pa1;' or similar)

// DO NOT MODIFY THE EXISTING METHOD SIGNATURES
//  (you may, however, add additional methods and fields)

// DO NOT INCLUDE LIBRARIES OUTSIDE OF THE JAVA STANDARD LIBRARY
//  (i.e., you may include java.util.ArrayList etc. here, but not junit, apache commons, google guava, etc.)


public class BinaryST {
	// member fields and methods
	private Node root;
	private int size;
	private int distinctSize;

	private class Node{
		private String value;
		private Node left, right;
		public int multiples;

		public Node(String val) {
			this.value = val;
		}
	}
	
	public BinaryST()
	{
		// implementation
		size = 0;
	}
	
	public BinaryST(String[] s)
	{
		size = 0;
		// implementation
		for (String value : s) {
			add(value);
		}
	}
	
	public int distinctSize()
	{
		// implementation
		return distinctSize;
	}
	
	public int size()
	{
		// implementation
		return size;
	}
	
	public int height()
	{
		// implementation
		return heightRecursive(root);
	}

	private int heightRecursive(Node n){
		if(n == null) {
			return 0;
		}else {
			int h1 = heightRecursive(n.left);
			int h2 = heightRecursive(n.right);
			return 1 + Math.max(h1, h2);
		}
	}
	
	public void add(String s)
	{
		Node node = new Node(s);
		if(size() == 0){
			root = node;
			node.multiples++;
			size++;
			distinctSize++;
		}else{
			addRecursive(root, node);
		}
	}

	private Node addRecursive(Node old, Node newNode){
		int compare = newNode.value.compareTo(old.value);
		if(compare < 0){
			if(old.left == null) {
				distinctSize++;
				size++;
				newNode.multiples++;
				old.left = newNode;
			}else {
				old.left = addRecursive(old.left, newNode);
			}
		}else if(compare > 0){
			if(old.right == null) {
				distinctSize++;
				size++;
				newNode.multiples++;
				old.right = newNode;
			}else {
				old.right = addRecursive(old.right, newNode);
			}
		}else{
			old.multiples++;
			size++;
		}
		return old;
	}
	
	public boolean search(String s)
	{
		return searchRecursive(root, s) != null;
	}

	private Node searchRecursive(Node node, String s){
		int compare = node.value.compareTo(s);
		if(compare < 0){
			if(node.right == null){
				return null;
			}else {
				return searchRecursive(node.right, s);
			}
		}else if(compare > 0){
			if(node.left == null){
				return null;
			}else {
				return searchRecursive(node.left, s);
			}
		}
		return node;
	}

	public int frequency(String s)
	{
		// implementation
		Node node = searchRecursive(root, s);
		if(node == null){
			return 0;
		}else{
			return node.multiples;
		}
	}
	
	public boolean remove(String s)
	{
		// implementation
		Node node = removeRecursive(root, s);
		return node != null;
	}

	private Node removeRecursive(Node node, String s){
		int compare = node.value.compareTo(s);
		if(compare < 0){
			if(node.left == null){
				return null;
			}else{
				node.left  = removeRecursive(node.left, s);
			}
		}else if(compare > 0){
			if(node.right == null){
				return null;
			}else{
				node.right = removeRecursive(node.right,s);
			}
		}else{
			if(node.multiples > 1){
				node.multiples--;
				return node;
			}
			
		}
		return node;
	}

	public String[] inOrder()
	{
		// implementation
		return new String[10];
	}
	
	public String[] preOrder()
	{
		// implementation
		return new String[10];
	}
	
	public int rankOf(String s)
	{
		// implementation
		return 0;
	}
	public static void main(String[] args){
		String[] array = {"BC"};
		BinaryST bst = new BinaryST(array);
		System.out.println(bst.height());
	}
}