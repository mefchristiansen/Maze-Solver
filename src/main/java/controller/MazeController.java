package controller;

import controller.listeners.*;
import model.*;
import view.MazeView;

import javax.swing.*;

/**
 * The controller of the maze (i.e. the controller in the MVC design pattern). This is the main orchestrator of the
 * application, initializing and controlling the maze and the maze view, handling GUI interactions, and managing the
 * SwingWorker threads for maze generation, solving, and solution path drawing.
 */
public class MazeController {
	// Model
	private final Maze maze;
	// View
	private final MazeView view;
	//// Custom Maze Dimensions
	private final MazeCustomNumRowsListener mazeCustomNumRowsListener;
	private final MazeCustomNumColsListener mazeCustomNumColsListener;
	//// Buttons
	private final MazeGeneratorListener mazeGeneratorListener;
	private final MazeSolverListener mazeSolverListener;
	private final MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
	private final MazeResetListener mazeResetListener;
	//// Speed Slider
	private final MazeAnimationSpeedSliderListener mazeAnimationSpeedSliderListener;
	private MazeState state; // Stores the current state of the maze.
	private int animationSpeed;

	// Listeners
	private int numRows;
	private int numCols;
	private GeneratorType generatorType;
	private MazeGeneratorWorker generator;
	private SolverType solverType;
	private MazeSolverWorker solver;
	private MazeSolutionWalkerWorker solutionWalker;

	public MazeController() {
		this.state = MazeState.INIT;

		this.maze = new Maze();
		this.generatorType = GeneratorType.RECURSIVE_BACKTRACKER;
		this.solverType = SolverType.BFS;

		this.mazeCustomNumRowsListener = new MazeCustomNumRowsListener(this);
		this.mazeCustomNumColsListener = new MazeCustomNumColsListener(this);

		this.mazeGeneratorListener = new MazeGeneratorListener(this);
		this.mazeSolverSelectionRadioListener = new MazeSolverSelectionRadioListener(this);
		this.mazeSolverListener = new MazeSolverListener(this);
		this.mazeResetListener = new MazeResetListener(this);

		this.mazeAnimationSpeedSliderListener = new MazeAnimationSpeedSliderListener(this);

		this.view = new MazeView(maze, this);

		this.animationSpeed = MazeAnimationConstants.DEFAULT_ANIMATION_SLEEP;

		this.numRows = MazeConstants.DEFAULT_NUM_ROWS;
		this.numCols = MazeConstants.DEFAULT_NUM_COLS;
	}

	public MazeState getState() {
		return state;
	}

	public void setGeneratorType(GeneratorType generatorType) {
		this.generatorType = generatorType;
	}

	public SolverType getSolverType() {
		return solverType;
	}

	public void setSolverType(SolverType solverType) {
		this.solverType = solverType;
	}

	public MazeGeneratorListener getMazeGeneratorListener() {
		return mazeGeneratorListener;
	}

	public MazeSolverSelectionRadioListener getMazeSolverSelectionRadioListener() {
		return mazeSolverSelectionRadioListener;
	}

	public MazeSolverListener getMazeSolverListener() {
		return mazeSolverListener;
	}

	public MazeResetListener getMazeResetListener() {
		return mazeResetListener;
	}

	public MazeCustomNumRowsListener getMazeCustomNumRowsListener() {
		return mazeCustomNumRowsListener;
	}

	public MazeCustomNumColsListener getMazeCustomNumColsListener() {
		return mazeCustomNumColsListener;
	}

	public MazeAnimationSpeedSliderListener getMazeAnimationSpeedSliderListener() {
		return mazeAnimationSpeedSliderListener;
	}

	public void setMazeNumRows(int numRows) {
		this.numRows = numRows;
	}

	public void setMazeNumCols(int numCols) {
		this.numCols = numCols;
	}

