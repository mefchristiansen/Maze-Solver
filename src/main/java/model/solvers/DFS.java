package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;
import controller.MazeController;

import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DFS extends MazeSolver {
	public DFS(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	public boolean solve() {
		Cell current, next;

		current = maze.startingCell;
		current.setVisited(true);
		current.setCurrent(true);

		Stack<Cell> searchStack = new Stack<>();

		while (current != null) {
			if (mazeController.isInterrupted()) {
				return false;
			}

			if (current == maze.endingCell) {
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

	    for (Direction direction : Direction.values()) {
	        newRow = currRow + direction.dy;
	        newCol = currCol + direction.dx;

            if (!maze.inBounds(newRow, newCol)) {
                continue;
            }

	        nextCell = maze.mazeCell(newRow, newCol);

	        if (!nextCell.visited() && !currCell.wallPresent(direction)) {
	            unvisitedNeighbors.add(nextCell);
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}
}