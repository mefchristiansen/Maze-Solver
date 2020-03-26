package model;

import controller.MazeController;
import model.solvers.*;

public class MazeSolverFactory {

	public static MazeSolver initMazeSolver(SolverType solverType, Maze maze, MazeController mazeController) {
		if (maze == null) {
			return null;
		}

		MazeSolver mazeSolver;

		switch(solverType) {
			case BFS:
				mazeSolver = new BFS(maze, mazeController);
				break;

			case DFS:
				mazeSolver = new DFS(maze, mazeController);
				break;

			case AStar:
				mazeSolver = new AStar(maze, mazeController);
				break;

			// Default to A*
			default:
				mazeSolver = new AStar(maze, mazeController);
				break;
		}

		return mazeSolver;
	}
}