package view;

import model.Maze;
import controller.MazeController;
import model.MazeState;
import view.drawable.MazePanel;
import view.drawable.GUIPanel;

import javax.swing.*;
import java.awt.*;

public class MazeSolverView extends JFrame {
    public MazePanel mazePanel;
    public GUIPanel guiPanel;

    public MazeSolverView(Maze maze, MazeController mazeController) {
        super("Maze Solver - Marcus Christiansen");
        this.mazePanel = new MazePanel(maze);
        this.guiPanel = new GUIPanel(mazeController);

        initDisplay();
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

        setVisible(true);
    }

    public void repaintMaze() {
        mazePanel.repaint();
    }

    public void setMazeState(MazeState mazeState) {
        this.mazePanel.setMazeState(mazeState);
    }
}