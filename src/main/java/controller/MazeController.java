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

public class MazeController {
    // Model
    private Maze maze;

    private GeneratorType generatorType;
    private SolverType solverType;

    // View
    private MazeSolverView view;

    // Listeners
    private MazeClickListener mazeClickListener;

    private MazeGeneratorListener mazeGeneratorListener;
    private MazeSolverListener mazeSolverListener;
    private MazeSolverSelectionRadioListener mazeSolverSelectionRadioListener;
    private MazeResetListener mazeResetListener;

//    private MazeGUIListener mazeGUIListener;

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
        setEndpoints();
    }

    private void setEndpoints() {
        this.mazeClickListener = new MazeClickListener(this.view);
        this.view.mazePanel.addMouseListener(this.mazeClickListener);
        this.mazeClickListener.enable();

        synchronized (view) {
            while(maze.startingCell == null || maze.endingCell == null) {
                try {
                    view.wait();
                } catch(InterruptedException e) {
                    System.out.println(e);
                }
            }
            this.view.mazePanel.removeMouseListener(this.mazeClickListener);
        }
    }

    private void generateMaze() {
        MazeGenerator generator = MazeGeneratorFactory.initMazeGenerator(generatorType, maze);
        generator.addChangeListener(this.view.mazePanel);
        setViewDisplayState("generate");
        generator.generateMaze();
    }

    public void solveMaze() {
        MazeSolver solver = MazeSolverFactory.initMazeSolver(solverType, maze);
        solver.addChangeListener(this.view.mazePanel);
        setViewDisplayState("solve");
        solver.solve();

        setViewDisplayState("solution");
        solver.walkSolutionPath();
    }

    public void resetMaze() {
        maze.resetMaze();
        view.repaintMaze();
    }
}
