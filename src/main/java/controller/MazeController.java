package controller;

import controller.listeners.*;

import model.*;

import view.MazeView;
import view.drawable.MazeDrawableConstants;

/**
 *
 */
public class MazeController {
    private MazeState state;
	private int animationSpeed;
	private int numRows;
	private int numCols;

    // Model
    private final Maze maze;
    private GeneratorType generatorType;
    private MazeGeneratorWorker generator;
    private SolverType solverType;
    private MazeSolverWorker solver;
    private MazeSolutionWalkerWorker solutionWalker;

    // View
    private final MazeView view;

    // Listeners

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

        this.animationSpeed = MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP;

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

    public void setAnimationSpeed(int animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    public long getAnimationSpeed() {
        double animationSpeedMultiplier;

        switch (state) {
            case GENERATING:
                animationSpeedMultiplier = MazeDrawableConstants.GENERATION_SLEEP_TIME_MULTIPLIER;
                break;
            case SOLVING:
                animationSpeedMultiplier = MazeDrawableConstants.SOLVE_SLEEP_TIME_MULTIPLIER;
                break;
            case SOLVED:
                animationSpeedMultiplier = MazeDrawableConstants.SOLUTION_SLEEP_TIME_MULTIPLIER;
                break;
            default:
                animationSpeedMultiplier = MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP;
                break;
        }

        return (long)(animationSpeed * animationSpeedMultiplier);
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
        generator.execute();
    }

    public void generateMazeSuccess() {
        state = MazeState.GENERATED;
        maze.defaultWaypoints();
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
        solver.execute();
    }

    public void solveMazeSuccess() {
        state = MazeState.SOLVED;
        walkSolutionPath();
    }

    private void walkSolutionPath() {
        solutionWalker = new MazeSolutionWalkerWorker(maze, this);
        solutionWalker.execute();
    }

    public void reset() {
        resetThreads();

        state = MazeState.INIT;

        maze.resetMaze();
        view.resetView();
    }

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

//    public void setInstructions(String instruction) {
//        view.setInstructions(instruction);
//    }

    /*
        private void setEndpoints() {
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
}
