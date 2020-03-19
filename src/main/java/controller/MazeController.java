package controller;

import model.Maze;
import model.MazeGenerator;
import model.MazeGeneratorFactory;
import model.MazeSolver;
import model.MazeSolverFactory;
import model.GeneratorType;
import model.SolverType;
import view.MazeSolverView;
import view.drawable.MazeSolverSelectionFrame;
import controller.listeners.AlgorithmSelectListener;
import controller.listeners.MazeClickListener;

public class MazeController {
    private static final int CELL_SIZE = 20;
    private static final int MARGIN = 10;
    private static final long GENERATION_SLEEP_TIME = 15L;
    private static final long SOLVE_SLEEP_TIME = 50L;
    private static final long SOLUTION_SLEEP_TIME = 50L;

    // Model
    private Maze maze;
    private MazeGenerator generator;
    private MazeSolver solver;

    // View
    private MazeSolverView view;

    // Listeners
    private MazeClickListener mazeClickListener;
    private AlgorithmSelectListener algorithmSelectListener;

    public MazeController(Maze maze) {
        this.maze = maze;

        // Create view
        MazeSolverView mazeView = new MazeSolverView(maze, this, CELL_SIZE, MARGIN,
        GENERATION_SLEEP_TIME, SOLVE_SLEEP_TIME, SOLUTION_SLEEP_TIME);
        this.view = mazeView;
    }

    public void launch() {
        initMaze();
        solveMaze();
        showSolution();
    }

    public void initMaze() {
        generateMaze();

//        setSolverMethod();
//        if (solver == null) {
//            setMazeSolver(null);
//        }
        setEndpoints();
    }

    public void solveMaze() {
        setMazeSolver(SolverType.AStar);
        view.setDisplayState("solve");
        solver.solve(maze.startingCell.row(), maze.startingCell.col(), maze.endingCell.row(), maze.endingCell.col());
        showSolution();
    }

    private void showSolution() {
        view.setDisplayState("solution");
        solver.walkSolutionPath();
    }

    public void generateMaze() {
        setMazeGenerator();
        view.setDisplayState("generate");
        generator.generateMaze();
        maze.resetMaze();
    }

    public void setSolverMethod() {
        this.algorithmSelectListener = new AlgorithmSelectListener(this, view);
        this.view.selectionFrame = new MazeSolverSelectionFrame(algorithmSelectListener);
        this.algorithmSelectListener.enable();

        synchronized (view.selectionFrame) {
            while(solver == null) {
                try {
                    view.selectionFrame.wait();
                } catch(InterruptedException e) {
                    System.out.println(e);
                }
            }
            // this.view.mazePanel.removeMouseListener(this.mazeClickListener);
        }
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

    public void setMazeGenerator() {
        MazeGenerator generator = MazeGeneratorFactory.initMazeGenerator(GeneratorType.RECURSIVE_BACKTRACKER, maze);
        // Have the view listen in on events triggered by the model
        generator.addChangeListener(this.view.mazePanel);

        this.generator = generator;
    }

    public void setMazeSolver(SolverType solverType) {
        MazeSolver solver = MazeSolverFactory.initMazeSolver(solverType, maze);
        // Have the view listen in on events triggered by the model
        solver.addChangeListener(this.view.mazePanel);

        this.solver = solver;
    }
}
