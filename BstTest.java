import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Scanner;

public class BstTest extends JPanel implements ActionListener {
        public static final long serialVersionUID = 3L;
	// the binary tree
	private BST tree = null;

	// the node location of the tree
	private HashMap<TreeNode, Rectangle> nodeLocations = null;

	// the sizes of the subtrees
	private HashMap<TreeNode, Dimension> subtreeSizes = null;

	// do we need to calculate locations
	static private boolean dirty = true;

	// space between nodes
	private int parent2child = 20, child2child = 30;

	// helpers
	private Dimension empty = new Dimension(0, 0);

	private FontMetrics fm = null;

	public BstTest(BST tree) {
		this.tree = tree;
		nodeLocations = new HashMap<TreeNode, Rectangle>();
		subtreeSizes = new HashMap<TreeNode, Dimension>();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	// calculate node locations
	private void calculateLocations() {
		nodeLocations.clear();
		subtreeSizes.clear();
		TreeNode root = tree.getRoot();
		if (root != null) {
			calculateSubtreeSize(root);
			calculateLocation(root, Integer.MAX_VALUE, Integer.MAX_VALUE, 0);
		}
	}

	// calculate the size of a subtree rooted at n
	private Dimension calculateSubtreeSize(TreeNode n) {
		if (n == null)
			return new Dimension(0, 0);
		Dimension ld = calculateSubtreeSize(n.getLeft());
		Dimension rd = calculateSubtreeSize(n.getRight());
		int h = fm.getHeight() + parent2child + Math.max(ld.height, rd.height);
		int w = ld.width + child2child + rd.width;
		Dimension d = new Dimension(w, h);
		subtreeSizes.put(n, d);
		return d;
	}

	// calculate the location of the nodes in the subtree rooted at n
	private void calculateLocation(TreeNode n, int left, int right, int top) {
		if (n == null)
			return;
		Dimension ld = (Dimension) subtreeSizes.get(n.getLeft());
		if (ld == null)
			ld = empty;
		Dimension rd = (Dimension) subtreeSizes.get(n.getRight());
		if (rd == null)
			rd = empty;
		int center = 0;
		if (right != Integer.MAX_VALUE)
			center = right - rd.width - child2child / 2;
		else if (left != Integer.MAX_VALUE)
			center = left + ld.width + child2child / 2;
		int width = fm.stringWidth("" + n.getKey());
		Rectangle r = new Rectangle(center - width / 2 - 3, top, width + 6, fm
				.getHeight());
		nodeLocations.put(n, r);
		calculateLocation(n.getLeft(), Integer.MAX_VALUE, center - child2child
				/ 2, top + fm.getHeight() + parent2child);
		calculateLocation(n.getRight(), center + child2child / 2,
				Integer.MAX_VALUE, top + fm.getHeight() + parent2child);
	}

	// draw the tree using the pre-calculated locations
	private void drawTree(Graphics2D g, TreeNode n, int px, int py, int yoffs) {
		if (n == null)
			return;
		Rectangle r = (Rectangle) nodeLocations.get(n);
		g.draw(r);
		g.drawString("" + n.getKey(), r.x + 3, r.y + yoffs);
		if (px != Integer.MAX_VALUE)
			g.drawLine(px, py, r.x + r.width / 2, r.y);
		drawTree(g, n.getLeft(), r.x + r.width / 2, r.y + r.height, yoffs);
		drawTree(g, n.getRight(), r.x + r.width / 2, r.y + r.height, yoffs);
	}

	public void paint(Graphics g) {
		super.paint(g);
		fm = g.getFontMetrics();
		// if node locations not calculated
		if (dirty) {
			calculateLocations();
			dirty = false;
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(getWidth() / 2, parent2child);
		drawTree(g2d, tree.getRoot(), Integer.MAX_VALUE, Integer.MAX_VALUE, fm
				.getLeading()
				+ fm.getAscent());
		fm = null;
	}

	static void printMenu() {
		String StrMenu = "+--- Binary trees ---\n";
		StrMenu += "r : Reset tree\n";
		StrMenu += "i : Insert string\n";
		StrMenu += "f : Find string\n";
		StrMenu += "d : Delete string\n";
		StrMenu += "p : traverse Preorder\n";
		StrMenu += "l : traverse Levelorder\n";
		StrMenu += "q : Quit program\n";
		StrMenu += "h : show this text\n";
		System.out.print(StrMenu);
		System.out.flush();
	}

	static char getChar(Scanner in) {
		String tmp;
		do {
			tmp = in.nextLine();
		} while (tmp.length() != 1);
		return tmp.charAt(0);
	}

	static int getInt(Scanner in) {
		int tmp;
		while (!in.hasNextInt()) {
			in.nextLine();
		}
		tmp = in.nextInt();
		in.nextLine();
		return tmp;
	}

	static String getString(Scanner in) {
		return in.nextLine();
	}

	public static void main(String[] args) {
		BST tree = new BST();

		JFrame f = new JFrame("BST");
		f.getContentPane().add(new BstTest(tree));
		// create and add an event handler for window closing event
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setBounds(50, 50, 600, 400);
		f.setVisible(true);

		char c;
		int k;
		String v;
		Scanner in = new Scanner(System.in);
		printMenu();
		for (;;) {
			System.out.print("lab > ");
			System.out.flush();
			c = getChar(in);

			switch (c) {
			case 'r':
				tree = new BST();
				f.dispose();
				f = new JFrame("BST");
				f.getContentPane().add(new BstTest(tree));
				// create and add an event handler for window closing event
				f.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});
				f.setBounds(50, 50, 600, 400);
				f.setVisible(true);
				break;
			case 'i':
				System.out.print("Insert key: ");
				System.out.flush();
				k = getInt(in);
				System.out.print("Insert value: ");
				System.out.flush();
				v = getString(in);
				tree.insert(k, v);
				System.out.print("Inserted key=" + k + " value=" + v + "\n");
				System.out.flush();
				dirty = true;
				f.repaint();
				break;
			case 'd':
				System.out.print("Delete key: ");
				System.out.flush();
				k = getInt(in);
				tree.remove(k);
				dirty = true;
				f.repaint();
				break;
			case 'f':
				System.out.print("Find key: ");
				System.out.flush();
				k = getInt(in);
				String str = tree.find(k);
				if (str != null)
					System.out.print("Found key:" + k + " with value:" + str
							+ "\n");
				else
					System.out.print("Key:" + k + " not found\n");
				break;
			case 'q':
				System.out.println("Program terminated.");
				System.exit(0);
				break;
			case 'p':
				PreorderIterator po = tree.preorder();
				while (po.hasNext()) {
					TreeNode tmp = po.next();
					System.out.print("Key=" + tmp.getKey() + " Value="
							+ tmp.getValue() + "\n");
				}
				break;
			case 'l':
				LevelorderIterator lo = tree.levelorder();
				while (lo.hasNext()) {
					TreeNode tmp = lo.next();
					System.out.print("Key=" + tmp.getKey() + " Value="
							+ tmp.getValue() + "\n");
				}
				break;
			case 'h':
				printMenu();
				break;
			default:
				System.out.print("**** Sorry, No menu item named '");
				System.out.print(c + "'\n");
				System.out.flush();
			}
		}
	}
}
