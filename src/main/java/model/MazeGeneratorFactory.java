package model;

// public enum MazeGenerator {
// 	RECURSIVE_BACKTRACKET {
// 		public MazeGenerator create() {
// 			return new RecursiveBacktracker
// 		}
// 	}
// }

public class MazeGeneratorFactory {
	private Maze maze;

	public static MazeGenerator getMazeGenerator(String generatorType, Maze maze) {
		if (generatorType == null || maze == null) {
			return null;
		}

		if (generatorType.equalsIgnoreCase("RECURSIVE")) {
			return new RecursiveBacktracker(maze);
		}

		return null;
	}
}