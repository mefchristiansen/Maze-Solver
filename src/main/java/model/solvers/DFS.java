package model.solvers;

import controller.MazeController;
import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;

import java.util.*;

public class DFS extends MazeSolver {
	public DFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	public boolean solve() {
		int rowStart = maze.startingCell.row();
		int colStart = maze.startingCell.col();
		int rowEnd = maze.endingCell.row();
		int colEnd = maze.endingCell.col();
		Cell current, next;

		current = maze.mazeCell(rowStart, colStart);
		current.setVisited(true);
		current.setCurrent(true);

		Stack<Cell> searchStack = new Stack<>();

		while (current != null) {
			if (mazeController.isInterrupted()) {
				return false;
			}

			if (current.row() == rowEnd && current.col() == colEnd) {
				goal = current;
				return true;
			}

			fireStateChanged();

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

	private Cell unvisitedNeighbor(Cell currCell) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
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
}