package model;

public class MazeSolverFactory {
	private Maze maze;

	public static MazeSolver getMazeSolver(String solverType, Maze maze) {
		if (solverType == null || maze == null) {
			return null;
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

		return null;
	}
}