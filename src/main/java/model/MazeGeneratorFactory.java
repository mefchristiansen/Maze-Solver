package model;

import model.generators.*;

public class MazeGeneratorFactory {

	public static MazeGenerator initMazeGenerator(GeneratorType generatorType, Maze maze) {
        if (maze == null) {
            return null;
        }

        MazeGenerator mazeGenerator;

        switch(generatorType) {
            case RECURSIVE_BACKTRACKER:
                mazeGenerator = new RecursiveBacktracker(maze);
                break;

            // Default to recursive backtracker
            default:
                mazeGenerator = new RecursiveBacktracker(maze);
                break;
        }

		return mazeGenerator;
	}
}