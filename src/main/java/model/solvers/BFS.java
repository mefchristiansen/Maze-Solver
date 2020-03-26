package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;

import java.util.*;

public class BFS extends MazeSolver {
	public BFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	public boolean solve() {
		int rowStart = maze.startingCell.row();
		int colStart = maze.startingCell.col();
		int rowEnd = maze.endingCell.row();
		int colEnd = maze.endingCell.col();
		Cell current;

		Queue<Cell> searchQueue = new LinkedList<>();
		searchQueue.add(maze.mazeCell(rowStart, colStart));

		while (searchQueue.size() != 0) {
			if (mazeController.isInterrupted()) {
				return false;
			}

			current = searchQueue.remove();

			current.setVisited(true);
			current.setCurrent(true);

			fireStateChanged();

			if (current.row() == rowEnd && current.col() == colEnd) {
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
}