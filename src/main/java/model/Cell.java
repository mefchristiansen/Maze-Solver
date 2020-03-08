package model;

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
	private Wall[] walls;
	private boolean current, visiting, visited, start, end, solution;
	private Cell parent;

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		walls = new Wall[] { new Wall(0,0,1,0), new Wall(0,1,1,1), new Wall(0,0,0,1), new Wall(1,0,1,1) }; // TOP, BOTTOM, LEFT, RIGHT.
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

	public Wall[] getWalls() {
		return walls;
	}

	public boolean wallPresent(int dx, int dy) {
		if (dx == 1) {
			return walls[3].present;
		} else if (dx == -1) {
			return walls[2].present;
		} else if (dy == 1) {
			return walls[1].present;
		} else {
			return walls[0].present;
		}
	}

	public void removeWall(int direction) {
		walls[direction].present = false;
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

	public void setStart() {
		this.start = true;
	}

	public boolean getStart() { return start; }

	public void setEnd() {
		this.end = true;
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
}