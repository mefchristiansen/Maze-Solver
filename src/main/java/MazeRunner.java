import controller.MazeController;
import model.*;
import view.MazeView;

public class MazeRunner {
	private static final int NUM_ROWS = 25;
	private static final int NUM_COLS = 25;
	private static final int CELL_SIZE = 25;
	private static final int MARGIN = 10;
    private static final long GENERATION_SLEEP_TIME = 10L;
    private static final long SOLVE_SLEEP_TIME = 25L;
    private static final long SOLUTION_SLEEP_TIME = 15L;

	public static void main(String[] args) {

        // Initialize model objects
        Maze maze = new Maze(NUM_ROWS, NUM_COLS);
        MazeGenerator mazeGenerator = new RecursiveBacktracker(maze);
        MazeSolver mazeSolver = new BFS(maze);

        // Create view
        MazeView mazeView = new MazeView(maze, CELL_SIZE, MARGIN,
        GENERATION_SLEEP_TIME, SOLVE_SLEEP_TIME, SOLUTION_SLEEP_TIME);

        // Have the view listen in on events triggered by the model
        mazeGenerator.addObserver(mazeView);
        mazeSolver.addObserver(mazeView);

        // Bind the model and view to the controller
        MazeController controller = new MazeController();
        controller.addModel(maze, mazeGenerator, mazeSolver);
        controller.addView(mazeView);

        // Start building maze
        controller.launch();
	}
}