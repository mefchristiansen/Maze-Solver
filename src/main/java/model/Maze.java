package model;

public class Maze {
	private final int numRows;
	private final int numCols;
	private Cell[][] maze;
    public Cell startingCell, endingCell;

	public Maze(int numRows, int numCols) {
		this.numRows = numRows;
		this.numCols = numCols;

        maze = new Cell[numRows][numCols];
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = new Cell(r, c);
            }
        }
	}

    public int numRows() {
        return numRows;
    }

    public int numCols() {
        return numCols;
    }

    public Cell mazeCell(int r, int c) {
        if (inBounds(r, c)) {
            return maze[r][c];
        }

        return null;
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < numRows && col < numCols;
    }

    public void voidVisits() {
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                Cell current = maze[r][c];
                current.setVisited(false);
                current.setCurrent(false);
                current.setVisiting(false);
            }
        }
    }

    public void resetMaze() {
        startingCell = endingCell = null;

        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                maze[r][c] = new Cell(r, c);
            }
        }
    }
}
