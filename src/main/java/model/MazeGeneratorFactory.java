package model;

import model.generators.*;

// public enum MazeGenerator {
// 	RECURSIVE_BACKTRACKET {
// 		public MazeGenerator create() {
// 			return new RecursiveBacktracker
// 		}
// 	}
// }

public class MazeGeneratorFactory {

	public static MazeGenerator initMazeGenerator(GeneratorType generatorType, Maze maze) {
        if (generatorType == null || maze == null) {
            return null;
        }

        MazeGenerator mazeGenerator = null;

        switch(generatorType) {
            case RECURSIVE_BACKTRACKER:
                mazeGenerator = new RecursiveBacktracker(maze);
                break;
            default:
                break;
        }

		return mazeGenerator;
	}
}