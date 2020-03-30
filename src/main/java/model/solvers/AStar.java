package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolver;
import controller.MazeController;

import java.util.*;

public class AStar extends MazeSolver {
	public AStar(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	public boolean solve() {
		List<Cell> openSet = new ArrayList<>();
		Cell start = maze.getStartingCell();
		Cell end = maze.getEndingCell();
		Cell current;
		int currentIndex, tentativeG;

		start.setG(0);
		start.setF(heuristic(start, end));
		start.setVisiting(true);
		openSet.add(start);

		while (openSet.size() != 0) {
			if (mazeController.isInterrupted()) {
				return false;
			}

			currentIndex = lowestFIndex(openSet);
			current = openSet.get(currentIndex);

			current.setCurrent(true);

			fireStateChanged();

			if (current == end) {
				goal = current;
				return true;
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

		return false;
	}

	private List<Cell> unvisitedNeighbors(Cell currCell) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;
	    Cell nextCell;

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