package editortrees;

import javax.print.attribute.standard.NumberUp;
import javax.swing.JFrame;

import editortrees.Node.Code;

// A height-balanced binary tree with rank that could be the basis for a text editor.

public class EditTree {

	public Node root;
	public int rotationCounter = 0;
	private DisplayableBinaryTree display;

	/**
	 * Construct an empty tree
	 */
	public EditTree() {
		root = Node.NULL_NODE;
	}

	/**
	 * Construct a single-node tree whose element is c
	 * 
	 * @param c
	 */
	public EditTree(char c) {
		root = new Node(c, this);

	}

	public EditTree(Node n) {
		root = n;
	}

	/**
	 * Create an EditTree whose toString is s. This can be done in O(N) time,
	 * where N is the length of the tree (repeatedly calling insert() would be
	 * O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		// Divide string in half, call method in Node which recurses on both
		// sides.
		int midpoint = s.length() / 2;
		char midChar = s.charAt(midpoint);
		this.root = new Node(midChar, this);
		String left = s.substring(0, midpoint);
		String right = s.substring(midpoint + 1);
		this.root.editTreeString(left, right);
	}

	/**
	 * Make this tree be a copy of e, with all new nodes, but the same shape and
	 * contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		this.root = this.treeCopy(e.root, Node.NULL_NODE);
		this.rotationCounter = e.rotationCounter;
	}

	/**
	 * Construct an empty tree
	 */
	public EditTree(boolean visable) {
		this.root = Node.NULL_NODE;
		this.rotationCounter = 0;
		this.display = new DisplayableBinaryTree(this, 600, 800);
		this.display.display(visable);
	}

	/**
	 * Construct a single-node tree whose element is c
	 * 
	 * @param c
	 */
	public EditTree(char c, boolean visable) {
		this.root = new Node(c, Node.NULL_NODE, 0, this);
		this.rotationCounter = 0;
		this.display = new DisplayableBinaryTree(this, 600, 800);
		this.display.display(visable);
	}

	/**
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		return this.root.height();
	}

	/**
	 * 
	 * returns the total number of rotations done in this tree since it was
	 * created. A double rotation counts as two.
	 * 
	 * @return number of rotations since tree was created.
	 */
	public int totalRotationCount() {
		// return this.root.totalRotationCount();
		return rotationCounter;
	}

	/**
	 * return the string produced by an inorder traversal of this tree
	 */
	@Override
	public String toString() {
		String s = "";
		return this.root.toString(s);
	}

	/**
	 * 
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.root.size() || this.root == Node.NULL_NODE) {
			throw new IndexOutOfBoundsException();
		}
		return this.root.get(pos);
	}

	/**
	 * 
	 * @param c
	 *            character to add to the end of this tree.
	 */
	public void add(char c) {
		if (this.root == Node.NULL_NODE) {
			this.root = new Node(c, Node.NULL_NODE, 0, this);

			return;
		}
		this.root = this.root.add(c);
	}

