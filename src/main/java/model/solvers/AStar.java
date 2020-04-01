package model.solvers;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeSolverWorker;
import controller.MazeController;

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class AStar extends MazeSolverWorker {
	public AStar(Maze maze, MazeController mazeController) {
		super(maze, mazeController);
	}

	@Override
	protected Boolean doInBackground() throws Exception {
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
			currentIndex = lowestFIndex(openSet);
			current = openSet.get(currentIndex);

			current.setCurrent(true);

            publish(maze);

			Thread.sleep(mazeController.getAnimationSpeed());

			if (current == end) {
				maze.setGoal(current);
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

		} catch (CancellationException e) {
//            mazeController.setInstructions("Generate a new Maze");
//            mazeController.reset();
		} catch (InterruptedException e) {
//            mazeController.reset();
		} catch (ExecutionException e) {
//            mazeController.reset();
		}
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