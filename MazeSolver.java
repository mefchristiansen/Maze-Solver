import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MazeSolver {
	enum Direction {
	    UP(0,1), DOWN(0,-1), LEFT(-1,0), RIGHT(1,0);

	    private final int dx, dy;

	    private Direction(int dx, int dy) {
	        this.dx = dx;
	        this.dy = dy;
	    }
	}

	private Maze maze;
	private MazeDisplay mazeDisplay;

	public MazeSolver(Maze maze, MazeDisplay mazeDisplay) {
		this.maze = maze;
		this.mazeDisplay = mazeDisplay;
	}

	public void DFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
		Cell current, next;
		current = maze.mazeCell(rowStart, colStart);
		current.setVisited(true);
		current.setSolution(true);

		Stack<Cell> searchStack = new Stack<Cell>();

		while (current != null) {
			if (current.row() == rowEnd && current.col() == colEnd) {
				return;
			}

			Cell unvisitedNeighbor = unvisitedNeighbor(current);

			if (unvisitedNeighbor != null) {
			    searchStack.push(current);
			    current = unvisitedNeighbor;
			    current.setVisited(true);
			    current.setSolution(true);
			} else if (!searchStack.empty()) {
				current.setSolution(false);
			    current = searchStack.peek();
			    searchStack.pop();
			} else {
				current.setSolution(false);
			    current = null;
			}

			mazeDisplay.animate();
		}
	}

	private Cell unvisitedNeighbor(Cell currCell) {
	    List<Cell> unvisitedNeighbors = new ArrayList<Cell>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;
	    Cell nextCell;
	    Random rand = new Random();

	    for (Direction dir : Direction.values()) {
	        newRow = currRow + dir.dy;
	        newCol = currCol + dir.dx;

	        nextCell = maze.mazeCell(newRow, newCol);

	        if (maze.inBounds(newRow, newCol) && !nextCell.visited() && !currCell.wallPresent(dir.dx, dir.dy)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}

	// public Cell BFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
	// 	Queue<Cell> searchQueue = new LinkedList<Cell>();
	// 	boolean[][] visited = new boolean[numRows][numCols];
	// 	for (int r = 0; r < numRows; r++) {
	// 		for (int c = 0; c < numCols; c++) {
	// 			visited[r][c] = false;
	// 		}
	// 	}

	// 	int row, col, newRow, newCol;

	// 	Cell root = new Cell(rowStart, colStart, null);
	// 	searchQueue.add(root);
	// 	visited[rowStart][colStart] = true;

	// 	while(searchQueue.size() != 0) {
	// 		Cell topCell = searchQueue.peek();
	// 		searchQueue.remove();

	// 		row = topCell.row();
	// 		col = topCell.col();

 //            if (row == rowEnd && col == colEnd) {
 //                return topCell;
 //            }

	// 		for (int rowDir = -1; rowDir <= 1; rowDir++) {
	// 			for (int colDir = -1; colDir <= 1; colDir++) {
	// 				if ((rowDir == 0 || colDir == 0) && (rowDir != 0 || colDir != 0)) {
	// 					newRow = row + rowDir;
	// 					newCol = col + colDir;

	// 					if (inBounds(newRow, newCol) && maze[newRow][newCol] == ' ' && !visited[newRow][newCol]) {
	// 						visited[newRow][newCol] = true;
	// 						Cell child = new Cell(newRow, newCol, topCell);
	// 						searchQueue.add(child);
	// 					}
	// 				}
	// 			}
	// 		}
	// 	}

 //        return null;

	// }

}