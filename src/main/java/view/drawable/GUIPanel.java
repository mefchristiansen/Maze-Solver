package view.drawable;

import controller.MazeController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GUIPanel extends JPanel {
    private final MazeController mazeController;

    public GUIPanel(MazeController mazeController) {
        this.mazeController = mazeController;
        initGUIPanel();
    }

    private void initGUIPanel() {
        setLayout(new GridLayout(0,1));

//        GridBagConstraints gbc = new GridBagConstraints();

        // Generate Maze Button
        JButton generateMazeButton = new JButton("Generate");
        generateMazeButton.addActionListener(mazeController.getMazeGeneratorListener());
        add(generateMazeButton);

        // Solve Method Radio
        JPanel solveMethodRadio = initSolveMethodRadio();
        add(solveMethodRadio);

        // Solve Maze Button
        JButton solveMazeButton = new JButton("Solve");
        solveMazeButton.addActionListener(mazeController.getMazeSolverListener());
        add(solveMazeButton);

        // Reset Maze Button
        JButton resetMazeButton = new JButton("Reset");
        add(resetMazeButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        add(exitButton);
    }

    private JPanel initSolveMethodRadio() {
        JPanel solveMethodRadioPanel = new JPanel();

        TitledBorder solveMethodTitledBorder = new TitledBorder("Solve Method");

        solveMethodRadioPanel.setBorder(solveMethodTitledBorder);

        JRadioButton bfsRadio = new JRadioButton("BFS");
        JRadioButton dfsRadio = new JRadioButton("DFS");
        JRadioButton aStarRadio = new JRadioButton("A*");

        Box solveMethodRadioBox = Box.createVerticalBox();
        solveMethodRadioBox.add(bfsRadio);
        solveMethodRadioBox.add(dfsRadio);
        solveMethodRadioBox.add(aStarRadio);

        ButtonGroup solveMethodRadioButtonGroup = new ButtonGroup();
        solveMethodRadioButtonGroup.add(bfsRadio);
        solveMethodRadioButtonGroup.add(dfsRadio);
        solveMethodRadioButtonGroup.add(aStarRadio);

        solveMethodRadioPanel.add(solveMethodRadioBox);

        return solveMethodRadioPanel;
    }
}
