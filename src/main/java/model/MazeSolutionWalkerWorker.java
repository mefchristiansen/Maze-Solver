package model;

import controller.MazeController;

import javax.swing.*;
import java.util.List;

public class MazeSolutionWalkerWorker extends SwingWorker<Void, Maze> {
    private final Maze maze;
    private final MazeController mazeController;

    public MazeSolutionWalkerWorker(Maze maze, MazeController mazeController) {
        this.maze = maze;
        this.mazeController = mazeController;
    }

    @Override
    protected Void doInBackground() throws Exception {
        Cell goal = maze.getGoal();

        while (goal != null) {
            goal.setSolution(true);
            goal = goal.parent();

            publish(maze);
            Thread.sleep(mazeController.getAnimationSpeed());
        }

        return null;
    }

    @Override
    protected void process(List<Maze> chunks) {
        for (Maze maze : chunks) {
            mazeController.repaintMaze(maze);
        }
    }
}
