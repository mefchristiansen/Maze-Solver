public class Node {
	int row;
	int col;
	Node parent;

	public Node(int row, int col, Node parent) {
		this.row = row;
		this.col = col;
		this.parent = parent;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public Node parent() {
		return parent;
	}

	public void printNode() {
		System.out.println("Node. X: " + col + ", Y: " + row);
	}
}