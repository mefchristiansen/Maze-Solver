package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;
import controller.MazeController;

import java.util.*;
import java.util.concurrent.ExecutionException;

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
			if (current == end) {
                maze.setGoal(current);
				return true;
			}

            publish(maze);

			Thread.sleep(mazeController.getAnimationSpeed());

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

		return false;
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
				mazeController.solveMazeSuccess();
			} else {
				mazeController.reset();
			}
		} catch (ExecutionException e) {
			mazeController.reset();
		}
		catch (Exception ignored) {
		}
	}

	private Cell unvisitedNeighbor(Cell currCell) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;
	    Cell nextCell;
	    Random rand = new Random();

	    for (Direction direction : Direction.values()) {
	        newRow = currRow + direction.dy;
	        newCol = currCol + direction.dx;

            if (!maze.inBounds(newRow, newCol)) {
                continue;
            }

	        nextCell = maze.mazeCell(newRow, newCol);

	        if (!nextCell.visited() && currCell.wallMissing(direction)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}
}
