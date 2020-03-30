package view.drawable;

import model.Cell;
import model.MazeState;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

public class CellDrawable {
    private static final Color WALL = Color.white;
    private static final int WALL_STROKE_SIZE = 2;
    private static final Stroke WALL_STROKE = new BasicStroke(WALL_STROKE_SIZE);
    private static final int SOLUTION_PATH_STROKE_SIZE = 1;
    private static final Stroke SOLUTION_PATH_STROKE = new BasicStroke(SOLUTION_PATH_STROKE_SIZE);
    private static final Color CURRENT = Color.green;
    private static final Color START = Color.cyan;
    private static final Color END = Color.magenta;
    private static final Color SOLUTION = Color.green;
    private static final Color VISITING = Color.red;
    private static final Color VISITED = Color.blue;
    private static final int SOLUTION_ROUTE_POINT_SCALING_FACTOR = 4;
    private static final int CELL_SIZE = CellDrawableConstants.CELL_SIZE;
    private static final int MARGIN = CellDrawableConstants.MARGIN;

    public static void drawCell(Cell cell, Graphics2D graphics2D, MazeState mazeState) {
        int cellX = cell.getCellX(MARGIN, CELL_SIZE);
        int cellY = cell.getCellY(MARGIN, CELL_SIZE);

        drawCellWalls(graphics2D, cell, cellX, cellY);

        if (mazeState == MazeState.GENERATED) {
            if (cell.getStart()) {
                graphics2D.setColor(START);
                graphics2D.fill(fillCell(cellX, cellY));
            } else if (cell.getEnd()) {
                graphics2D.setColor(END);
                graphics2D.fill(fillCell(cellX, cellY));
            }
        }
        else if (mazeState == MazeState.SOLVING) {
            if (cell.visiting()) {
                graphics2D.setColor(VISITING);
                graphics2D.fill(fillCell(cellX, cellY));
            } else if (cell.visited()) {
                graphics2D.setColor(VISITED);
                graphics2D.fill(fillCell(cellX, cellY));
            }

            if (cell.getStart()) {
                graphics2D.setColor(START);
                graphics2D.fill(fillCell(cellX, cellY));
            } else if (cell.getEnd()) {
                graphics2D.setColor(END);
                graphics2D.fill(fillCell(cellX, cellY));
            }
        } else if (mazeState == MazeState.SOLVED) {
            if (cell.getSolution()) {
                graphics2D.setColor(SOLUTION);
                drawSolutionPathComponent(graphics2D, cell, cellX, cellY);
            }
        }

        if (cell.current()) {
            graphics2D.setColor(CURRENT);
            graphics2D.fill(fillCell(cellX, cellY));
        }
    }

    private static void drawCellWalls(Graphics2D graphics2D, Cell cell, int cellX, int cellY) {
        int xStart, yStart, xEnd, yEnd;

        graphics2D.setStroke(WALL_STROKE);
        graphics2D.setColor(WALL);

        for (Cell.Wall wall : cell.getWalls()) {
            if (wall.isPresent()) {
                xStart = cellX + (wall.xStart() * CELL_SIZE);
                yStart = cellY + (wall.yStart() * CELL_SIZE);
                xEnd = cellX + (wall.xEnd() * CELL_SIZE);
                yEnd = cellY + (wall.yEnd() * CELL_SIZE);
                graphics2D.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
            }
        }
    }

    private static void drawSolutionPathComponent(Graphics2D graphics2D, Cell cell, int cellX, int cellY) {
        double solutionRoutePointSize = (CELL_SIZE - (WALL_STROKE_SIZE / 2)) / SOLUTION_ROUTE_POINT_SCALING_FACTOR;

        double solutionRoutePointX = cellX + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2) - (CELL_SIZE / (2 * SOLUTION_ROUTE_POINT_SCALING_FACTOR));
        double solutionRoutePointY = cellY + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2) - (CELL_SIZE / (2 * SOLUTION_ROUTE_POINT_SCALING_FACTOR));

        Ellipse2D.Double solutionRoutePoint = new Ellipse2D.Double(solutionRoutePointX, solutionRoutePointY, solutionRoutePointSize, solutionRoutePointSize);
        graphics2D.fill(solutionRoutePoint);

        if (cell.parent() != null) {
            double solutionRoutePathPointStartX = cellX + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2);
            double solutionRoutePathPointStartY = cellY + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2);

            double solutionRoutePathPointEndX = cell.parent().getCellX(MARGIN, CELL_SIZE) + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2);
            double solutionRoutePathPointEndY = cell.parent().getCellY(MARGIN, CELL_SIZE) + (WALL_STROKE_SIZE / 2) + (CELL_SIZE / 2);

            graphics2D.setStroke(SOLUTION_PATH_STROKE);
            graphics2D.draw(new Line2D.Double(solutionRoutePathPointStartX, solutionRoutePathPointStartY, solutionRoutePathPointEndX, solutionRoutePathPointEndY));
        }
    }

    private static Rectangle2D.Double fillCell(int cellX, int cellY) {
        return new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, CELL_SIZE - WALL_STROKE_SIZE, CELL_SIZE - WALL_STROKE_SIZE);
    }
}