	/**
	 * 
	 * @param c
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             id pos is negative or too large for this tree
	 */
	public void add(char c, int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.root.size()) {
			throw new IndexOutOfBoundsException();
		}
		if (this.root == Node.NULL_NODE) {
			this.root = new Node(c, Node.NULL_NODE, 0, this);

			return;
		}
		this.root = this.root.add(c, pos);
	}

	/**
	 * 
	 * @return the number of nodes in this tree
	 */
	public int size() {
		if (this.root == Node.NULL_NODE) {
			return 0;
		}
		return this.root.size();
	}

	/**
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		int size = this.root.size();
		if (pos < 0 || pos > size || size == 0) {
			throw new IndexOutOfBoundsException();
		}
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume assume that you will replace it with the
		// *successor*.
		char elemente = this.root.get(pos);
		this.root = this.root.delete(pos);
		return elemente; // replace by a real calculation.
	}

	/**
	 * This method operates in O(length*log N), where N is the size of this
	 * tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if (pos >= 0 && pos + length - 1 < this.root.size()) {
			String s = "";
			for (int i = pos; i < pos + length; i++) {
				s += this.get(i);
			}
			return s;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}

	/**
	 * This method is provided for you, and should not need to be changed. If
	 * split() and concatenate() are O(log N) operations as required, delete
	 * should also be O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length)
			throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete"
							: "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		if (this.equals(other)) {
			// if they're the same tree
			throw new IllegalArgumentException();
		}
		if (this.root == Node.NULL_NODE && other.root == Node.NULL_NODE) {
			// both are empty trees
		} else {
			if (this.root == Node.NULL_NODE) {
				// if this tree is empty
				this.root = other.root;
			} else if (other.root == Node.NULL_NODE) {
				// adding a null tree
			}

			// delete the leftmost inorder element of the smaller tree
			// and call the rest of the tree V
			else if (this.root.nodeHeight >= other.root.nodeHeight) {
				char qElement = other.delete(0);

				EditTree V = other;

				this.root = pasteTreeR(this, V, qElement);
			} else {
				int size = this.size();

				char qElement = this.delete(size - 1);

				EditTree V = this;

				this.root = pasteTreeL(other, V, qElement);
			}

			// making the other tree a null tree
			other.root = Node.NULL_NODE;
		}
	}

	public static Node pasteTreeR(EditTree T, EditTree V, char qElement) {
		// returns root of tree

		int heightOfT = T.getRoot().nodeHeight;
		int heightOfV = V.getRoot().nodeHeight;

		Node p = T.root;
		int hp = heightOfT;
		Node parent = Node.NULL_NODE;

		while (hp - heightOfV > 1) {
			// go right until you find a place to insert the tree without
			// changing the height
			if (p.balance == Code.LEFT) {
				// you subtract two since the left subtree height had 1 less
				// than the current height subtree height, and since the
				// balance code was to the left, you know that the height of
				// the node you're going to is one less than the left subtree
				hp -= 2;
			} else {
				hp--;
			}

			parent = p;
			p = p.right;
		}

		// add the new node and reassign values
		Node q = new Node(qElement, parent, p.rank + 1, T);
		q.left = p;
		p.parent = q;

		q.right = V.root;
		V.root.parent = q;

		if (parent != Node.NULL_NODE) {
			parent.right = q;
		}

		// overwrite p as q
		p = q;
		p.rank = p.left.size();

		// updating node fields
		q.setNodeHeight(q);

		if (p.left.nodeHeight - p.right.nodeHeight == 1) {
			q.balance = Code.LEFT;
		} else if (p.right.nodeHeight - p.left.nodeHeight == 1) {
			p.balance = Code.RIGHT;
		} else if (p.left.nodeHeight == p.right.nodeHeight) {
			p.balance = Code.SAME;
		} else if (q.left.nodeHeight - q.right.nodeHeight > 1) {
			if (q.left.balance == Code.RIGHT) {
				// double right
				q.left = q.rotateLeft(q.left);
				q.rotateRight(q);
			} else {
				// single right
				q.rotateRight(q);
			}
		} else {
			if (q.right.balance == Code.LEFT) {
				// double left
				q.right = q.rotateRight(q.right);
				q.rotateLeft(q);
			} else {
				// single left
				q.rotateLeft(q);
			}
		}
		q.setNodeHeight(p);

		// rebalance after insertion
		q.balanceCodes(p);
		q.setNodeHeight(p);
		p.rank = p.left.size();
		return p.getRoot(p);
	}

	public static Node pasteTreeL(EditTree T, EditTree V, char qElement) {
		// returns root of tree

		int heightOfT = T.getRoot().nodeHeight;
		int heightOfV = V.getRoot().nodeHeight;

		Node p = T.root;
		int hp = heightOfT;
		Node parent = Node.NULL_NODE;

		while (hp - heightOfV > 1) {
			// go right until you find a place to insert the tree without
			// changing the height
			if (p.balance == Code.RIGHT) {
				// same as before but mirror
				hp -= 2;
			} else {
				hp--;
			}

			parent = p;
			p = p.left;
		}

		// add the new node and reassign values
		Node q = new Node(qElement, parent, V.root.rank + 1, T);
		q.right = p;
		p.parent = q;

		q.left = V.root;
		V.root.parent = q;

		if (parent != Node.NULL_NODE) {
			parent.left = q;
		}

		// overwrite p as q
		p = q;
		p.rank = p.left.size();

		// updating node fields
		q.setNodeHeight(q);
		if (p.left.nodeHeight - p.right.nodeHeight == 1) {
			p.balance = Code.LEFT;
		} else if (p.right.nodeHeight - p.left.nodeHeight == 1) {
			p.balance = Code.RIGHT;
		} else if (p.left.nodeHeight == p.right.nodeHeight) {
			p.balance = Code.SAME;
		} else if (q.left.nodeHeight - q.right.nodeHeight > 1) {
			if (q.left.balance == Code.RIGHT) {
				// double right
				q.left = q.rotateLeft(q.left);
				q.rotateRight(q);
			} else {
				// single right
				q.rotateRight(q);
			}
		} else {
			if (q.right.balance == Code.LEFT) {
				// double left
				q.right = q.rotateRight(q.right);
				q.rotateLeft(q);
			} else {
				// single left
				q.rotateLeft(q);
			}
		}
		q.setNodeHeight(p);

		// rebalance after insertion
		q.balanceCodes(p);

		q.setNodeHeight(p);
		return p.getRoot(p);
	}

	public void addToBeginning(char c) {
		// adds a node at the 0 position of the tree
		if (this.root != Node.NULL_NODE) {
			Node node = this.root;
			while (node.left != Node.NULL_NODE) {
				// increment rank while recursing left
				node.rank++;
				node = node.left;
			}
			// adds new node at beginning
			// with updated fields
			node.rank++;
			Node nodeWithC = new Node();
			nodeWithC.parent = node;
			nodeWithC.element = c;
			nodeWithC.left = Node.NULL_NODE;
			nodeWithC.right = Node.NULL_NODE;
			nodeWithC.rank = 0;
			nodeWithC.nodeHeight = 0;
			nodeWithC.balance = Code.SAME;
			node.left = nodeWithC;
			node.setNodeHeight(node);
			node.setBalanceCode(node);

			Node temp = node.left;
			while (node.parent != Node.NULL_NODE) {
				// goes up the tree while fixing node fields
				node.setNodeHeight(node);
				node.setBalanceCode(node);
				node = node.parent;
			}
		} else {
			// add new root
			Node nodeWithC = new Node();
			nodeWithC.parent = Node.NULL_NODE;
			nodeWithC.element = c;
			nodeWithC.rank = 0;
			nodeWithC.left = Node.NULL_NODE;
			nodeWithC.right = Node.NULL_NODE;
			nodeWithC.balance = Code.SAME;
			this.root = nodeWithC;
		}
	}

	/**
	 * This operation must be done in time proportional to the height of this
	 * tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		// returns the right tree
		// this is the left tree

		int size = this.root.size();
		if (pos < 0 || pos > size || size == 0) {
			throw new IndexOutOfBoundsException();
		}

		EditTree tree = new EditTree(Node.NULL_NODE);
		tree.root = this.root;

		if (pos == 0) {
			// base case
			EditTree copyTree = new EditTree(this.root);
			this.root = Node.NULL_NODE;
			return copyTree;
		} else {
			Node[] leftRightRoots = this.root.split(pos);
			// right tree
			tree.root = leftRightRoots[1];
			// left tree
			this.root = leftRightRoots[0];

			// making sure the root's parent's are NULL_NODES after they are
			// split
			tree.root.parent = Node.NULL_NODE;
			this.root.parent = Node.NULL_NODE;

		}
		if (tree.root.left.nodeHeight - tree.root.right.nodeHeight == 1) {
			tree.root.balance = Code.LEFT;
		} else if (tree.root.right.nodeHeight - tree.root.left.nodeHeight == 1) {
			tree.root.balance = Code.RIGHT;
		} else if (tree.root.left.nodeHeight == tree.root.right.nodeHeight) {
			tree.root.balance = Code.SAME;
		} else if (tree.root.left.nodeHeight - tree.root.right.nodeHeight > 1) {
			if (tree.root.left.balance == Code.RIGHT) {
				// double right
				tree.root.left = tree.root.rotateLeft(tree.root.left);
				tree.root = tree.root.rotateRight(tree.root);

			} else {
				// single right
				tree.root = tree.root.rotateRight(tree.root);
			}
		} else {
			if (tree.root.right.balance == Code.LEFT) {
				// double left
				tree.root.right = tree.root.rotateRight(tree.root.right);
				tree.root = tree.root.rotateLeft(tree.root);
			} else {
				// single left
				tree.root = tree.root.rotateLeft(tree.root);
			}
		}
		return tree;
	}

	/**
	 * Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		return this.toString().indexOf(s);
	}

	/**
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		if (find(s) < pos) {
			return -1;
		}
		return find(s);
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	public Node treeCopy(Node n, Node parent) {
		if (n == Node.NULL_NODE) {
			root = Node.NULL_NODE;
			return root;
		}
		Node root = new Node(n, this);
		if (n.left != Node.NULL_NODE && n.right != Node.NULL_NODE) {
			// both
			root.left = treeCopy(n.left, root);
			root.right = treeCopy(n.right, root);
		} else if (n.left != Node.NULL_NODE && n.right == Node.NULL_NODE) {
			// left
			root.left = treeCopy(n.left, root);
		} else if (n.left == Node.NULL_NODE && n.right != Node.NULL_NODE) {
			// right
			root.right = this.treeCopy(n.right, root);
		}
		// all
		root.parent = parent;
		return root;
	}

	public void show() {
		if (this.display == null) {
			this.display = new DisplayableBinaryTree(this, 1000, 960);
		}
		this.display.display();
	}

	public void close() {
		if (this.display != null) {
			this.display.close();
		}
	}

}
