package model.generators;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeGeneratorWorker;
import controller.MazeController;

import java.util.*;

public class RecursiveBacktracker extends MazeGeneratorWorker {
	public RecursiveBacktracker(Maze maze, MazeController mazeController) {
	    super(maze, mazeController);
	}

	@Override
	protected Boolean doInBackground() throws Exception {
	    Random rand = new Random();

	    int startRow = rand.nextInt(maze.numRows() - 1);
	    int startCol = rand.nextInt(maze.numCols() - 1);

	    Cell current;
	    current = maze.mazeCell(startRow, startCol);
	    current.setCurrent(true);
	    current.setVisited(true);

	    Stack<Cell> searchStack = new Stack<>();

	    while (current != null) {
	        Cell unvisitedNeighbor = unvisitedNeighbor(current, rand);

	        if (unvisitedNeighbor != null) {
	            searchStack.push(current);

	            removeWalls(current, unvisitedNeighbor);

	            current.setCurrent(false);
	            current = unvisitedNeighbor;
	            current.setCurrent(true);
	            current.setVisited(true);
	        } else if (!searchStack.empty()) {
	        	current.setCurrent(false);
	            current = searchStack.pop();
	            current.setCurrent(true);
	        } else {
	        	current.setCurrent(false);
	            current = null;
	        }

	        publish(maze);

            Thread.sleep(mazeController.getAnimationSpeed());
		}

		maze.voidVisits();
	    return true;
	}

    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }

    @Override
    protected void done() {
	    try {
            Boolean status = get();

            if (status) {
                mazeController.generateMazeSuccess();
            } else {
                mazeController.reset();
            }
        } catch (Exception e) {
	    	// LOG
	    	return;
        }
    }

    private Cell unvisitedNeighbor(Cell currCell, Random rand) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
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
	    Direction direction = current.directionToCell(next);
        current.removeWall(direction);
        next.removeWall(direction.oppositeDirection());
	}
}
