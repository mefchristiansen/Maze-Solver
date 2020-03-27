package view.drawable;

import model.Cell;
import model.Maze;
import model.MazeState;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MazePanel extends JPanel implements ChangeListener {
    private enum WaypointState {
        START, END, COMPLETE;
    }

	private static final Color BACKGROUND = new Color(55, 50, 55);
    private model.Maze maze;
    private MazeDrawable mazeDrawable;
    private MazeState mazeState;
    private WaypointState waypointState;
    private int animationSpeed;

    public MazePanel(Maze maze) {
    	this.maze = maze;
        this.mazeDrawable = new MazeDrawable(this.maze);
        this.mazeState = MazeState.INIT;
        this.waypointState = WaypointState.START;
        this.animationSpeed = MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP;

        initMazePanel();
    }

    public void setMazeState(MazeState mazeState) {
        this.mazeState = mazeState;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        animateMaze();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        mazeDrawable.drawMaze(graphics, mazeState);
    }

    private void initMazePanel() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        Dimension mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        setBackground(BACKGROUND);
        repaint();
    }

    public void resize() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        Dimension mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        repaint();
    }

    public void setPoint(int x, int y) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                if (cell.pointInside(x, y, CellDrawableConstants.CELL_SIZE, CellDrawableConstants.MARGIN)) {
                    if (waypointState == WaypointState.START) {
                        cell.setStart();
                        maze.startingCell = cell;
                        waypointState = WaypointState.END;
                        repaint();
                    } else if (waypointState == WaypointState.END) {
                        cell.setEnd();
                        maze.endingCell = cell;
                        waypointState = WaypointState.COMPLETE;
                        repaint();
                    }
                }
            }
        }
    }

    public void resetWaypointSetterState() {
        waypointState = WaypointState.START;
    }

    private void animateMaze() {
        try {
            Thread.sleep(animationSpeed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}