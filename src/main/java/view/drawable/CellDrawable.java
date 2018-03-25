package view.drawable;

import model.Cell;

import java.awt.*;

public class CellDrawable {

    private static final Color WALL = Color.white;
    private static final int WALL_STROKE_SIZE = 2;
    private static final Stroke WALL_STROKE = new BasicStroke(WALL_STROKE_SIZE);
    private static final Color CURRENT = Color.green;
    private static final Color END = Color.magenta;
    private static final Color SOLUTION = Color.green;
    private static final Color VISITING = Color.red;
    private static final Color VISITED = Color.blue;

    public static void drawCell(Cell cell, Graphics2D g, int scale, int margin, String state) {
        int cellX = margin + cell.col() * scale;
        int cellY = margin + cell.row() * scale;
        int xStart, yStart, xEnd, yEnd;

        g.setStroke(WALL_STROKE);
        g.setColor(WALL);

        for (Cell.Wall wall : cell.getWalls()) {
            if (wall.isPresent()) {
                xStart = cellX + (wall.xStart() * scale);
                yStart = cellY + (wall.yStart() * scale);
                xEnd = cellX + (wall.xEnd() * scale);
                yEnd = cellY + (wall.yEnd() * scale);
                g.drawLine(xStart, yStart, xEnd, yEnd);
            }
        }

        if (state.equals("solve")) {
            if (cell.visiting()) {
                g.setColor(VISITING);
                g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
            } else if (cell.visited()) {
                g.setColor(VISITED);
                g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
            }
        }

        if (cell.current()) {
            g.setColor(CURRENT);
            g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        }

        if (cell.getEnd()) {
            g.setColor(END);
            g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        }

        if (cell.getSolution()) {
            g.setColor(SOLUTION);
            g.fill(new Rectangle(cellX + WALL_STROKE_SIZE / 2, cellY + WALL_STROKE_SIZE / 2, scale - WALL_STROKE_SIZE, scale - WALL_STROKE_SIZE));
        }
    }
}
