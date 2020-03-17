package model;

public abstract class MazeGenerator extends java.util.Observable {
	protected Maze maze;

	public MazeGenerator(Maze maze) {
		this.maze = maze;
	}

	public abstract void generateMaze();
}