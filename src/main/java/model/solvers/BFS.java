package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;
import controller.MazeController;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class BFS extends MazeSolver {
	public BFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	public boolean solve() {
		Cell current;
		Queue<Cell> searchQueue = new LinkedList<>();
		searchQueue.add(maze.startingCell);

		while (searchQueue.size() != 0) {
			if (mazeController.isInterrupted()) {
				return false;
			}

			current = searchQueue.remove();

			current.setVisited(true);
			current.setCurrent(true);

			fireStateChanged();

			if (current == maze.endingCell) {
				goal = current;
				return true;
			}

			List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

			if (unvisitedNeighbors != null) {
				for (Cell neighbor : unvisitedNeighbors) {
					if (!neighbor.visiting()) {
						searchQueue.add(neighbor);
						neighbor.setVisiting(true);
						neighbor.setParent(current);
					}
				}
			}

			current.setVisiting(false);
			current.setCurrent(false);
		}

		return false;
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

	        if (!nextCell.visited() && !currCell.wallPresent(direction)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    return unvisitedNeighbors.size() == 0 ? null : unvisitedNeighbors;
	}
}