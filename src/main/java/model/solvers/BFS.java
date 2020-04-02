package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;
import controller.MazeController;

import java.util.*;
import java.util.concurrent.ExecutionException;

public class BFS extends MazeSolverWorker {
	public BFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

    @Override
    protected Boolean doInBackground() throws Exception {
        Cell current;
        Cell end = maze.getEndingCell();
        Queue<Cell> searchQueue = new LinkedList<>();
        searchQueue.add(maze.getStartingCell());

        while (searchQueue.size() != 0) {
            current = searchQueue.remove();

            current.setVisited(true);
            current.setCurrent(true);

            publish(maze);

            Thread.sleep(mazeController.getAnimationSpeed());

            if (current == end) {
                maze.setGoal(current);
                return true;
            }

            List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

			for (Cell neighbor : unvisitedNeighbors) {
				if (!neighbor.visiting()) {
					searchQueue.add(neighbor);
					neighbor.setVisiting(true);
					neighbor.setParent(current);
				}
			}

            current.setVisiting(false);
            current.setCurrent(false);
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

	private List<Cell> unvisitedNeighbors(Cell currCell) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;
	    Cell nextCell;

	    for (Direction direction : Direction.values()) {
            newCol = currCol + direction.dx;
	        newRow = currRow + direction.dy;

	        if (!maze.inBounds(newRow, newCol)) {
	            continue;
            }

	        nextCell = maze.mazeCell(newRow, newCol);

	        if (!nextCell.visited() && currCell.wallMissing(direction)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    return unvisitedNeighbors;
	}
}
