import javax.swing.JFrame;

public class MazeRunner {
	private static final int NUM_ROWS = 24;
	private static final int NUM_COLS = 24;
	private static final int CELL_SIZE = 25;
	private static final int MARGIN = 10;

	public static void main(String[] args) {
		Maze maze = new Maze(NUM_ROWS, NUM_COLS);

		JFrame frame = new JFrame("Maze");
        MazeDisplay mazeDisplay = new MazeDisplay(maze, CELL_SIZE, MARGIN);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mazeDisplay);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        MazeGenerator mazeGenerator = new MazeGenerator(maze);
        // mazeGenerator
        mazeGenerator.generateMaze();

        MazeSolver mazeSolver = new MazeSolver(maze);
        mazeSolver.DFSSolve(0, 0, NUM_ROWS - 1, NUM_COLS - 1);
	}

}