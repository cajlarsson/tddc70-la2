import java.util.Iterator;

public class PreorderIterator implements Iterator<TreeNode> {

	protected Stack<TreeNode> S;

	public PreorderIterator(TreeNode tree) {
		S = new Stack<TreeNode>();
		if (tree != null)
			S.push(tree);
	}

	public void remove() {
		// Leave empty
	}

	public TreeNode next() {
		return null;
		// To be completed
	}

	public boolean hasNext() {
		return false;
		// To be completed
	}
}
