package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;

import java.util.*;

public class AStar extends MazeSolver {
	public AStar(Maze maze) {
		super(maze);
	}

	@Override
	public void solve() {
		int rowStart = maze.startingCell.row();
		int colStart = maze.startingCell.col();
		int rowEnd = maze.endingCell.row();
		int colEnd = maze.endingCell.col();
		List<Cell> openSet = new ArrayList<>();
		Cell start = maze.mazeCell(rowStart, colStart);
		Cell end = maze.mazeCell(rowEnd, colEnd);
		Cell current;
		int currentIndex, tentativeG;

		start.setG(0);
		start.setF(heuristic(start, end));
		start.setVisiting(true);
		openSet.add(start);

		while (openSet.size() != 0) {
			currentIndex = lowestFIndex(openSet);
			current = openSet.get(currentIndex);

			current.setCurrent(true);

			fireStateChanged();

			if (current.row() == rowEnd && current.col() == colEnd) {
				goal = current;
				return;
			}

			openSet.remove(currentIndex);
			current.setVisiting(false);
			current.setVisited(true);

			List<Cell> unvisitedNeighbors = unvisitedNeighbors(current);

			if (unvisitedNeighbors != null) {
				for (Cell neighbor : unvisitedNeighbors) {
					if (!neighbor.visiting()) {
						openSet.add(neighbor);
						neighbor.setVisiting(true);
						neighbor.setParent(current);
					}

					tentativeG = current.getG() + 1;

					if (tentativeG >= neighbor.getG()) {
						continue;
					}

					neighbor.setG(tentativeG);
					neighbor.setF(neighbor.getG() + heuristic(neighbor, end));
				}
			}

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

	private int lowestFIndex(List<Cell> openSet) {
		int lowestIndex = 0;

		for (int i = 1; i < openSet.size(); i++) {
			if (openSet.get(i).getF() < openSet.get(lowestIndex).getF()) {
				lowestIndex = i;
			}
		}

		return lowestIndex;
	}

	// Manhattan Distance
	private int heuristic(Cell neighbor, Cell end) {
		return Math.abs(neighbor.row() - end.row()) + Math.abs(neighbor.col() - end.col());
	}
}