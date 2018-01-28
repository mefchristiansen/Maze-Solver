import javax.swing.JFrame;
import java.util.concurrent.TimeUnit;

public class MazeRunner {
	private static final int NUM_ROWS = 25;
	private static final int NUM_COLS = 25;
	private static final int CELL_SIZE = 25;
	private static final int MARGIN = 10;
	private static final long SLEEP_TIME = 5L;

	public static void main(String[] args) {
		Maze maze = new Maze(NUM_ROWS, NUM_COLS);

		JFrame frame = new JFrame("Maze");
        MazeDisplay mazeDisplay = new MazeDisplay(maze, CELL_SIZE, MARGIN, SLEEP_TIME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mazeDisplay);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        MazeGenerator mazeGenerator = new MazeGenerator(maze, mazeDisplay);
        mazeGenerator.generateMaze();

        maze.initVisited();

        MazeSolver mazeSolver = new MazeSolver(maze, mazeDisplay);
        mazeSolver.DFSSolve(0, 0, NUM_ROWS - 1, NUM_COLS - 1);
	}

}