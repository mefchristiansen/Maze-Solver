import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MazeSolver {
	public static final int VISITED = 1;

	private final int numRows;
	private final int numCols;
	private char[][] maze;
	LinkedList<Node> solution;

	public static void main(String[] args) {
		MazeSolver mazeSolver = new MazeSolver();

	}

	public MazeSolver() {
		numRows = 5;
		numCols = 5;

		maze = new char[numRows][numCols];

		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				maze[r][c] = ' ';
			}
		}

		initGUI();
	}

	private void initGUI() {
		// JFrame frame = new JFrame("Maze Solver");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setBounds(50, 50, 200, 200);
		// frame.setVisible(true);
	}


	public void BFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
		Queue<Node> searchQueue = new LinkedList<Node>();
		boolean[][] visited = new boolean[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				visited[r][c] = false;
			}
		}

		int row, col, newRow, newCol;

		Node root = new Node(rowStart, colStart, null);
		searchQueue.add(root);
		visited[rowStart][colStart] = true;

		while(searchQueue.size() != 0) {
			Node topNode = searchQueue.peek();
			searchQueue.remove();

			row = topNode.row();
			col = topNode.col();

			topNode.printNode();

			for (int rowDir = -1; rowDir <= 1; rowDir++) {
				for (int colDir = -1; colDir <= 1; colDir++) {
					if ((rowDir == 0 || colDir == 0) && (rowDir != 0 || colDir != 0)) {
						newRow = row + rowDir;
						newCol = col + colDir;

						if (inBounds(newRow, newCol) && maze[newRow][newCol] == ' ' && !visited[newRow][newCol]) {
							visited[newRow][newCol] = true;
							Node child = new Node(newRow, newCol, topNode);
							searchQueue.add(child);
						}
					}
				}
			}
		}

	}

	public void DFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
		boolean[][] visited = new boolean[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				visited[r][c] = false;
			}
		}

		Node root = new Node(rowStart, colStart, null);

		Stack<Node> pathStack = new Stack<Node>();

		DFS(root, visited, pathStack);
	}

	public void DFS(Node root, boolean[][] visited, Stack<Node> pathStack) {
		if (root == null) {
			return;
		}

		int row = root.row();
		int col = root.col();
		int newRow, newCol;

		root.printNode();

		visited[row][col] = true;

		pathStack.push(root);

		for (int rowDir = -1; rowDir <= 1; rowDir++) {
			for (int colDir = -1; colDir <= 1; colDir++) {
				if ((rowDir == 0 || colDir == 0) && (rowDir != 0 || colDir != 0)) {
					newRow = row + rowDir;
					newCol = col + colDir;

					if (inBounds(newRow, newCol) && maze[newRow][newCol] == ' ' && !visited[newRow][newCol]) {
						Node child = new Node(newRow, newCol, root);
						DFS(child, visited, pathStack);
					}
				}
			}
		}
	}

	private boolean inBounds(int row, int col) {
		return row >= 0 && col >= 0 && row < numRows && col < numCols;
	}



}