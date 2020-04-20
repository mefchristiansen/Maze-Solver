package model;

import model.Cell.CellVisitState;

/**
 * This class represents the maze and its properties. A maze is a 2D array of cells.
 */
public class Maze {
	private int numRows, numCols, checks, solutionLength;
	private Cell[][] maze;
    private Cell startingCell, endingCell, goal;

    public Maze() {
        this(MazeConstants.DEFAULT_NUM_ROWS, MazeConstants.DEFAULT_NUM_COLS);
    }

	public Maze(int numRows, int numCols) {
        initMaze(numRows, numCols);
	}

	public void initMaze(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;

		checks = solutionLength = 0;

		maze = new Cell[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = new Cell(r, c);
            }
        }
    }

    public Cell getStartingCell() { return startingCell; }

    public Cell getEndingCell() { return endingCell; }

    public int numRows() {
        return numRows;
    }

    public int numCols() { return numCols; }

	public void setGoal(Cell goal) {
		this.goal = goal;
	}

	public Cell getGoal() {
		return goal;
	}

	public int getChecks() {
    	return checks;
	}

	public int getSolutionLength() {
		return solutionLength;
	}

	public void incrementChecks() {
    	checks++;
	}

	public void incrementSolutionLength() {
		solutionLength++;
	}

    public Cell mazeCell(int r, int c) {
        if (inBounds(r, c)) {
            return maze[r][c];
        }

        return null;
    }

	/**
	 * Determines if a cell at the current row and column is in the bounds of the maze.
	 *
	 * @param row A row
	 * @param col A column
	 * @return A boolean indicating if a cell at that row and column is in the bounds of the maze
	 */
    public boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < numRows && col < numCols;
    }

	/**
	 * Set all properties of the cell related to it being visited to false. This function is called after the generator
	 * has generated the maze so that the solver can visit any cell in the maze.
	 */
    public void voidVisits() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell current = maze[r][c];
                current.setCurrent(false);
                current.setVisitState(CellVisitState.UNVISITED);
            }
        }
    }

	/**
	 * Resets the maze, clearing the start, end and goal cells, as well as reinitializing the maze cells.
	 */
    public void resetMaze() {
        startingCell = endingCell = goal = null;
		checks = solutionLength = 0;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = new Cell(r, c);
            }
        }
    }

	/**
	 * Sets the starting cell of the maze
	 *
	 * @param cell The starting cell
	 */
    public void setStartingCell(Cell cell) {
        startingCell.setStart(false);
        startingCell = cell;
        startingCell.setStart(true);
    }

	/**
	 * Sets the ending cell of the maze
	 *
	 * @param cell The ending cell
	 */
    public void setEndingCell(Cell cell) {
        endingCell.setEnd(false);
        endingCell = cell;
        endingCell.setEnd(true);
    }

	/**
	 * Defaults the start and end points for the maze. This function is called at the end of generation so that the user
	 * isn't required to pick start and end points. The start point is defaulted to the top left cell, and the end point
	 * is defaulted to the bottom right cell.
	 */
    public void defaultWaypoints() {
        startingCell = maze[0][0];
        endingCell = maze[maze.length - 1][maze[maze.length - 1].length - 1];
    }
}
