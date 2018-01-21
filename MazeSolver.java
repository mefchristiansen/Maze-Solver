import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MazeSolver {
    enum Dir {
        N(0, -1), S(0, 1), E(1, 0), W(-1, 0);

        final int dx;
        final int dy;
        
        Dir(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }    
    };

	private final int numRows;
	private final int numCols;
	private char[][] maze;
	LinkedList<Node> solution;

	public static void main(String[] args) {
		MazeSolver mazeSolver = new MazeSolver(11);

        Node end;
        end = mazeSolver.BFSSolve(1,1,9,9);
        end = mazeSolver.DFSSolve(1,1,9,9);

	}

	public MazeSolver(int size) {
		numRows = size;
		numCols = size;

		initMaze();

        printMaze();

		// initGUI();
	}

	// private void initGUI() {
	// 	JFrame frame = new JFrame("Java Mazes");
 //        JPanel container = new JPanel();
 //        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
 //        frame.setContentPane(container);
 //        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 //        frame.pack();
 //        frame.setLocationRelativeTo(null);
 //        frame.setVisible(true);
	// }

    private void initMaze() {
        maze = new char[numRows][numCols];

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = 'O';
            }
        }

        Random rand = new Random();

        int r = rand.nextInt(numRows);
        while (r % 2 == 0) {
            r = rand.nextInt(numRows);
        }
        int c = rand.nextInt(numCols);
        while (c % 2 == 0) {
            c = rand.nextInt(numCols);
        }

        maze[r][c] = ' ';

        System.out.println("Start. R: " + r + ", C: " + c);

        generateMaze(r, c);

    }

    private void generateMaze(int r, int c) {
        Dir[] dirs = Dir.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (Dir dir : dirs) {
            int nc = c + dir.dx * 2;
            int nr = r + dir.dy * 2;
            if (inBounds(nr, nc) && maze[nr][nc] != ' ') {
                maze[nr - dir.dy][nc - dir.dx] = ' ';
                maze[nr][nc] = ' ';

                // printMaze();

                // System.exit(0);

                generateMaze(nr, nc);
            }
        }
    }

	public Node BFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
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

            if (row == rowEnd && col == colEnd) {
                return topNode;
            }

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

        return null;

	}

    public Node DFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
        Stack<Node> searchStack = new Stack<Node>();
        boolean[][] visited = new boolean[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                visited[r][c] = false;
            }
        }

        int row, col, newRow, newCol;

        Node root = new Node(rowStart, colStart, null);
        searchStack.add(root);
        visited[rowStart][colStart] = true;

        while(searchStack.empty()) {
            Node topNode = searchStack.peek();
            searchStack.pop();

            row = topNode.row();
            col = topNode.col();

            if (row == rowEnd && col == colEnd) {
                return topNode;
            }

            for (int rowDir = -1; rowDir <= 1; rowDir++) {
                for (int colDir = -1; colDir <= 1; colDir++) {
                    if ((rowDir == 0 || colDir == 0) && (rowDir != 0 || colDir != 0)) {
                        newRow = row + rowDir;
                        newCol = col + colDir;

                        if (inBounds(newRow, newCol) && maze[newRow][newCol] == ' ' && !visited[newRow][newCol]) {
                            visited[newRow][newCol] = true;
                            Node child = new Node(newRow, newCol, topNode);
                            searchStack.push(child);
                        }
                    }
                }
            }
        }

        return null;
    }

	// public void DFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
	// 	boolean[][] visited = new boolean[numRows][numCols];
	// 	for (int r = 0; r < numRows; r++) {
	// 		for (int c = 0; c < numCols; c++) {
	// 			visited[r][c] = false;
	// 		}
	// 	}

	// 	Node root = new Node(rowStart, colStart, null);

	// 	Stack<Node> pathStack = new Stack<Node>();

	// 	DFS(root, visited, pathStack, rowEnd, colEnd);
	// }

	// public void DFS(Node root, boolean[][] visited, Stack<Node> pathStack, int rowEnd, int colEnd) {
	// 	if (root == null) {
	// 		return;
	// 	}

	// 	int row = root.row();
	// 	int col = root.col();

 //        if (row == rowEnd && col == colEnd) {
 //            return;
 //        }

	// 	int newRow, newCol;

	// 	visited[row][col] = true;

	// 	pathStack.push(root);

	// 	for (int rowDir = -1; rowDir <= 1; rowDir++) {
	// 		for (int colDir = -1; colDir <= 1; colDir++) {
	// 			if ((rowDir == 0 || colDir == 0) && (rowDir != 0 || colDir != 0)) {
	// 				newRow = row + rowDir;
	// 				newCol = col + colDir;

	// 				if (inBounds(newRow, newCol) && maze[newRow][newCol] == ' ' && !visited[newRow][newCol]) {
	// 					Node child = new Node(newRow, newCol, root);
	// 					DFS(child, visited, pathStack, rowEnd, colEnd);
	// 				}
	// 			}
	// 		}
	// 	}

 //        pathStack.pop();
	// }

	private boolean inBounds(int row, int col) {
		return row >= 0 && col >= 0 && row < numRows && col < numCols;
	}

    private void printMaze() {
        for (int r = 0; r < numRows; r++) {
            System.out.println();
            for (int c = 0; c < numCols; c++) {
                System.out.print(maze[r][c]);
            }
        }
    }

}
