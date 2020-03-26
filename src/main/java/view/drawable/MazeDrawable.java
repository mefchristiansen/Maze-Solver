package view.drawable;

import model.Maze;
import model.Cell;
import model.MazeState;

import java.awt.*;

public class MazeDrawable {
    private final Maze maze;

    public MazeDrawable(Maze maze) {
        this.maze = maze;
    }

    public void drawMaze(Graphics graphics, MazeState mazeState) {
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (int r = 0; r < maze.numRows(); r++) {
            for (int c = 0; c < maze.numCols(); c++) {
                Cell cell = maze.mazeCell(r,c);
                CellDrawable.drawCell(cell, graphics2D, mazeState);
            }
        }
    }
}