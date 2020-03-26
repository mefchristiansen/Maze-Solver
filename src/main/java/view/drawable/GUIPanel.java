package view.drawable;

import controller.MazeController;
import model.SolverType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class GUIPanel extends JPanel implements ActionListener {
    private final MazeController mazeController;

    public GUIPanel(MazeController mazeController) {
        this.mazeController = mazeController;
        initGUIPanel();
    }

    private void initGUIPanel() {
        setLayout(new GridBagLayout());

        // Generate Maze Button
        JButton generateMazeButton = new JButton("Generate");
        generateMazeButton.setActionCommand("generate");
        generateMazeButton.addActionListener(mazeController.getMazeGeneratorListener());
        addComponent(generateMazeButton, 0, 0, 1, 1, GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL);

        // Solve Method Radio
        JPanel solveMethodRadio = initSolveMethodRadio();
        addComponent(solveMethodRadio, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Solve Maze Button
        JButton solveMazeButton = new JButton("Solve");
        solveMazeButton.setActionCommand("solve");
        solveMazeButton.addActionListener(mazeController.getMazeSolverListener());
        addComponent(solveMazeButton, 0, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Reset Maze Button
        JButton resetMazeButton = new JButton("Reset");
        resetMazeButton.setActionCommand("reset");
        resetMazeButton.addActionListener(mazeController.getMazeResetListener());
        addComponent(resetMazeButton, 0, 3, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        addComponent(exitButton, 0, 4, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);
    }

    private void addComponent(Component component, int gridx, int gridy, int gridwidth, int gridheight, int anchor, int fill) {
        Insets insets = new Insets(5, 0, 0, 0);
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 1.0,
                anchor, fill, insets, 0, 0);
        this.add(component, gbc);
    }

    private JPanel initSolveMethodRadio() {
        JPanel solveMethodRadioPanel = new JPanel();

        solveMethodRadioPanel.setBorder(BorderFactory.createTitledBorder("Solve Method"));

        Box solveMethodRadioBox = Box.createVerticalBox();
        ButtonGroup solveMethodRadioButtonGroup = new ButtonGroup();

        for (SolverType solverType : SolverType.values()) {
            JRadioButton solverTypeOption = new JRadioButton(solverType.getName());
            solverTypeOption.addActionListener(mazeController.getMazeSolverSelectionRadioListener());

            if (solverType == mazeController.getSolverType()) {
                solverTypeOption.setSelected(true);
            }

            solveMethodRadioBox.add(solverTypeOption);
            solveMethodRadioButtonGroup.add(solverTypeOption);
        }

        solveMethodRadioPanel.add(solveMethodRadioBox);

        Dimension guiDimension = new Dimension(125, 100);
        solveMethodRadioPanel.setMinimumSize(guiDimension);
        solveMethodRadioPanel.setPreferredSize(guiDimension);

        return solveMethodRadioPanel;
    }

    @Override
    public void actionPerformed(ActionEvent mazeGuiButtonClick) {
        String buttonIntention = mazeGuiButtonClick.getActionCommand();

        if (buttonIntention == "exit") {
            System.exit(0);
        }
    }
}
