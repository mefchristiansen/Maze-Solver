package model;

import controller.MazeController;

import javax.swing.*;

public abstract class MazeGeneratorWorker extends SwingWorker<Boolean, Maze> {
	protected final Maze maze;
    protected final MazeController mazeController;

	protected MazeGeneratorWorker(Maze maze, MazeController mazeController) {
		this.maze = maze;
		this.mazeController = mazeController;
	}
}