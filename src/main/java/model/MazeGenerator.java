package model;

public abstract class MazeGenerator extends java.util.Observable {
	protected Maze maze;

	public abstract void generateMaze();
}