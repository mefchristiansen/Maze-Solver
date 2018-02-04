import javax.swing.JFrame;

public class MazeRunner {
	private static final int NUM_ROWS = 20;
	private static final int NUM_COLS = 20;
	private static final int CELL_SIZE = 25;
	private static final int MARGIN = 10;
	private static final long GENERATION_SLEEP_TIME = 1L;
	private static final long SOLVE_SLEEP_TIME = 25L;
	private static final long SOLUTION_SLEEP_TIME = 15L;
	private static final String SOLUTION_METHOD = "A*";

	public static void main(String[] args) {
        Maze maze = new Maze(NUM_ROWS, NUM_COLS);

        JFrame frame = new JFrame("Maze");
        MazeDisplay mazeDisplay = new MazeDisplay(maze, CELL_SIZE, MARGIN,
        	GENERATION_SLEEP_TIME, SOLVE_SLEEP_TIME, SOLUTION_SLEEP_TIME);
        // MazeClickListener mazeClickListener  = new MazeClickListener(mazeDisplay);
        // mazeDisplay.addMouseListener(mazeClickListener);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mazeDisplay);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        MazeGenerator mazeGenerator = new MazeGenerator(maze, mazeDisplay);
        mazeGenerator.generateMaze();

        maze.resetMaze();
        // mazeClickListener.enable();

        MazeSolver mazeSolver = new MazeSolver(maze, mazeDisplay, SOLUTION_METHOD);
        mazeSolver.solve();
        // mazeSolver.paintSolution();
	}
}