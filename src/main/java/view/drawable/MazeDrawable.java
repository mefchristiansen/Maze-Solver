package view.drawable;

import model.Maze;
import model.Cell;

import java.awt.*;

public class MazeDrawable {
    private final Maze maze;
    private final int scale, margin;
    private String displayState = "generate";

    public MazeDrawable(Maze maze) {
        this.maze = maze;
        this.scale = MazeConstants.CELL_SIZE;
        this.margin = MazeConstants.MARGIN;
    }

    public void setDisplayState(String state) {
        this.displayState = state;
    }

    public void drawMaze(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                CellDrawable.drawCell(cell, g, scale, margin, displayState);
            }
        }
    }
}