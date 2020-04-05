package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;
import controller.MazeController;

import java.util.*;
import java.util.concurrent.CancellationException;

/**
 * A SwingWorker class (extending MazeGeneratorWorker) that implements the DFS graph traversal algorithm. This algorithm
 * traverses a graph by starting at the root node (in this case the starting cell), and explores as far as possible
 * along a specific branch before backtracking. This uses the opposite strategy compared to BFS. This algorithm can be
 * implemented either recursively or iteratively (I implemented it using the iterative approach). The algorithm uses a
 * Stack to keep track of all the previously visited nodes which it will pop off when backtracking.
 *
 * https://en.wikipedia.org/wiki/Depth-first_search
 */
public class DFS extends MazeSolverWorker {
	public DFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		Cell current, next, end;

		current = maze.getStartingCell();
		current.setVisited(true);
		current.setCurrent(true);

		end = maze.getEndingCell();

		Stack<Cell> searchStack = new Stack<>();

		while (current != null) {
			if (current == end) { // Check if the current cell is the goal cell (i.e. maze has been solved)
                maze.setGoal(current);
				return true;
			}

            publish(maze); // Publish the current maze state to be repainted on the event dispatch thread.

			Thread.sleep(mazeController.getAnimationSpeed());

			Cell unvisitedNeighbor = unvisitedNeighbor(current);

			if (unvisitedNeighbor != null) { // There is an unvisited neighboring cells to visit from the current cell.
				searchStack.push(current);
				current.setVisiting(true);
				current.setCurrent(false);
				next = unvisitedNeighbor;
				next.setParent(current);
				current = next;
				current.setVisited(true);
				current.setCurrent(true);
			} else if (!searchStack.empty()) {
				/*
	        		There are no unvisited neighboring cells for the current cell. Backtrack to the cell that was
	        		visited previously to find an unvisited neighboring cell from there.
	        	 */

				current.setCurrent(false);
				current.setVisiting(false);
				current.setParent(null);
				current = searchStack.pop();
				current.setCurrent(true);
			} else { // All cells have been visited
				current.setCurrent(false);
				current.setVisiting(false);
				current = null;
			}
		}

		return false;
	}

	/**
	 * Override of the SwingWorker process function, which repaints the maze at every iteration of maze solving
	 * asynchronously on the event dispatch thread.
	 */
	@Override
	protected void process(List<Maze> chunks) {
		for (Maze maze : chunks) {
			mazeController.repaintMaze(maze);
		}
	}

	/**
	 * Override of the SwingWorker done function (run after the thread is completed). If the maze was successfully
	 * solved, this will call the solveMazeSuccess method defined in the controller, which will trigger the walking of
	 * the solution path. In the case of this SwingWorker being interrupted or cancelled, it will have been done by the
	 * maze reset function in the controller, and any clean-up will be handled there. For other exceptions, these will
	 * not have been triggered by the maze reset function, so that will trigger the maze reset function to clean up.
	 */
	@Override
	protected void done() {
		Boolean status;

		try {
			status = get();

			if (status) {
				mazeController.solveMazeSuccess();
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
	 * picked valid neighboring cell that has not already been visited.
	 *
	 * @param current The current cell
	 * @return A randomly picked valid (i.e. in bounds) neighboring cell that has not already been visited
	 */
	private Cell unvisitedNeighbor(Cell current) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = current.row();
	    int currCol = current.col();
	    int newRow, newCol;
	    Cell nextCell;
	    Random rand = new Random();

	    for (Direction direction : Direction.values()) {
	        newRow = currRow + direction.dy;
	        newCol = currCol + direction.dx;

			// Check that the cell is in the bounds of the maze
            if (!maze.inBounds(newRow, newCol)) {
                continue;
            }

	        nextCell = maze.mazeCell(newRow, newCol);

            // Check that the cell hasn't already been visited and that a wall doesn't exist in that direction
	        if (!nextCell.visited() && current.wallMissing(direction)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}
}
