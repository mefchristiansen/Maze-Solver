package view;

import view.drawable.MazePanel;
import view.drawable.GUIPanel;
import view.drawable.MazeSolverSelectionFrame;

import javax.swing.*;
import java.awt.*;

import java.util.Observable;

public class MazeSolverView extends JFrame implements java.util.Observer {
    public MazePanel mazeDisplay;

    public GUIPanel guiPanel;

    public MazeSolverSelectionFrame selectionFrame;

    public MazeSolverView(model.Maze maze, int scale, int margin, long generationSleep, long solveSleep, long solutionSleep) {
        super("Maze Solver - Marcus Christiansen");
        this.mazeDisplay = new MazePanel(maze, scale, margin, "generate", generationSleep, solveSleep, solutionSleep);
        this.guiPanel = new GUIPanel();

        initDisplay();
    }

    private void initDisplay() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        getContentPane().add(this.mazeDisplay, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        getContentPane().add(this.guiPanel, gbc);

        pack();

        setVisible(true);
    }

    public void setDisplayState(String displayState) {
        this.mazeDisplay.setDisplayState(displayState);
    }

    public void update(Observable o, Object arg) {
        // Who sent us a notification?
        // if (o.getClass() == MazeGenerator.class) {
            // We can directly call the maze display since we know that it has inherited the same maze object as the model.
            this.mazeDisplay.generationAnimate();
        // }
        // else if (o.getClass() == MazeSolver.class) {
            // if (arg != null) {
                // this.mazeDisplay.solveAnimate();
            // } else {
                // this.mazeDisplay.solutionAnimate();
            // }
        // }

    }
}