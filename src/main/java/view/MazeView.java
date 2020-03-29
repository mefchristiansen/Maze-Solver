package view;

import model.Maze;
import controller.MazeController;
import model.MazeConstants;
import model.MazeState;
import view.drawable.MazeDrawableConstants;
import view.drawable.MazePanel;
import view.drawable.GUIPanel;

import javax.swing.*;
import java.awt.*;

public class MazeView extends JFrame {
    public MazeController mazeController;

    public MazePanel mazePanel;
    public GUIPanel guiPanel;

    public MazeView(Maze maze, MazeController mazeController) {
        super("Maze Solver - Marcus Christiansen");
        this.mazeController = mazeController;
        this.mazePanel = new MazePanel(maze, mazeController);
        this.guiPanel = new GUIPanel(mazeController);

        initDisplay();
    }

    private void initDisplay() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

        addComponent(mazePanel, 0, 0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(guiPanel, 2, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);

        pack();

        setVisible(true);
    }

    public void resize() {
        mazePanel.resize();
        pack();
    }

    public void resetView() {
        mazePanel.resetWaypointSetterState();
        mazePanel.repaint();
    }

    public void setAnimationSpeed(int animationSpeed) { mazePanel.setAnimationSpeed(animationSpeed); }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        getContentPane().add(component, gbc);
    }
}