package controller;

import controller.listeners.*;
import model.*;
import view.MazeSolverView;

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
    private MazeSolverView view;

    // Listeners

    //// Custom Maze Dimensions
    private MazeCustomNumRowsListener mazeCustomNumRowsListener;
    private MazeCustomNumColsListener mazeCustomNumColsListener;

    //// Buttons
    private MazeGeneratorListener mazeGeneratorListener;
    private MazeSolverListener mazeSolverListener;
    private MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
    private MazeResetListener mazeResetListener;

    //// Waypoints
    private MazeWaypointClickListener mazeWaypointClickListener;

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

        this.mazeWaypointClickListener = new MazeWaypointClickListener(this.view, this);

        this.view = new MazeSolverView(maze, this);

        this.runState = new AtomicBoolean(true);

        this.numRows = MazeConstants.DEFAULT_NUM_ROWS;
        this.numCols = MazeConstants.DEFAULT_NUM_COLS;

        // TODO: Should this be moved?
        this.view.mazePanel.addMouseListener(this.mazeWaypointClickListener);
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

    private void updateMazeViewState() {
        view.setMazeState(state);
    }

    public void setMazeNumRows(int numRows) {
        this.numRows = numRows;
    }

    public void setMazeNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void initGenerate() {
        maze.initMaze(numRows, numCols);
        view.resize();

        generator = MazeGeneratorFactory.initMazeGenerator(generatorType, maze, this);
        generator.addChangeListener(this.view.mazePanel);
        updateMazeViewState();
        state = MazeState.GENERATING;
    }

    public void generateMaze() {
        if (generator.generateMaze()) {
            state = MazeState.GENERATED;
            updateMazeViewState();
            mazeGeneratorListener.resetGenerator();
        }
    }

    public void initSolve() {
        solver = MazeSolverFactory.initMazeSolver(solverType, maze, this);
        solver.addChangeListener(this.view.mazePanel);
        state = MazeState.SOLVING;
        updateMazeViewState();
    }

    public void solveMaze() {
        if (!maze.waypointsSet()) {
            maze.defaultWaypoints();
        }

        if(solver.solve()) {
            state = MazeState.SOLVED;
            updateMazeViewState();
            mazeSolverListener.resetSolver();

            updateMazeViewState();
            solver.walkSolutionPath();
        }
    }

    public void resetMaze() {
        setRunState(false);

        mazeGeneratorListener.resetGenerator();
        mazeSolverListener.resetSolver();

        maze.startingCell = maze.endingCell = null;

        maze.resetMaze();
        view.resetWaypointSetterState();
        view.repaintMaze();

        state = MazeState.INIT;

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
