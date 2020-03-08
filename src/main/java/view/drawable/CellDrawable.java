package view.drawable;

import model.Cell;

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

    public static void drawCell(Cell cell, Graphics2D g, int scale, int margin, String state) {
        int cellX = cell.getCellX(margin, scale);
        int cellY = cell.getCellY(margin, scale);
        int xStart, yStart, xEnd, yEnd;

        g.setStroke(WALL_STROKE);
        g.setColor(WALL);

        for (Cell.Wall wall : cell.getWalls()) {
            if (wall.isPresent()) {
                xStart = cellX + (wall.xStart() * scale);
                yStart = cellY + (wall.yStart() * scale);
                xEnd = cellX + (wall.xEnd() * scale);
                yEnd = cellY + (wall.yEnd() * scale);
                g.draw(new Line2D.Double(xStart, yStart, xEnd, yEnd));
            }
        }

        if (cell.current()) {
            g.setColor(CURRENT);
            g.fill(new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        }

        if (state.equals("solve")) {
            if (cell.visiting()) {
                g.setColor(VISITING);
                g.fill(new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
            } else if (cell.visited()) {
                g.setColor(VISITED);
                g.fill(new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
            }
        } else if (state.equals("solution")) {
            if (cell.getSolution()) {
                g.setColor(SOLUTION);

                double solutionRoutePointSize = (scale - (WALL_STROKE_SIZE / 2)) / SOLUTION_ROUTE_POINT_SCALING_FACTOR;

                // Extract this logic to function
                double solutionRoutePointX = cellX + (WALL_STROKE_SIZE / 2) + (scale / 2) - (scale / (2 * SOLUTION_ROUTE_POINT_SCALING_FACTOR));
                double solutionRoutePointY = cellY + (WALL_STROKE_SIZE / 2) + (scale / 2) - (scale / (2 * SOLUTION_ROUTE_POINT_SCALING_FACTOR));

                Ellipse2D.Double solutionRoutePoint = new Ellipse2D.Double(solutionRoutePointX, solutionRoutePointY, solutionRoutePointSize, solutionRoutePointSize);
                g.fill(solutionRoutePoint);

                if (cell.parent() != null) {
                    double solutionRoutePathPointStartX = cellX + (WALL_STROKE_SIZE / 2) + (scale / 2);
                    double solutionRoutePathPointStartY = cellY + (WALL_STROKE_SIZE / 2) + (scale / 2);

                    double solutionRoutePathPointEndX = cell.parent().getCellX(margin, scale) + (WALL_STROKE_SIZE / 2) + (scale / 2);
                    double solutionRoutePathPointEndY = cell.parent().getCellY(margin, scale) + (WALL_STROKE_SIZE / 2) + (scale / 2);

                    g.setStroke(SOLUTION_PATH_STROKE);
                    g.draw(new Line2D.Double(solutionRoutePathPointStartX, solutionRoutePathPointStartY, solutionRoutePathPointEndX, solutionRoutePathPointEndY));
                }
            }
        }

        if (cell.getStart()) {
            g.setColor(START);
            g.fill(new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        } else if (cell.getEnd()) {
            g.setColor(END);
            g.fill(new Rectangle2D.Double(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        }
    }
}
