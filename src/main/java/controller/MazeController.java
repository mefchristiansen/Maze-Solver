package controller;

import controller.listeners.*;
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
    // Model
    private Maze maze;
    private GeneratorType generatorType;
    private SolverType solverType;

    // View
    private MazeSolverView view;

    // Listeners
    private MazeWaypointClickListener mazeWaypointClickListener;
    private MazeGeneratorListener mazeGeneratorListener;
    private MazeSolverListener mazeSolverListener;
    private MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
    private MazeResetListener mazeResetListener;

//    private MazeGUIListener mazeGUIListener;

    private AtomicBoolean run;

    public MazeController() {
        this.maze = new Maze();

        this.generatorType = GeneratorType.RECURSIVE_BACKTRACKER;
        this.solverType = SolverType.BFS;

        this.mazeGeneratorListener = new MazeGeneratorListener(this);
        this.mazeSolverSelectionRadioListener = new MazeSolverSelectionRadioListener(this);
        this.mazeSolverListener = new MazeSolverListener(this);
        this.mazeResetListener = new MazeResetListener(this);

//        this.mazeGUIListener = new MazeGUIListener(this);

        // Create view
        this.view = new MazeSolverView(maze, this);

        this.run = new AtomicBoolean(false);
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

//    public MazeGUIListener getMazeGUIListener() {
//        return mazeGUIListener;
//    }

    private void setViewDisplayState(String displayState) {
        view.setDisplayState(displayState);
    }

    public void initMaze() {
        generateMaze();
//        setEndpoints();
    }

    private void setEndpoints() {
        this.mazeWaypointClickListener = new MazeWaypointClickListener(this.view);
        this.view.mazePanel.addMouseListener(this.mazeWaypointClickListener);
        this.mazeWaypointClickListener.enable();

        synchronized (view) {
            while(maze.startingCell == null || maze.endingCell == null) {
                try {
                    view.wait();
                } catch(InterruptedException e) {
                    System.out.println(e);
                }
            }
            this.view.mazePanel.removeMouseListener(this.mazeWaypointClickListener);
        }
    }

    private void generateMaze() {
        MazeGenerator generator = MazeGeneratorFactory.initMazeGenerator(generatorType, maze, this);
        generator.addChangeListener(this.view.mazePanel);
        setViewDisplayState("generate");
        setRun(true);
        generator.generateMaze();
    }

    public void solveMaze() {
        MazeSolver solver = MazeSolverFactory.initMazeSolver(solverType, maze);
        solver.addChangeListener(this.view.mazePanel);
        setViewDisplayState("solve");
        setRun(true);
        solver.solve();

        setViewDisplayState("solution");
        solver.walkSolutionPath();
    }

    public void resetMaze() {
        setRun(false);

        maze.resetMaze();
        view.repaintMaze();
    }

    private void setRun(boolean runState) {
        this.run.set(runState);
    }

    public boolean run() {
        return this.run.get();
    }
}
