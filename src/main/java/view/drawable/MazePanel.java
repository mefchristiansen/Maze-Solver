package view.drawable;

import controller.MazeController;
import controller.listeners.MazeWaypointClickListener;
import model.Cell;
import model.Maze;
import model.MazeConstants;
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
    private Maze maze;
    private MazeController mazeController;
    private MazeDrawable mazeDrawable;
    private WaypointState waypointState;
    private int animationSpeed;

    public MazePanel(Maze maze, MazeController mazeController) {
    	this.maze = maze;
    	this.mazeController = mazeController;
        this.mazeDrawable = new MazeDrawable(this.maze);
        this.waypointState = WaypointState.START;
        this.animationSpeed = MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP;

        initMazePanel();
    }

    private void initMazePanel() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        Dimension mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        setBackground(BACKGROUND);

        addMouseListener(new MazeWaypointClickListener(this, mazeController));

        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        animateMaze();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        MazeState mazeState = mazeController.getState();
        mazeDrawable.drawMaze(graphics, mazeState);
    }

    public void resize() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        Dimension mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        repaint();
    }

    public void setWaypoint(int x, int y) {
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
        MazeState mazeState = mazeController.getState();
        double animationSpeedMultipler;

        switch (mazeState) {
            case GENERATING:
                animationSpeedMultipler = MazeDrawableConstants.GENERATION_SLEEP_TIME_MULTIPLER;
                break;
            case SOLVING:
                animationSpeedMultipler = MazeDrawableConstants.SOLVE_SLEEP_TIME_MULTIPLER;
                break;
            case SOLVED:
                animationSpeedMultipler = MazeDrawableConstants.SOLUTION_SLEEP_TIME_MULTIPLIER;
                break;
            default:
                animationSpeedMultipler = MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP;
                break;
        }

        try {
            Thread.sleep((long)(animationSpeed * animationSpeedMultipler));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }
}