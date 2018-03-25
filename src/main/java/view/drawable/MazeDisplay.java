package view.drawable;

import model.Cell;
import model.Maze;

import javax.swing.*;
import java.awt.*;

public class MazeDisplay extends JPanel {

	private static final Color BACKGROUND = new Color(55, 50, 55);
    private Maze maze;
    private final int scale, margin;
    private final long generationSleep, solveSleep, solutionSleep;
    private String displayState, initState;

    public void setDisplayState(String state) { this.displayState = state; }

    public MazeDisplay(Maze maze, int scale, int margin, String displayState, long generationSleep, long solveSleep, long solutionSleep) {
    	super();
    	this.maze = maze;
    	this.scale = scale;
    	this.margin = margin;
    	this.displayState = displayState;
        this.initState = "start";
        this.generationSleep = generationSleep;
        this.solveSleep = solveSleep;
        this.solutionSleep = solutionSleep;

    	initMazeDisplay();
    }

    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	Graphics2D g = (Graphics2D) graphics;

    	for (int r = 0; r < maze.numRows(); r++) {
    	    for (int c = 0; c < maze.numCols(); c++) {
    	    	Cell cell = maze.mazeCell(r,c);
                CellDrawable.drawCell(cell, g, scale, margin, displayState);
    	    }
    	}
    }

    private void initMazeDisplay() {
    	Dimension size = new Dimension(maze.numCols() * scale + margin * 2, maze.numRows() * scale + margin * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(BACKGROUND);
        repaint();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void setPoint(int x, int y) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                if (cell.pointInside(x, y, scale, margin)) {
                    if (initState.equals("start")) {
                        cell.setStart();
                        maze.startingCell = cell;
                        initState = "end";
                        repaint();
                    } else if (initState.equals("end")) {
                        cell.setEnd();
                        maze.endingCell = cell;
                        initState = "solve";
                        repaint();
                    }
                }
            }
        }
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
}