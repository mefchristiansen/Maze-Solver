package model;

import java.util.Collection;
import java.util.EnumMap;

public class Cell {

	public class Wall {
	    private int xStart, yStart, xEnd, yEnd;
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

	private int row, col, f, g;
//	private Wall[] walls;
    private EnumMap<Direction, Wall> walls;
	private boolean current, visiting, visited, start, end, solution;
	private Cell parent;

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
        walls = new EnumMap<Direction, Wall>(Direction.class) {{
            put(Direction.UP, new Wall(0,0,1,0));
            put(Direction.DOWN, new Wall(0,1,1,1));
            put(Direction.LEFT, new Wall(0,0,0,1));
            put(Direction.RIGHT, new Wall(1,0,1,1));
        }};

		current = visiting = visited = end = solution = false;
		parent = null;
		f = g = Integer.MAX_VALUE;
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

	public boolean wallPresent(Direction direction) {
		return walls.get(direction).present;
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

	public void setF(int f) {
		this.f = f;
	}

	public int getF() {
		return f;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getG() {
		return g;
	}

	public boolean pointInside(int x, int y, int scale, int margin) {
		int cellXStart = margin + col * scale;
		int cellYStart = margin + row * scale;
		int cellXEnd = cellXStart + scale;
		int cellYEnd = cellYStart + scale;

		return (x >= cellXStart && x <= cellXEnd) && (y >= cellYStart && y <= cellYEnd);
	}

	public Direction directionToCell(Cell toCell) {
        int x_diff = toCell.col() - this.col();

        if (x_diff > 0) {
            return Direction.RIGHT;
        } else if (x_diff < 0) {
            return Direction.LEFT;
        }

        int y_diff = toCell.row() - this.row();

        if (y_diff > 0) {
            return Direction.DOWN;
        } else if (y_diff < 0) {
            return Direction.UP;
        }

        return null;
    }
}