package controller;

import controller.listeners.*;
import model.*;
import view.MazeView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MazeController {
    private MazeState state;

    // Model
    private Maze maze;
    private GeneratorType generatorType;
    private MazeGenerator generator;
    private SolverType solverType;
    private MazeSolver solver;

    // View
    private MazeView view;

    // Listeners

    //// Custom Maze Dimensions
    private MazeCustomNumRowsListener mazeCustomNumRowsListener;
    private MazeCustomNumColsListener mazeCustomNumColsListener;

    //// Buttons
    private MazeGeneratorListener mazeGeneratorListener;
    private MazeSolverListener mazeSolverListener;
    private MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
    private MazeResetListener mazeResetListener;

    //// Speed Slider
    private MazeAnimationSpeedSliderListener mazeAnimationSpeedSliderListener;

    private AtomicBoolean runState;

    private int numRows;
    private int numCols;

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

        this.runState = new AtomicBoolean(true);

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
        view.setAnimationSpeed(animationSpeed);
    }

    public void initGenerate() {
        maze.initMaze(numRows, numCols);
        view.resize();

        generator = MazeGeneratorFactory.initMazeGenerator(generatorType, maze, this);
        generator.addChangeListener(view);
        state = MazeState.GENERATING;
    }

    public void generateMaze() {
        if (generator.generateMaze()) {
            state = MazeState.GENERATED;
            mazeGeneratorListener.resetGenerator();
        }
    }

    public void initSolve() {
        solver = MazeSolverFactory.initMazeSolver(solverType, maze, this);
        solver.addChangeListener(view);
        state = MazeState.SOLVING;
    }

    public void solveMaze() {
        if (!maze.waypointsSet()) {
            maze.defaultWaypoints();
        }

        if(solver.solve()) {
            state = MazeState.SOLVED;
            mazeSolverListener.resetSolver();
            solver.walkSolutionPath();
        }
    }

    public void resetMaze() {
        setRunState(false);

        mazeGeneratorListener.resetGenerator();
        mazeSolverListener.resetSolver();

        state = MazeState.INIT;

        maze.resetMaze();
        view.resetView();

        setRunState(true);
    }

    private void setRunState(boolean runState) {
        this.runState.set(runState);
    }

    public boolean isInterrupted() {
        return !this.runState.get();
    }

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
