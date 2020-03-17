package view;

import view.drawable.MazePanel;
import view.drawable.GUIPanel;
import view.drawable.MazeSolverSelectionFrame;

import javax.swing.*;
import java.awt.*;

import java.util.Observable;

public class MazeSolverView extends JFrame implements java.util.Observer {
    public MazePanel mazePanel;

    public GUIPanel guiPanel;

    public MazeSolverSelectionFrame selectionFrame;

    public MazeSolverView(model.Maze maze, int scale, int margin, long generationSleep, long solveSleep, long solutionSleep) {
        super("Maze Solver - Marcus Christiansen");
        this.mazePanel = new MazePanel(maze, scale, margin, "generate", generationSleep, solveSleep, solutionSleep);
        this.guiPanel = new GUIPanel();

        initDisplay();

        setVisible(true);
    }

    private void initDisplay() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.VERTICAL;

        getContentPane().add(this.mazePanel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.VERTICAL;

        getContentPane().add(this.guiPanel, gbc);

        pack();
    }

    public void setDisplayState(String displayState) {
        this.mazePanel.setDisplayState(displayState);
    }

    public void update(Observable o, Object arg) {
        // Who sent us a notification?
        // if (o.getClass() == MazeGenerator.class) {
            // We can directly call the maze display since we know that it has inherited the same maze object as the model.
            this.mazePanel.generationAnimate();
        // }
        // else if (o.getClass() == MazeSolver.class) {
            // if (arg != null) {
                // this.mazePanel.solveAnimate();
            // } else {
                // this.mazePanel.solutionAnimate();
            // }
        // }

    }
}