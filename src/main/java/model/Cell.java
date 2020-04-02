package model;

import java.util.*;

/**
 * This class represents the cells in a maze. A maze consists of a 2D array of cells.
 */
public class Cell {
	/**
	 * This class represents the walls for a cell. Each cell has 4 walls, one in each direction (up, down, left, right).
	 * A wall instance stores the coordinates of the wall in the maze, and a boolean indicating whether that wall is
	 * present or not.
	 */
	public static class Wall {
	    private final int xStart, yStart, xEnd, yEnd;
	    private boolean present;

	    private Wall(int xStart, int yStart, int xEnd, int yEnd) {
	    	this.xStart = xStart;
	    	this.yStart = yStart;
	    	this.xEnd = xEnd;
	    	this.yEnd = yEnd;
	    	this.present = true;
	    }

	    public int xStart() {
	    	return xStart;
	    }

	    public int yStart() {
	    	return yStart;
	    }

	    public int xEnd() {
	    	return xEnd;
	    }

	    public int yEnd() {
	    	return yEnd;
	    }

	    public boolean isPresent() {
	    	return present;
	    }
	}

	private final int row, col;
	/**
	 * The f and g scores used by the A* algorithm
	 */
    private int fCost, gCost, hCost;

	/**
	 * An EnumMap storing the walls for each direction for a cell
	 */
	private final EnumMap<Direction, Wall> walls;
	private boolean current, visiting, visited, start, end, solution;
	/**
	 * The parent of the cell for walking the solution path
	 */
	private Cell parent;

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
        walls = new EnumMap<>(Direction.class) {{
			put(Direction.UP, new Wall(0, 0, 1, 0));
			put(Direction.DOWN, new Wall(0, 1, 1, 1));
			put(Direction.LEFT, new Wall(0, 0, 0, 1));
			put(Direction.RIGHT, new Wall(1, 0, 1, 1));
		}};

		current = visiting = visited = end = solution = false;
		parent = null;
		fCost = gCost = hCost = Integer.MAX_VALUE;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public int getCellX(int margin, int scale) {
		return margin + col * scale;
	}

	public int getCellY(int margin, int scale) {
		return margin + row * scale;
	}

	public Collection<Wall> getWalls() {
		return walls.values();
	}

	public boolean wallMissing(Direction direction) {
		return !walls.get(direction).present;
	}

	public void removeWall(Direction direction) {
        walls.get(direction).present = false;
	}

	public boolean visited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean current() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	public void setVisiting(boolean visiting) {
		this.visiting = visiting;
	}

	public boolean visiting() {
		return visiting;
	}

	public void setStart(boolean state) {
		this.start = state;
	}

	public boolean getStart() { return start; }

	public void setEnd(boolean state) {
		this.end = state;
	}

	public boolean getEnd() { return end; }

	public Cell parent() {
		return parent;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public void setSolution(boolean solution) {
		this.solution = solution;
	}

	public boolean getSolution() { return solution; }

	public void setFCost(int fCost) {
		this.fCost = fCost;
	}

	public int getFCost() {
		return fCost;
	}

	public void setGCost(int gCost) {
		this.gCost = gCost;
	}

	public int getGCost() {
		return gCost;
	}

	public int getHCost() { return hCost; }

	public void setHCost(int hCost) { this.hCost = hCost; }

	/**
	 * Returns a boolean indicating whether a mouse click is inside the cell.
	 *
	 * @param mouseClickX The x coordinate of a mouse click
	 * @param mouseClickY The y coordinate of a mouse click
	 * @param scale The maze scale constant
	 * @param margin The maze margin constant
	 * @return Boolean indicating whether a mouse click is inside the cell.
	 */
	public boolean pointInside(int mouseClickX, int mouseClickY, int scale, int margin) {
		int cellXStart = margin + col * scale;
		int cellYStart = margin + row * scale;
		int cellXEnd = cellXStart + scale;
		int cellYEnd = cellYStart + scale;

		return (mouseClickX >= cellXStart && mouseClickX <= cellXEnd) &&
				(mouseClickY >= cellYStart && mouseClickY <= cellYEnd);
	}

	/**
	 * Calculates the direction from a starting cell to a neighbouring cell.
	 *
	 * @param neighbour The neighbouring cell
	 * @return The direction from the cell to the neighbouring cell
	 */
	public Direction directionToCell(Cell neighbour) {
        int x_diff = neighbour.col() - this.col();

        if (x_diff > 0) {
            return Direction.RIGHT;
        } else if (x_diff < 0) {
            return Direction.LEFT;
        }

        int y_diff = neighbour.row() - this.row();

        if (y_diff > 0) {
            return Direction.DOWN;
        } else if (y_diff < 0) {
            return Direction.UP;
        }

        return null;
    }
}
