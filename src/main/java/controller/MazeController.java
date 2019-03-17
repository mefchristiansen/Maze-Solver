package controller;

import controller.listeners.AlgorithmSelectListener;
import controller.listeners.MazeClickListener;
import model.Maze;
import model.MazeGenerator;
import model.MazeGeneratorFactory;
import model.MazeSolver;
import model.MazeSolverFactory;
import view.MazeView;
import view.drawable.SelectionFrame;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

public class MazeController implements java.awt.event.ActionListener {
    private static final int CELL_SIZE = 25;
    private static final int MARGIN = 10;
    private static final long GENERATION_SLEEP_TIME = 10L;
    private static final long SOLVE_SLEEP_TIME = 25L;
    private static final long SOLUTION_SLEEP_TIME = 15L;

    // Model
    private Maze maze;
    private MazeGenerator generator;
    private MazeSolver solver;

    // View
    private MazeView view;

    // Listeners
    private MazeClickListener mazeClickListener;
    private MouseAdapter algorithmSelectListener;

    // Which algorithm to run
    public String searchAlgorithm;

    public MazeController(Maze maze) {
        this.maze = maze;

        // Create view
        MazeView mazeView = new MazeView(maze, CELL_SIZE, MARGIN,
        GENERATION_SLEEP_TIME, SOLVE_SLEEP_TIME, SOLUTION_SLEEP_TIME);
        addView(mazeView);
    }

    public void addView(MazeView view) {
        this.view = view;
        this.algorithmSelectListener = new AlgorithmSelectListener(this, view);
        this.mazeClickListener = new MazeClickListener(this.view);
        this.view.mazeDisplay.addMouseListener(this.mazeClickListener);
    }

    public void launch() {
        generateMaze();
        setEndpoints();
        solveMaze();
    }

    private void solveMaze() {
        setMazeSolver();
        view.setDisplayState("solve");
        solver.solve(maze.startingCell.row(), maze.startingCell.col(), maze.endingCell.row(), maze.endingCell.col());
        solver.walkSolutionPath();
    }

    private void setEndpoints() {
        this.mazeClickListener.enable();

        synchronized (view) {
            while(maze.startingCell == null || maze.endingCell == null) {
                try {
                    view.wait();
                } catch(InterruptedException e) {
                    System.out.println(e);
                }
            }
            this.view.mazeDisplay.removeMouseListener(this.mazeClickListener);
        }
    }

    private void generateMaze() {
        setMazeGenerator();
        view.setDisplayState("generate");
        generator.generateMaze();
        maze.resetMaze();
        // displaySelectFrame();
    }

    private void displaySelectFrame() {
        this.view.selectionFrame = new SelectionFrame();
        this.view.selectionFrame.aStarButton.addMouseListener(algorithmSelectListener);
        this.view.selectionFrame.dfsButton.addMouseListener(algorithmSelectListener);
        this.view.selectionFrame.bfsButton.addMouseListener(algorithmSelectListener);
    }

    private void setMazeGenerator() {
        MazeGenerator generator = MazeGeneratorFactory.getMazeGenerator("RECURSIVE", maze);
        // Have the view listen in on events triggered by the model
        generator.addObserver(this.view);

        this.generator = generator;
    }

    private void setMazeSolver() {
        MazeSolver solver = MazeSolverFactory.getMazeSolver("DFS", maze);
        // Have the view listen in on events triggered by the model
        solver.addObserver(this.view);

        this.solver = solver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
