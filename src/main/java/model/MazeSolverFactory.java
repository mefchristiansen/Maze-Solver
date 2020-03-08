package model;

public class MazeSolverFactory {
	private Maze maze;

	public static MazeSolver getMazeSolver(String solverType, Maze maze) {
		if (maze == null) {
			return null;
		}

		// Default to a star
		if (solverType == null) {
			return new AStar(maze);
		}

		if (solverType.equalsIgnoreCase("BFS")) {
			return new BFS(maze);
		}

		else if (solverType.equalsIgnoreCase("DFS")) {
			return new DFS(maze);
		}

		else if (solverType.equalsIgnoreCase("AStar")) {
			return new AStar(maze);
		}

		// Default to a star
		else {
			return new AStar(maze);
		}
	}
}