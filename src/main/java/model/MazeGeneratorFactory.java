package model;

import controller.MazeController;
import model.generators.*;

public class MazeGeneratorFactory {

	public static MazeGenerator initMazeGenerator(GeneratorType generatorType, Maze maze, MazeController mazeController) {
        if (maze == null || mazeController == null) {
            return null;
        }

        MazeGenerator mazeGenerator;

        switch(generatorType) {
            case RECURSIVE_BACKTRACKER:
                mazeGenerator = new RecursiveBacktracker(maze, mazeController);
                break;

            // Default to recursive backtracker
            default:
                mazeGenerator = new RecursiveBacktracker(maze, mazeController);
                break;
        }

		return mazeGenerator;
	}
}