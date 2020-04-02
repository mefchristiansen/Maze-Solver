package model.generators;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeGeneratorWorker;
import controller.MazeController;

import java.util.*;
import java.util.concurrent.CancellationException;

/**
 * A SwingWorker class (extending MazeGeneratorWorker) that implements the recursive backtracker maze generation
 * algorithm. This algorithm is a DFS implementation which randomly picks an unvisited neighbouring cell at each
 * iteration to visit to construct the maze.
 *
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Recursive_backtracker
 */
public class RecursiveBacktracker extends MazeGeneratorWorker {
	public RecursiveBacktracker(Maze maze, MazeController mazeController) {
	    super(maze, mazeController);
	}

	/**
	 * Override of the SwingWorker doInBackground method, executing the recursive backtracker in a background thread.
	 * This method returns a Boolean class type indicating if the maze generation was a success, and publishes
	 * Maze instances at each iteration of the generation to be repainted in the view asynchronously.
	 *
	 * @return The success of maze generation
	 * @throws Exception Any exception thrown by the thread sleep
	 */
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
	        Cell unvisitedNeighbor = unvisitedNeighbor(current);

	        if (unvisitedNeighbor != null) { // There is an unvisited neighbouring cells to visit from the current cell.
	            searchStack.push(current);

	            removeWalls(current, unvisitedNeighbor);

	            current.setCurrent(false);
	            current = unvisitedNeighbor;
	            current.setCurrent(true);
	            current.setVisited(true);
	        } else if (!searchStack.empty()) {
	        	/*
	        		There are no unvisited neighbouring cells for the current cell. Backtrack to the cell that was
	        		visited previously to find an unvisited neighbouring cell from there.
	        	 */
	        	current.setCurrent(false);
	            current = searchStack.pop();
	            current.setCurrent(true);
	        } else { // All cells have been visited
	        	current.setCurrent(false);
	            current = null;
	        }

	        publish(maze); // Publish the current maze state to be repainted on the event dispatch thread.

            Thread.sleep(mazeController.getAnimationSpeed());
		}

		maze.voidVisits();
	    return true;
	}

	/**
	 * Override of the SwingWorker process function for the recursive backtracker SwingWorker, which repaints the maze
	 * at every iteration of maze generation asynchronously on the event dispatch thread.
	 */
    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }

	/**
	 * Override of the SwingWorker done function (run after the thread is completed) for the recursive backtracker
	 * SwingWorker. If the maze was successfully generated, this will call the generateMazeSuccess method defined in the
	 * controller. In the case of this SwingWorker being interrupted or cancelled, it will have been done by the maze
	 * reset function in the controller, and any clean-up will be handled there. For other exceptions, these will not
	 * have been triggered by the maze reset function, so that will trigger the maze reset function to clean up.
	 */
    @Override
    protected void done() {
		Boolean status;

		try {
			status = get();

			if (status) {
				mazeController.generateMazeSuccess();
			} else {
				mazeController.reset();
			}
		} catch (CancellationException ignore) {
		} catch (Exception e) {
			mazeController.reset();
		}
    }

	/**
	 * Iterates through all neighbours of the currently visited cell (up, down left, right), and returns a randomly
	 * picked valid neighbouring cell that has not already been visited by the recursive backtracker.
	 *
	 * @param current The current cell being visited by the recursive backtracker
	 * @return A randomly picked valid (i.e. in bounds) neighbouring cell that has not already been visited
	 */
    private Cell unvisitedNeighbor(Cell current) {
		Random rand = new Random();
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = current.row();
	    int currCol = current.col();
	    int newRow, newCol;

	    for (Direction dir : Direction.values()) {
	        newRow = currRow + dir.dy;
	        newCol = currCol + dir.dx;

	        // Check that the cell is in the bounds of the maze, and that it hasn't already been visited
	        if (maze.inBounds(newRow, newCol) && !maze.mazeCell(newRow, newCol).visited()) {
	            unvisitedNeighbors.add(maze.mazeCell(newRow, newCol));
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}

	/**
	 * Removes the wall between two neighbouring cells for each cell. As the cells are neighbouring, each cell has to
	 * remove the wall in the opposite direction to the other. For example, if the neighbouring cell is to the right of
	 * the current cell, then the current cell needs to remove its right wall, whereas the neighbouring cell needs to
	 * remove it left wall.
	 *
	 * @param current A cell
	 * @param neighbour A neighbouring cell
	 */
	private void removeWalls(Cell current, Cell neighbour) {
	    Direction direction = current.directionToCell(neighbour);
        current.removeWall(direction);
        neighbour.removeWall(direction.oppositeDirection());
	}
}
