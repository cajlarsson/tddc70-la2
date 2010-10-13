public class TreeNode {
	private int key;

	private String value;

	private TreeNode left, right;

	public TreeNode(int k, String v) {
		key = k;
		value = v;
		left = right = null;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int k) {
		key = k;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String v) {
		value = v;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode t) {
		left = t;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode t) {
		right = t;
	}
}
