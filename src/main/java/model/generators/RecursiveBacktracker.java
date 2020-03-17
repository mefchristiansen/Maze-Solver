package model.generators;

import model.Cell;
import model.Direction;
import model.Maze;
import model.MazeGenerator;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class RecursiveBacktracker extends MazeGenerator {
	public RecursiveBacktracker(Maze maze) {
	    super(maze);
	}

	@Override
	public void generateMaze() {
	    Random rand = new Random();

	    int startRow = rand.nextInt(maze.numRows() - 1);
	    int startCol = rand.nextInt(maze.numCols() - 1);

	    Cell current;
	    current = maze.mazeCell(startRow, startCol);
	    current.setCurrent(true);
	    current.setVisited(true);

	    Stack<Cell> searchStack = new Stack<>();

	    while (current != null) {
	        Cell unvisitedNeighbor = unvisitedNeighbor(current, rand);

	        if (unvisitedNeighbor != null) {
	            searchStack.push(current);

	            removeWalls(current, unvisitedNeighbor);

	            current.setCurrent(false);
	            current = unvisitedNeighbor;
	            current.setCurrent(true);
	            current.setVisited(true);
	        } else if (!searchStack.empty()) {
	        	current.setCurrent(false);
	            current = searchStack.pop();
	            current.setCurrent(true);
	        } else {
	        	current.setCurrent(false);
	            current = null;
	        }

            // Send event to observers that the maze has been updated.
            fireStateChanged();
	    }
	}

	private Cell unvisitedNeighbor(Cell currCell, Random rand) {
	    List<Cell> unvisitedNeighbors = new ArrayList<>();
	    int currRow = currCell.row();
	    int currCol = currCell.col();
	    int newRow, newCol;

	    for (Direction dir : Direction.values()) {
	        newRow = currRow + dir.dy;
	        newCol = currCol + dir.dx;

	        if (maze.inBounds(newRow, newCol) && !maze.mazeCell(newRow, newCol).visited()) {
	            unvisitedNeighbors.add(maze.mazeCell(newRow, newCol));
	        }
	    }

	    if (unvisitedNeighbors.size() == 0) {
	        return null;
	    }

	    return unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));
	}

	private void removeWalls(Cell current, Cell next) {
	    int x = current.col() - next.col();

	    if (x == 1) {
	        current.removeWall(2);
	        next.removeWall(3);
	        return;
	    } else if (x == -1) {
	        current.removeWall(3);
	        next.removeWall(2);
	        return;
	    }

	    int y = current.row() - next.row();

	    if (y == 1) {
	        current.removeWall(0);
	        next.removeWall(1);
	        return;
	    } else if (y == -1) {
	        current.removeWall(1);
	        next.removeWall(0);
	        return;
	    }
	}

}