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

        addComponent(this.mazePanel, 0, 0, 1, 2, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(this.guiPanel, 2, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        pack();

        setVisible(true);
    }

    public void resize() {
        mazePanel.resize();
        pack();
    }

    public void repaintMaze() {
        mazePanel.repaint();
    }

    public void setMazeState(MazeState mazeState) {
        this.mazePanel.setMazeState(mazeState);
    }

    public void resetWaypointSetterState() {
        this.mazePanel.resetWaypointSetterState();
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        getContentPane().add(component, gbc);
    }
}