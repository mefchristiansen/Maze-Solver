package view;

import model.Maze;
import controller.MazeController;
import view.drawable.MazePanel;
import view.drawable.GUIPanel;

import javax.swing.*;
import java.awt.*;

public class MazeView extends JFrame {
    private final MazePanel mazePanel;
    private final GUIPanel guiPanel;
//    private final JLabel instructions;

    public MazeView(Maze maze, MazeController mazeController) {
        super("Maze Solver - Marcus Christiansen");
        this.mazePanel = new MazePanel(maze, mazeController);
        this.guiPanel = new GUIPanel(mazeController);
//        this.instructions = new JLabel();

        initDisplay();
    }

    private void initDisplay() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridBagLayout());

//        instructions.setText("Generate a maze");

        addComponent(mazePanel, 0, 0, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
        addComponent(guiPanel, 2, 0, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL);
//        addComponent(instructions, 0, 1, 2, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        pack();

        setVisible(true);
    }

//    public void setInstructions(String instruction) {
//        instructions.setText(instruction);
//    }

    public void resize() {
        mazePanel.resize();
        pack();
    }

    public void resetView() {
        mazePanel.resetWaypointSetterState();
        mazePanel.repaint();
    }

    public void repaintMaze(Maze maze) {
        mazePanel.repaintMaze(maze);
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(5, 5, 5, 5);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        getContentPane().add(component, gbc);
    }
}
