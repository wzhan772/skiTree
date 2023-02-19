/**
 * @author William Zhang 251215208 
 * The purpose of this class is to create the binary trees for the program using a queue-based approach
 */
public class TreeBuilder<T> {
	
    public LinkedBinaryTree<T> buildTree (T[] data) {
    	//create new linked queues
        LinkedQueue<T> dataQueue = new LinkedQueue<T>();
        LinkedQueue<BinaryTreeNode<T>> parentQueue = new LinkedQueue<BinaryTreeNode<T>>();
        //add items into the queue
        for (int i=0; i<data.length; i++) {
            dataQueue.enqueue(data[i]);
        }
        //initialize new tree and treeRoot
        BinaryTreeNode<T> treeRoot = new BinaryTreeNode<T>(dataQueue.dequeue());
        LinkedBinaryTree<T> tree = new LinkedBinaryTree<T>(treeRoot);
		parentQueue.enqueue(treeRoot);
		//while the queue is empty, store each child
        while (!dataQueue.isEmpty()) {
        	//store left and right (A and B)
            T A = dataQueue.dequeue();
            T B = dataQueue.dequeue();
            BinaryTreeNode<T> parent = parentQueue.dequeue();
            if (A != null) {
                //add child to parent - left
                BinaryTreeNode<T> left = new BinaryTreeNode<T>(A);
                parent.setLeft(left);
                parentQueue.enqueue(left);
            }
            if (B != null) {
                //add child to parent - right
                BinaryTreeNode<T> right = new BinaryTreeNode<T>(B);
                parent.setRight(right);
                parentQueue.enqueue(right);
            }
        }
        //return the tree
        return tree;
    }

}