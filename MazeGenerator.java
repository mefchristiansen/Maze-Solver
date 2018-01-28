import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
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

	public MazeGenerator(Maze maze, MazeDisplay mazeDisplay) {
		this.maze = maze;
		this.mazeDisplay = mazeDisplay;
	}

	public void generateMaze() {
	    Random rand = new Random();

	    int startRow = rand.nextInt(maze.numRows() - 1);
	    int startCol = rand.nextInt(maze.numCols() - 1);
	    maze.setStartCell(startRow, startCol);

	    Cell current, next;
	    current = maze.mazeCell(startRow, startCol);
	    current.setVisited(true);

	    Stack<Cell> searchStack = new Stack<Cell>();

	    while (current != null) {
	        Cell unvisitedNeighbor = unvisitedNeighbor(current, rand);

	        if (unvisitedNeighbor != null) {
	            searchStack.push(current);
	            removeWalls(current, unvisitedNeighbor);

	            mazeDisplay.animate();

	            current = unvisitedNeighbor;
	            current.setVisited(true);

	        } else if (!searchStack.empty()) {
	            current = searchStack.peek();
	            searchStack.pop();
	        } else {
	            current = null;
	        }
	    }
	}

	private Cell unvisitedNeighbor(Cell currCell, Random rand) {
	    List<Cell> unvisitedNeighbors = new ArrayList<Cell>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;

	    for (Direction dir : Direction.values()) {
	        newRow = currRow + dir.dy;
	        newCol = currCol + dir.dx;

	        if (maze.inBounds(newRow, newCol) && !maze.mazeCell(newRow, newCol).visited()) {
	            unvisitedNeighbors.add(maze.mazeCell(newRow, newCol));
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}

	private void removeWalls(Cell current, Cell next) {
	    int x = current.col() - next.col();

	    if (x == 1) {
	        current.removeWall(2);
	        next.removeWall(3);
	        return;
	    } else if (x == -1) {
	        current.removeWall(3);
	        next.removeWall(2);
	        return;
	    }

	    int y = current.row() - next.row();

	    if (y == 1) {
	        current.removeWall(0);
	        next.removeWall(1);
	        return;
	    } else if (y == -1) {
	        current.removeWall(1);
	        next.removeWall(0);
	        return;
	    }
	}
	
}