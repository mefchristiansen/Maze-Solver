import java.awt.Color;
import java.awt.Stroke;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
// import java.awt.Shape;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class MazeDisplay extends JPanel {
	private static final Color WALL = Color.white;
	private static final Stroke WALL_STROKE = new BasicStroke(2);
	private static final Color SOLUTION = Color.red;
	private static final Stroke SOLUTION_STROKE = new BasicStroke(3);

    private Maze maze;
    private final int scale;
    private final int margin;

    public MazeDisplay(Maze maze, int scale, int margin) {
    	super();
    	this.maze = maze;
    	this.scale = scale;
    	this.margin = margin;
    	initMazeDisplay();
    }

    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	Graphics2D g = (Graphics2D) graphics;
    	g.setStroke(WALL_STROKE);
    	g.setColor(WALL);

    	int centerX, centerY;

    	for (int r = 0; r < maze.numRows(); r++) {
    	    for (int c = 0; c < maze.numCols(); c++) {
    	    	Cell cell = maze.mazeCell(r,c);
    	    	drawCell(g, cell);

    	    	if (cell.isSolution()) {
    	    		centerX = margin + cell.col() * scale + (scale / 2);
    	    		centerY = margin + cell.row() * scale + (scale / 2);
    	    		g.setColor(SOLUTION);
    	    		g.fill(new Rectangle(centerX, centerY, scale / 4, scale / 4));
    	    	}

    	    	g.setColor(WALL);

    	    }
    	}
    }

    private void drawCell(Graphics2D g, Cell cell) {
    	int cellX = margin + cell.col() * scale;
    	int cellY = margin + cell.row() * scale;
    	int xStart, yStart, xEnd, yEnd;

    	for (Cell.Wall wall : cell.getWalls()) {
    		if (wall.isPresent()) {
    			xStart = cellX + (wall.xStart() * scale);
    			yStart = cellY + (wall.yStart() * scale);
    			xEnd = cellX + (wall.xEnd() * scale);
    			yEnd = cellY + (wall.yEnd() * scale);
    			g.drawLine(xStart, yStart, xEnd, yEnd);
    		}
    	}

    }

    private void initMazeDisplay() {
    	Dimension size = new Dimension(maze.numCols() * scale + margin * 2, maze.numRows() * scale + margin * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(Color.black);
        repaint();
    }

    public void animate() {
    	repaint();
    }

}