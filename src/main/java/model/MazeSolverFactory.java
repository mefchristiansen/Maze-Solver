package model;

import model.solvers.*;

public class MazeSolverFactory {

	public static MazeSolver initMazeSolver(SolverType solverType, Maze maze) {
		if (maze == null) {
			return null;
		}

		MazeSolver mazeSolver;

		switch(solverType) {
			case BFS:
				mazeSolver = new BFS(maze);
				break;

			case DFS:
				mazeSolver = new DFS(maze);
				break;

			case AStar:
				mazeSolver = new AStar(maze);
				break;

			// Default to A*
			default:
				mazeSolver = new AStar(maze);
				break;
		}

		return mazeSolver;
	}
}