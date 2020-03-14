package model;

import java.util.*;

public class BFS extends MazeSolver {
	public BFS(Maze maze) {
		this.maze = maze;
	}

	public void solve(int rowStart, int colStart, int rowEnd, int colEnd) {
		Cell current;
		current = null;

		Queue<Cell> searchQueue = new LinkedList<>();
		searchQueue.add(maze.mazeCell(rowStart, colStart));

		while (searchQueue.size() != 0) {
			current = searchQueue.remove();

			current.setVisited(true);
			current.setCurrent(true);

			setChanged();
			notifyObservers();

			if (current.row() == rowEnd && current.col() == colEnd) {
				goal = current;
				return;
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

		return;
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