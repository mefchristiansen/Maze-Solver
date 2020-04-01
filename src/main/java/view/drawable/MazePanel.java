package view.drawable;

import controller.MazeController;
import controller.listeners.MazeWaypointClickListener;
import model.Cell;
import model.Maze;
import model.MazeState;

import javax.swing.*;
import java.awt.*;

public class MazePanel extends JPanel {
    private enum WaypointState {
        START, END
    }

	private static final Color BACKGROUND = new Color(55, 50, 55);
    private Maze maze;
    private final MazeController mazeController;
    private final MazeDrawable mazeDrawable;
    private WaypointState waypointState;

    public MazePanel(Maze maze, MazeController mazeController) {
    	this.maze = maze;
    	this.mazeController = mazeController;
        this.mazeDrawable = new MazeDrawable();
        this.waypointState = WaypointState.START;

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

    public void repaintMaze(Maze maze) {
        this.maze = maze;
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        MazeState mazeState = mazeController.getState();
        mazeDrawable.drawMaze(maze, graphics, mazeState);
    }

    public void resize() {
        int mazeWidth = maze.numCols() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;
        int mazeHeight = maze.numRows() * CellDrawableConstants.CELL_SIZE + CellDrawableConstants.MARGIN * 2;

        Dimension mazeDimension = new Dimension(mazeWidth, mazeHeight);
        setMinimumSize(mazeDimension);
        setPreferredSize(mazeDimension);
        repaint();
    }

    public void setWaypoint(int mouseClickX, int mouseClickY) {
        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                if (cell.pointInside(mouseClickX, mouseClickY, CellDrawableConstants.CELL_SIZE, CellDrawableConstants.MARGIN)) {
                    if (waypointState == WaypointState.START) {
                        maze.setStartingCell(cell);
                        waypointState = WaypointState.END;
                        repaint();
                    } else if (waypointState == WaypointState.END && !cell.getStart()) {
                        maze.setEndingCell(cell);
                        waypointState = WaypointState.START;
                        repaint();
                    }
                }
            }
        }
    }

    public void resetWaypointSetterState() {
        waypointState = WaypointState.START;
    }
}