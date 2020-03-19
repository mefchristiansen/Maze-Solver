package view;

import model.Maze;
import controller.MazeController;
import view.drawable.MazePanel;
import view.drawable.GUIPanel;
import view.drawable.MazeSolverSelectionFrame;

import javax.swing.*;
import java.awt.*;

public class MazeSolverView extends JFrame {
    public MazePanel mazePanel;

    public GUIPanel guiPanel;

    public MazeSolverSelectionFrame selectionFrame;

    public MazeSolverView(Maze maze, MazeController mazeController, int scale, int margin, long generationSleep, long solveSleep, long solutionSleep) {
        super("Maze Solver - Marcus Christiansen");
        this.mazePanel = new MazePanel(maze, scale, margin, "generate", generationSleep, solveSleep, solutionSleep);
        this.guiPanel = new GUIPanel(mazeController);

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
}