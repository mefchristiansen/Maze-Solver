package model;

import controller.MazeController;
import model.solvers.AStar;
import model.solvers.BFS;
import model.solvers.DFS;

/**
 * The MazeSolverWorker factory.
 */
public class MazeSolverWorkerFactory {

	public static MazeSolverWorker initMazeSolver(SolverType solverType, Maze maze, MazeController mazeController) {
		if (maze == null) {
			return null;
		}

		MazeSolverWorker mazeSolverWorker;

		switch (solverType) {
			case BFS:
				mazeSolverWorker = new BFS(maze, mazeController);
				break;

			case DFS:
				mazeSolverWorker = new DFS(maze, mazeController);
				break;

			case AStar:
				mazeSolverWorker = new AStar(maze, mazeController);
				break;

			// Default to A*
			default:
				mazeSolverWorker = new AStar(maze, mazeController);
				break;
		}

		return mazeSolverWorker;
	}
}
