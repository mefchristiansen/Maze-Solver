import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class MazeDisplay extends JPanel {
	private static final Color BACKGROUND = new Color(55, 50, 55);

    private Maze maze;
    private final int scale, margin;
    private final long generationSleep, solveSleep, solutionSleep;
    private String displayState, initState;

    public MazeDisplay(Maze maze, int scale, int margin, long generationSleep, long solveSleep, long solutionSleep) {
    	super();
    	this.maze = maze;
    	this.scale = scale;
    	this.margin = margin;
    	this.generationSleep = generationSleep;
        this.solveSleep = solveSleep;
        this.solutionSleep = solutionSleep;
        this.displayState = "generate";
        this.initState = "start";
    	initMazeDisplay();
    }

    public void setDisplayState(String displayState) {
        this.displayState = displayState;
    }

    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	Graphics2D g = (Graphics2D) graphics;

    	for (int r = 0; r < maze.numRows(); r++) {
    	    for (int c = 0; c < maze.numCols(); c++) {
    	    	Cell cell = maze.mazeCell(r,c);
                cell.drawCell(g, scale, margin, displayState);
    	    }
    	}
    }

    private void initMazeDisplay() {
    	Dimension size = new Dimension(maze.numCols() * scale + margin * 2, maze.numRows() * scale + margin * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(BACKGROUND);
        repaint();
    }

    public void generationAnimate() {
    	try {
            Thread.sleep(generationSleep);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

    public void solveAnimate() {
        try {
            Thread.sleep(solveSleep);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

    public void solutionAnimate() {
        try {
            Thread.sleep(solutionSleep);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

    public boolean disableMouseListener() {
        return initState == "solve";
    }

    public void setPoint(int x, int y) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                if (cell.pointInside(x, y, scale, margin)) {
                    if (initState == "start" ) {
                        cell.setStart();
                        initState = "end";
                        repaint();
                    } else if (initState == "end") {
                        cell.setEnd();
                        initState = "solve";
                        repaint();
                    }
                }
            }
        }
    }
}