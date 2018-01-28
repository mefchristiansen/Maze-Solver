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

	private static final int NUM_WALLS = 4;

	private int row;
	private int col;
	private Wall[] walls;
	private boolean visited;
	// private Cell parent;
	private boolean solution;


	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		this.walls = new Wall[] { new Wall(0,0,1,0), new Wall(0,1,1,1), new Wall(0,0,0,1), new Wall(1,0,1,1) }; // TOP, BOTTOM, LEFT, RIGHT.
		this.visited = false;
		this.solution = false;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
	}

	public boolean visited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
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

	public void setSolution(boolean solution) {
		this.solution = solution;
	}

	public boolean isSolution() {
		return solution;
	}

	public void printCell() {
		System.out.println("Node. X: " + col + ", Y: " + row);
	}
}