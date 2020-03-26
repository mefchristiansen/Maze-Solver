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

    public MazePanel(Maze maze) {
    	this.maze = maze;
        this.mazeDrawable = new MazeDrawable(this.maze);
        this.mazeState = MazeState.INIT;
        this.waypointState = WaypointState.START;

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
        Dimension size = new Dimension(maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2, maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2);
        setMinimumSize(size);
        setPreferredSize(size);
        setBackground(BACKGROUND);
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

    public void animateMaze() {
        long sleepTime;

        switch (mazeState) {
            case GENERATING:
                sleepTime = MazeDrawableConstants.GENERATION_SLEEP_TIME;
                break;
            case SOLVING:
                sleepTime = MazeDrawableConstants.SOLVE_SLEEP_TIME;
                break;
            case SOLVED:
                sleepTime = MazeDrawableConstants.SOLUTION_SLEEP_TIME;
                break;
            default:
                sleepTime = MazeDrawableConstants.ANIMATION_SLEEP;
                break;
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException ignored) {
        }
        repaint();
    }
}