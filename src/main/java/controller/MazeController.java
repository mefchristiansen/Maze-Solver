package controller;

import controller.listener.AlgorithmSelectListener;
import controller.listener.MazeClickListener;
import model.Maze;
import model.MazeGenerator;
import model.MazeSolver;
import view.MazeView;
import view.drawable.SelectionFrame;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;

public class MazeController implements java.awt.event.ActionListener {
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

    public MazeController(){}

    public void addModel(Maze maze, MazeGenerator generator, MazeSolver solver) {
        this.maze = maze;
        this.generator = generator;
        this.solver = solver;
    }

    public void addView(MazeView view) {
        this.view = view;
        this.algorithmSelectListener = new AlgorithmSelectListener(this, view);
        this.mazeClickListener = new MazeClickListener(this.view);
        this.view.mazeDisplay.addMouseListener(this.mazeClickListener);
    }

    public void solveMaze() {
        view.setDisplayState("solve");
        solver.solve(maze.startingCell.row(), maze.startingCell.col(), maze.endingCell.row(), maze.endingCell.col());
        solver.walkSolutionPath();
    }

    public void generateMaze() {
        view.setDisplayState("generate");
        generator.generateMaze();
        maze.resetMaze();
    }

    public void launch() {
        generateMaze();

        this.mazeClickListener.enable();

        synchronized (view) {
            while(maze.startingCell == null || maze.endingCell == null) {
                try {
                    view.wait();

                } catch(InterruptedException e) {
                }
            }
            this.view.mazeDisplay.removeMouseListener(this.mazeClickListener);
        }

        solveMaze();
    }

    private void displaySelectFrame() {
        this.view.selectionFrame = new SelectionFrame();
        this.view.selectionFrame.aStarButton.addMouseListener(algorithmSelectListener);
        this.view.selectionFrame.dfsButton.addMouseListener(algorithmSelectListener);
        this.view.selectionFrame.bfsButton.addMouseListener(algorithmSelectListener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
    }
}
