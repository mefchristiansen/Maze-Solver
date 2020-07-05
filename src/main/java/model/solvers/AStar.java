package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Cell.CellVisitState;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

/**
 * A SwingWorker class (extending MazeGeneratorWorker) that implements the A* graph traversal and path finding
 * algorithm. A* aims to find the path to the end point with the smallest cost (in this case, the shortest distance).
 * At each iteration, the algorithm chooses the path that minimizes a cost function: f(n) = g(n) + h(n), where g(n) is
 * the cost to the current node from the start node and h(n) is the estimate of the remaining cost to the end node using a heuristic. In
 * this case, the heuristic (i.e. estimated cost), is the Manhattan Distance between the current and end node. Unlike
 * BFS and DFS, A* is an informed search algorithm, meaning that it knows where the end node is, and uses this knowledge
 * to make an informed guess as to which path is most efficient.
 * <p>
 * https://en.wikipedia.org/wiki/A*_search_algorithm
 */
public class AStar extends MazeSolverWorker {
	public AStar(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	protected Boolean doInBackground() throws Exception {
		/*
			The open set is the list of nodes that the algorithm can choose to traverse next
		 */
		List<Cell> openSet = new ArrayList<>();

		Cell start, end, current;
		int tentativeG;

		start = maze.getStartingCell();
		end = maze.getEndingCell();

		start.setGCost(0);
		start.setHCost(manhattan_distance(start, end));
		start.setFCost(start.getGCost() + start.getHCost());
		openSet.add(start);

		while (!openSet.isEmpty()) {
			current = lowestFScoreCell(openSet); // Pick the node with the lowest estimated cost
			current.setCurrent(true);
			current.setVisitState(CellVisitState.VISITED);

			if (current == end) { // Check if the current cell is the goal cell (i.e. maze has been solved)
				maze.setGoal(current);
				return true;
			}

			List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

			/*
            	Add each valid unvisited neighboring cell to the open set list to be visited later, and calculate its
            	g (the cost to the current node from the start node) and h (the estimate of the remaining cost to the
            	end node) cost to calculate f (the path's estimated total cost).
             */
			for (Cell neighbor : unvisitedNeighbors) {
				openSet.add(neighbor);
				neighbor.setVisitState(CellVisitState.VISITING);
				neighbor.setParent(current);

				tentativeG = current.getGCost() + 1;

				/*
					If the cost to the neighbor via this path is greater then a previously traversed path,
					then the previous path was better and we can ignore this new path.
				 */
				if (tentativeG >= neighbor.getGCost()) {
					continue;
				}

				neighbor.setGCost(tentativeG);
				neighbor.setHCost(manhattan_distance(neighbor, end));
				neighbor.setFCost(neighbor.getGCost() + neighbor.getHCost());
			}

			publish(maze); // Publish the current maze state to be repainted on the event dispatch thread

			Thread.sleep(mazeController.getAnimationSpeed());

			current.setCurrent(false);
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
	 * Iterates through all neighbours of the currently visited cell (up, down left, right), and returns all of the
	 * valid neighboring cells that have not already been visited.
	 *
	 * @param current The current cell
	 * @return A list of valid (i.e. in bounds) neighboring cells that have not already been visited
	 */
	private List<Cell> unvisitedNeighbors(Cell current) {
		Cell neighbor;
		List<Cell> unvisitedNeighbors = new ArrayList<>();
		int currRow = current.row();
		int currCol = current.col();
		int newRow, newCol;

		for (Direction direction : Direction.values()) {
			newRow = currRow + direction.dy;
			newCol = currCol + direction.dx;

			if (!maze.inBounds(newRow, newCol)) {
				continue;
			}

			neighbor = maze.mazeCell(newRow, newCol);

			if (current.wallMissing(direction) && neighbor.unvisited()) {
				unvisitedNeighbors.add(neighbor);
			}
		}

		return unvisitedNeighbors;
	}

	/**
	 * Returns the cell from the open set with the lowest f score (the path's estimated total cost)
	 *
	 * @param openSet The current open set
	 * @return The cell from the open set with the lowest f cost
	 */
	private Cell lowestFScoreCell(List<Cell> openSet) {
		Cell lowestFScoreCell = openSet.get(0);
		int lowestFScore = lowestFScoreCell.getFCost();
		Cell cell;
		int cellFScore;

		for (int i = 1; i < openSet.size(); i++) {
			cell = openSet.get(i);
			cellFScore = cell.getFCost();

			if (cellFScore < lowestFScore) {
				lowestFScoreCell = cell;
				lowestFScore = cellFScore;
			}
		}

		openSet.remove(lowestFScoreCell);

		return lowestFScoreCell;
	}

	/**
	 * Calculates the Manhattan Distance (https://en.wikipedia.org/wiki/Taxicab_geometry) between a cell and the maze
	 * ending cell
	 *
	 * @param cell A cell
	 * @param end  The maze ending
	 * @return The Manhattan Distance between the two cells
	 */
	private int manhattan_distance(Cell cell, Cell end) {
		return Math.abs(cell.row() - end.row()) + Math.abs(cell.col() - end.col());
	}
}
