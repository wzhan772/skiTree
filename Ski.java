/**
 * @author William Zhang 251215208 
 * The purpose of this class is to show each step of the skier going down the hill based on the requirements
 */
public class Ski {
	
	//set private instance variable
	private BinaryTreeNode<SkiSegment> root;
	
	/**
	 * Takes in a string array and constructs a tree using the data
	 */
	public Ski(String[] data) {
		SkiSegment[] segments = new SkiSegment[data.length];
		//loop through to check for child nodes
		for (int i = 0; i < data.length; i++) {
			if (data[i] == null) {
				segments[i] = null;
			//add each corresponding object data depending on each data value
			} else if (data[i].contains("jump")) {
				segments[i] = new JumpSegment(String.valueOf(i),data[i]);
			} else if (data[i].contains("slalom")) {
				segments[i] = new SlalomSegment(String.valueOf(i),data[i]);
			} else {
				segments[i] = new SkiSegment(String.valueOf(i),data[i]);
			}
		}
		TreeBuilder<SkiSegment> newTree = new TreeBuilder<SkiSegment>();
		LinkedBinaryTree<SkiSegment> newRoot = newTree.buildTree(segments);
		root = newRoot.getRoot();
	}
	
	/**
	 * method returns the root node of the tree
	 * @return root node
	 */
	public BinaryTreeNode<SkiSegment> getRoot() {
		return root;
	}
	
	/**
	 * method chooses best path for the skier to take
	 * @param node is a node that stores data
	 * @param sequence is the path that is chosen in a list
	 */
	public void skiNextSegment(BinaryTreeNode<SkiSegment> node, ArrayUnorderedList<SkiSegment> sequence) {
		sequence.addToRear(node.getData());
		//base case for recursion 
		if (node.getLeft() == null && node.getRight() == null) {
			return;
		}
		//set tree nodes
		BinaryTreeNode<SkiSegment> path = null;
		BinaryTreeNode<SkiSegment> left = node.getLeft();
		BinaryTreeNode<SkiSegment> right = node.getRight();
		//check to see if there is only one option/path
		if (left == null) { 
			path = right;
			skiNextSegment(path, sequence);
		} else if (right == null) { 
			path = left;
			skiNextSegment(path, sequence);
		//both paths contain options
		} else {
			if (left.getData() instanceof JumpSegment && right.getData() instanceof JumpSegment) {
				//if height of left is higher than right, choose the left path, otherwise choose right
				if (((JumpSegment)left.getData()).getHeight() > ((JumpSegment)right.getData()).getHeight()) {
					path = left;
				} else {
					path = right;
				}
			//case where only one path contains a jump
			} else if (left.getData() instanceof JumpSegment) {
				path = node.getLeft();
			} else if (right.getData() instanceof JumpSegment) {
				path = node.getRight();
			//cases with slaloms (one and two paths containing them)
			} else if (right.getData() instanceof SlalomSegment && left.getData() instanceof SkiSegment) {
				path = safeChoice(right, left);
			} else if (left.getData() instanceof SlalomSegment && right.getData() instanceof SlalomSegment) {
				path = safeChoice(left, right);
			} else if (left.getData() instanceof SlalomSegment && right.getData() instanceof SkiSegment) {
				path = safeChoice(left, right);
			//cases with regulars 
			} else {
				path = node.getRight();
			}
			skiNextSegment(path, sequence);
		}
	}
	
	/**
	 * method chooses the path that has a leeward direction
	 * @param first is the first node 
	 * @param second is the second node
	 * @return the node with safest(leeward) path
	 */
	private BinaryTreeNode<SkiSegment> safeChoice(BinaryTreeNode<SkiSegment> firstPath, BinaryTreeNode<SkiSegment> secondPath) {
		//if there is a leeward direction in the first node, return the first, otherwise return the second node
		if (((SlalomSegment)firstPath.getData()).getDirection().equals("L")) {
			return firstPath;
		} else {
			return secondPath;
		}
	}
}