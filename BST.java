public class BST {
	protected TreeNode root;

	protected int size;

	public BST() {
		root = null;
		size = 0;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public TreeNode getRoot() {
		return root;
	}

	protected TreeNode treeFind(int k) {
		// Requires a nonempty tree
		TreeNode here = root;
		while (true) {
			if (k == here.getKey()) { // Found it...
				return here;
			} else if (k < here.getKey()) { // ...else check left subtree
				if (here.getLeft() == null)
					return here;
				else
					here = here.getLeft();
			} else { // ...else check right subtree
				if (here.getRight() == null)
					return here;
				else
					here = here.getRight();
			}
		}
	}

	public String find(int k) {
		if (root == null)
			return null; // Empty tree
		TreeNode here = treeFind(k);
		if (k == here.getKey())
			return here.getValue(); // Found
		else
			return null; // Not found
	}

	public void insert(int k, String v) {
		if (root == null) {
			root = new TreeNode(k, v);
			size++;
		} else {
			TreeNode here = treeFind(k);
			if (k < here.getKey()) {
				here.setLeft(new TreeNode(k, v));
				size++;
			} else if (k > here.getKey()) {
				here.setRight(new TreeNode(k, v));
				size++;
			} else {
				here.setValue(v);
			}
		}
		return;
	}

	public void remove(int k) {
		// To be completed
	}

	public PreorderIterator preorder() {
		return new PreorderIterator(root);
	}

	public LevelorderIterator levelorder() {
		return new LevelorderIterator(root);
	}
}
