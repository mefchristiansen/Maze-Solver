package view.drawable;

import model.Cell;
import model.Maze;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MazePanel extends JPanel implements ChangeListener {
	private static final Color BACKGROUND = new Color(55, 50, 55);
    private model.Maze maze;
    private String displayState, initState;

    public void setDisplayState(String state) { this.displayState = state; }

    public MazePanel(Maze maze, String displayState) {
    	this.maze = maze;
    	this.displayState = displayState;
        this.initState = "start";

        initMazePanel();
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        generationAnimate();
    }

    @Override
    public void paintComponent(Graphics graphics) {
    	super.paintComponent(graphics);
    	Graphics2D g = (Graphics2D) graphics;

    	for (int r = 0; r < maze.numRows(); r++) {
    	    for (int c = 0; c < maze.numCols(); c++) {
    	    	Cell cell = maze.mazeCell(r,c);
                CellDrawable.drawCell(cell, g, MazeConstants.CELL_SIZE, MazeConstants.MARGIN, displayState);
    	    }
    	}
    }

    private void initMazePanel() {
        Dimension size = new Dimension(maze.numCols() * MazeConstants.CELL_SIZE + MazeConstants.MARGIN * 2, maze.numRows() * MazeConstants.CELL_SIZE + MazeConstants.MARGIN * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(BACKGROUND);
        repaint();
    }

    public void setPoint(int x, int y) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                if (cell.pointInside(x, y, MazeConstants.CELL_SIZE, MazeConstants.MARGIN)) {
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
            System.out.println("PAINT");
            Thread.sleep(MazeConstants.ANIMATION_SLEEP);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }

//    public void solveAnimate() {
//        try {
//            Thread.sleep(solveSleep);
//        } catch (InterruptedException ignored) {
//        }
//        repaint();
//    }
//
//    public void solutionAnimate() {
//        System.out.println("SOLUTION ANIMATION");
//
//
//        try {
//            Thread.sleep(solutionSleep);
//        } catch (InterruptedException ignored) {
//        }
//        repaint();
//    }
}