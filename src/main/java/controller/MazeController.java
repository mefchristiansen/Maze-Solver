package controller;

import controller.listeners.*;
import model.MazeState;
import model.Maze;
import model.MazeGenerator;
import model.MazeGeneratorFactory;
import model.MazeSolver;
import model.MazeSolverFactory;
import model.GeneratorType;
import model.SolverType;
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
    private MazeWaypointClickListener mazeWaypointClickListener;
    private MazeGeneratorListener mazeGeneratorListener;
    private MazeSolverListener mazeSolverListener;
    private MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
    private MazeResetListener mazeResetListener;

    private AtomicBoolean runState;

    public MazeController() {
        this.state = MazeState.INIT;

        this.maze = new Maze();
        this.generatorType = GeneratorType.RECURSIVE_BACKTRACKER;
        this.solverType = SolverType.BFS;

        this.mazeGeneratorListener = new MazeGeneratorListener(this);
        this.mazeSolverSelectionRadioListener = new MazeSolverSelectionRadioListener(this);
        this.mazeSolverListener = new MazeSolverListener(this);
        this.mazeResetListener = new MazeResetListener(this);

        this.view = new MazeSolverView(maze, this);

        this.runState = new AtomicBoolean(true);

        this.mazeWaypointClickListener = new MazeWaypointClickListener(this.view, this);
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

    private void updateMazeViewState() {
        view.setMazeState(state);
    }

    public void initGenerate() {
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
