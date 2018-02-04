import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Queue;
import java.util.LinkedList;

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
	private int rowStart, colStart, rowEnd, colEnd;
	private String solutionMethod;

	public MazeSolver(Maze maze, MazeDisplay mazeDisplay, String solutionMethod) {
		this.maze = maze;
		this.mazeDisplay = mazeDisplay;
		this.solutionMethod = solutionMethod;
		mazeDisplay.setDisplayState("solve");
	}

	public void solve() {
		this.rowStart = this.colStart = 0;
		this.rowEnd = maze.numRows() - 1;
		this.colEnd = maze.numCols() - 1;
		Cell end = null;

		if (solutionMethod == "DFS") {
			end = DFSSolve(rowStart, colStart, rowEnd, colEnd);
		} else if (solutionMethod == "BFS") {
			end = BFSSolve(rowStart, colStart, rowEnd, colEnd);
		}

		animateSolutionPath(end);
	}

	public Cell DFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
		Cell current, next;
		current = maze.mazeCell(rowStart, colStart);
		current.setVisited(true);
		current.setCurrent(true);

		Stack<Cell> searchStack = new Stack<Cell>();

		while (current != null) {
			if (current.row() == rowEnd && current.col() == colEnd) {
				return current;
			}

			mazeDisplay.solveAnimate();

			Cell unvisitedNeighbor = unvisitedNeighbor(current);

			if (unvisitedNeighbor != null) {
			    searchStack.push(current);
			    current.setVisiting(true);
			    current.setCurrent(false);
			    next = unvisitedNeighbor;
			    next.setParent(current);
			    current = next;
			    current.setVisited(true);
			    current.setCurrent(true);
			} else if (!searchStack.empty()) {
				current.setCurrent(false);
				current.setVisiting(false);
				current.setParent(null);
			    current = searchStack.pop();
			    current.setCurrent(true);
			} else {
				current.setCurrent(false);
				current.setVisiting(false);
			    current = null;
			}
		}

		return current;
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

	public Cell BFSSolve(int rowStart, int colStart, int rowEnd, int colEnd) {
		Cell current, next;
		current = null;

		Queue<Cell> searchQueue = new LinkedList<Cell>();
		searchQueue.add(maze.mazeCell(rowStart, colStart));

		while (searchQueue.size() != 0) {
			current = searchQueue.remove();

			current.setVisited(true);
			current.setCurrent(true);

			mazeDisplay.solveAnimate();

			if (current.row() == rowEnd && current.col() == colEnd) {
				return current;
			}

			List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

			if (unvisitedNeighbors != null) {
				for (Cell neighbor : unvisitedNeighbors) {
					searchQueue.add(neighbor);
					neighbor.setVisiting(true);
					neighbor.setParent(current);
				}
			}

			current.setVisiting(false);
			current.setCurrent(false);
		}

		return current;
	}

	private List<Cell> unvisitedNeighbors(Cell currCell) {
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

	    return unvisitedNeighbors.size() == 0 ? null : unvisitedNeighbors;
	}

	private void aStar(int rowStart, int colStart, int rowEnd, int colEnd) {

	}

	private void animateSolutionPath(Cell end) {
		while (end != null) {
			end.setSolution(true);
			end = end.parent();
			mazeDisplay.solutionAnimate();
		}
	}

}