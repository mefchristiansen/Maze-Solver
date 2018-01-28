import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MazeDisplay extends JPanel {
	private static final Color BACKGROUND = new Color(55, 50, 55);
	private static final Color WALL = Color.white;
	private static final int WALL_STROKE_SIZE = 2;
	private static final Stroke WALL_STROKE = new BasicStroke(WALL_STROKE_SIZE);
	private static final Color SOLUTION = Color.red;
	private static final Color VISITED = Color.blue;
	private static final Stroke SOLUTION_STROKE = new BasicStroke(3);

    private Maze maze;
    private final int scale;
    private final int margin;
    private final long sleep;


    public MazeDisplay(Maze maze, int scale, int margin, long sleep) {
    	super();
    	this.maze = maze;
    	this.scale = scale;
    	this.margin = margin;
    	this.sleep = sleep;
    	initMazeDisplay();
    }

    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	Graphics2D g = (Graphics2D) graphics;
    	g.setStroke(WALL_STROKE);
    	g.setColor(WALL);

    	for (int r = 0; r < maze.numRows(); r++) {
    	    for (int c = 0; c < maze.numCols(); c++) {
    	    	drawCell(g, maze.mazeCell(r,c));
    	    }
    	}
    }

    private void drawCell(Graphics2D g, Cell cell) {
    	int cellX = margin + cell.col() * scale;
    	int cellY = margin + cell.row() * scale;
    	int xStart, yStart, xEnd, yEnd;

    	g.setColor(WALL);

    	for (Cell.Wall wall : cell.getWalls()) {
    		if (wall.isPresent()) {
    			xStart = cellX + (wall.xStart() * scale);
    			yStart = cellY + (wall.yStart() * scale);
    			xEnd = cellX + (wall.xEnd() * scale);
    			yEnd = cellY + (wall.yEnd() * scale);
    			g.drawLine(xStart, yStart, xEnd, yEnd);
    		}
    	}

    	if (cell.isSolution()) {
    		g.setColor(SOLUTION);
    		g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
    	} else if (cell.visited()) {
    		g.setColor(VISITED);
    		g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
    	}

    }

    private void initMazeDisplay() {
    	Dimension size = new Dimension(maze.numCols() * scale + margin * 2, maze.numRows() * scale + margin * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(BACKGROUND);
        repaint();
    }

    public void animate() {
    	try {
            Thread.sleep(sleep);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }
}