	/**
	 * Returns the thread sleep time for animation based on the current animation speed (set by the speed slider) and
	 * maze state. In an effort to limit the maximum speed of animation, the animation speed is scaled by a scaling
	 * factor corresponding to the current maze state. As some stages are more interesting to visualize than others,
	 * each stage has an individual scaling factor.
	 *
	 * @return the thread sleep time for animation
	 */
	public long getAnimationSpeed() {
		double animationSpeedMultiplier;

		switch (state) {
			case GENERATING:
				animationSpeedMultiplier = MazeAnimationConstants.GENERATION_SLEEP_TIME_MULTIPLIER;
				break;
			case SOLVING:
				animationSpeedMultiplier = MazeAnimationConstants.SOLVE_SLEEP_TIME_MULTIPLIER;
				break;
			case SOLVED:
				animationSpeedMultiplier = MazeAnimationConstants.SOLUTION_SLEEP_TIME_MULTIPLIER;
				break;
			default:
				animationSpeedMultiplier = MazeAnimationConstants.DEFAULT_ANIMATION_SLEEP;
				break;
		}

		return (long) (animationSpeed * animationSpeedMultiplier);
	}

	public void setAnimationSpeed(int animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	public void generate() {
		initGenerate();
		generateMaze();
	}

	private void initGenerate() {
		maze.initMaze(numRows, numCols);
		view.resize();
		generator = MazeGeneratorWorkerFactory.initMazeGenerator(generatorType, maze, this);
	}

	private void generateMaze() {
		state = MazeState.GENERATING;
		setInstructions();
		generator.execute();
	}

	/**
	 * Function called on maze generation success to update the maze state, and to default the starting and ending cell
	 * for solving the maze.
	 */
	public void generateMazeSuccess() {
		state = MazeState.GENERATED;
		maze.defaultWaypoints();
		setInstructions();
	}

	public void solve() {
		initSolve();
		solveMaze();
	}

	private void initSolve() {
		solver = MazeSolverWorkerFactory.initMazeSolver(solverType, maze, this);
	}

	private void solveMaze() {
		state = MazeState.SOLVING;
		setInstructions();
		solver.execute();
	}

	/**
	 * Function called on maze generation success to update the maze state, and to trigger the solution path walker.
	 */
	public void solveMazeSuccess() {
		state = MazeState.SOLVED;
		walkSolutionPath();
		setInstructions();
	}

	/**
	 * Executes the solution path walker to draw the solution path from the starting cell to the ending cell.
	 */
	private void walkSolutionPath() {
		solutionWalker = new MazeSolutionWalkerWorker(maze, this);
		solutionWalker.execute();
	}

	/**
	 * Resets the maze to its initial state, ready to generate a new maze. This includes cancelling all currently
	 * running threads, resetting the maze cells, and repainting the view.
	 */
	public void reset() {
		resetThreads();

		state = MazeState.INIT;
		setInstructions();

		maze.resetMaze();
		view.resetView();
	}

	/**
	 * Cancels any currently running SwingWorker.
	 */
	private void resetThreads() {
		if (generator != null) {
			generator.cancel(true);
			generator = null;
		}

		if (solver != null) {
			solver.cancel(true);
			solver = null;
		}

		if (solutionWalker != null) {
			solutionWalker.cancel(true);
			solutionWalker = null;
		}
	}

	public void repaintMaze(Maze newMaze) {
		view.repaintMaze(newMaze);
	}

	/**
	 * Updates instructions for maze on the GUI (based on the maze state) asynchronously.
	 */
	public void setInstructions() {
		SwingUtilities.invokeLater(view::setInstructions);
	}
}

/*

	* Old method to force the user to select the start and end points for maze solving. In the current version,
	* the user has a choice as to whether or not to set the start and end points.

	private void setWaypoints() {
		synchronized (view) {
			while(maze.startingCell == null || maze.endingCell == null) {
				try {
					view.wait();
				} catch(InterruptedException e) {
					System.out.println(e);
				}
			}
		}
	}

*/
