package model;

import java.util.*;

public abstract class MazeSolver extends java.util.Observable {
	protected Maze maze;
	protected Cell goal;

	public MazeSolver(Maze maze) {
		this.maze = maze;
	}

	public abstract void solve(int rowStart, int colStart, int rowEnd, int colEnd);

	public void walkSolutionPath() {
		while (goal != null) {
			goal.setSolution(true);
			goal = goal.parent();
			setChanged();
			notifyObservers();
		}
	}
}