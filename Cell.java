import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;

public class Cell {
	private static final Color WALL = Color.white;
	private static final int WALL_STROKE_SIZE = 2;
	private static final Stroke WALL_STROKE = new BasicStroke(WALL_STROKE_SIZE);
    private static final Color CURRENT = Color.green;
    private static final Color END = Color.magenta;
    private static final Color SOLUTION = Color.green;
    private static final Color VISITING = Color.red;
    private static final Color VISITED = Color.blue;

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
	private boolean current, visiting, visited, end, solution;
	private Cell parent;

	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		walls = new Wall[] { new Wall(0,0,1,0), new Wall(0,1,1,1), new Wall(0,0,0,1), new Wall(1,0,1,1) }; // TOP, BOTTOM, LEFT, RIGHT.
		current = visiting = visited = end = solution = false;
		parent = null;
	}

	public int col() {
		return col;
	}

	public int row() {
		return row;
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

	public void setStart() {
		this.current = true;
	}

	public void setEnd() {
		this.end = true;
	}

	public Cell parent() {
		return parent;
	}

	public void setParent(Cell parent) {
		this.parent = parent;
	}

	public void setSolution(boolean solution) {
		this.solution = solution;
	}

	public boolean pointInside(int x, int y, int scale, int margin) {
		int cellXStart = margin + col * scale;
		int cellYStart = margin + row * scale;
		int cellXEnd = cellXStart + scale;
		int cellYEnd = cellYStart + scale;

		return (x >= cellXStart && x <= cellXEnd) && (y >= cellYStart && y <= cellYEnd);
	}

	public void drawCell(Graphics2D g, int scale, int margin, String state) {
		int cellX = margin + col * scale;
		int cellY = margin + row * scale;
		int xStart, yStart, xEnd, yEnd;

		g.setStroke(WALL_STROKE);
		g.setColor(WALL);

		for (Cell.Wall wall : walls) {
			if (wall.isPresent()) {
				xStart = cellX + (wall.xStart() * scale);
				yStart = cellY + (wall.yStart() * scale);
				xEnd = cellX + (wall.xEnd() * scale);
				yEnd = cellY + (wall.yEnd() * scale);
				g.drawLine(xStart, yStart, xEnd, yEnd);
			}
		}

	    if (state == "solve") {
	        if (visiting) {
	            g.setColor(VISITING);
	            g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
	        } else if (visited) {
	            g.setColor(VISITED);
	            g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
	        }
	    }

	    if (current) {
	        g.setColor(CURRENT);
	        g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
	    }

	    if (end) {
	        g.setColor(END);
	        g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
	    }

	    if (solution) {
	    	g.setColor(SOLUTION);
	    	g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
	    }
	}

	public void printCell() {
		System.out.println("Row: " + row + ", Col: " + col);
	}
}