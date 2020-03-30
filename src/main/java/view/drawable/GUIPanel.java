package view.drawable;

import controller.MazeChangeListener;
import controller.MazeController;
import model.MazeConstants;
import model.SolverType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIPanel extends JPanel implements ActionListener {
    private final MazeController mazeController;

    public GUIPanel(MazeController mazeController) {
        this.mazeController = mazeController;
        initGUIPanel();
    }

    private void initGUIPanel() {
        setLayout(new GridBagLayout());

        Insets insets = new Insets(5, 0, 0, 0);

        // Maze Size Inputs
        JPanel customMazeSizeInputs = customMazeSizeInputs();
        addComponent(this, customMazeSizeInputs, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Generate Maze Button
        JButton generateMazeButton = new JButton("Generate");
        generateMazeButton.setActionCommand("generate");
        generateMazeButton.addActionListener(mazeController.getMazeGeneratorListener());
        addComponent(this, generateMazeButton, 0, 1, 1, 1,
                GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL, insets);

        // Solve Method Radio
        JPanel solveMethodRadio = solveMethodRadio();
        addComponent(this, solveMethodRadio, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Solve Maze Button
        JButton solveMazeButton = new JButton("Solve");
        solveMazeButton.setActionCommand("solve");
        solveMazeButton.addActionListener(mazeController.getMazeSolverListener());
        addComponent(this, solveMazeButton, 0, 3, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Speed Slider
        JPanel mazeAnimationSpeedSliderPanel = mazeAnimationSpeedSliderPanel();
        addComponent(this, mazeAnimationSpeedSliderPanel, 0, 4, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Reset Maze Button
        JButton resetMazeButton = new JButton("Reset");
        resetMazeButton.setActionCommand("reset");
        resetMazeButton.addActionListener(mazeController.getMazeResetListener());
        addComponent(this, resetMazeButton, 0, 5, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");
        exitButton.addActionListener(this);
        addComponent(this, exitButton, 0, 6, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);
    }

    private JPanel solveMethodRadio() {
        JPanel solveMethodRadioPanel = new JPanel(new GridBagLayout());

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

        Insets insets = new Insets(2, 2, 2, 2);
        addComponent(solveMethodRadioPanel, solveMethodRadioBox, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        Dimension guiDimension = new Dimension(125, 100);
        solveMethodRadioPanel.setMinimumSize(guiDimension);
        solveMethodRadioPanel.setPreferredSize(guiDimension);

        return solveMethodRadioPanel;
    }

    private JPanel customMazeSizeInputs() {
        JPanel customMazeSizeInputsPanel = new JPanel(new GridBagLayout());

        JPanel numRowsPanel = new JPanel(new GridLayout(1,0));
        String numRowsSpinnerLabel = String.format("Num Rows (%d-%d):", MazeConstants.MIN_NUM_ROWS, MazeConstants.MAX_NUM_ROWS);
        SpinnerModel numRowsSpinner = new SpinnerNumberModel(
                MazeConstants.DEFAULT_NUM_ROWS,
                MazeConstants.MIN_NUM_ROWS,
                MazeConstants.MAX_NUM_ROWS,
                1
        );

        JPanel numColsPanel = new JPanel(new GridLayout(1,0));
        String numColsSpinnerLabel = String.format("Num Cols (%d-%d):", MazeConstants.MIN_NUM_COLS, MazeConstants.MAX_NUM_COLS);
        SpinnerModel numColsSpinner = new SpinnerNumberModel(
                MazeConstants.DEFAULT_NUM_COLS,
                MazeConstants.MIN_NUM_COLS,
                MazeConstants.MAX_NUM_COLS,
                1
        );

        addLabeledSpinner(numRowsPanel, numRowsSpinner, numRowsSpinnerLabel, mazeController.getMazeCustomNumRowsListener());
        addLabeledSpinner(numColsPanel, numColsSpinner, numColsSpinnerLabel, mazeController.getMazeCustomNumColsListener());

        Insets insets = new Insets(0, 0, 0, 0);
        addComponent(customMazeSizeInputsPanel, numRowsPanel, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        addComponent(customMazeSizeInputsPanel, numColsPanel, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        return customMazeSizeInputsPanel;
    }

    private JPanel mazeAnimationSpeedSliderPanel() {
        JPanel mazeAnimationSpeedSliderPanel = new JPanel(new GridBagLayout());

        JSlider mazeAnimationSpeedSlider = new JSlider(JSlider.HORIZONTAL, MazeDrawableConstants.MINIMUM_ANIMATION_SLEEP,
                MazeDrawableConstants.MAXIMUM_ANIMATION_SLEEP, MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP);
        mazeAnimationSpeedSlider.setInverted(true);
        mazeAnimationSpeedSlider.addChangeListener(mazeController.getMazeAnimationSpeedSliderListener());

        JLabel mazeAnimationSpeedSliderLabel = new JLabel("Animation Time");

        Insets insets = new Insets(0, 0, 0, 0);

        addComponent(mazeAnimationSpeedSliderPanel, mazeAnimationSpeedSliderLabel, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        mazeAnimationSpeedSliderLabel.setLabelFor(mazeAnimationSpeedSlider);
        addComponent(mazeAnimationSpeedSliderPanel, mazeAnimationSpeedSlider, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        return mazeAnimationSpeedSliderPanel;
    }

    private static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth,
                                     int gridheight, int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                1.0, 1.0, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }

    private static void addLabeledSpinner(Container container, SpinnerModel model, String label, MazeChangeListener mazeChangeListener) {
        JLabel spinnerLabel = new JLabel(label);
        container.add(spinnerLabel);

        JSpinner spinner = new JSpinner(model);
        spinner.addChangeListener(mazeChangeListener);
        spinnerLabel.setLabelFor(spinner);
        container.add(spinner);
    }

    @Override
    public void actionPerformed(ActionEvent mazeGuiButtonClick) {
        String buttonIntention = mazeGuiButtonClick.getActionCommand();

        if (buttonIntention.equals("exit")) {
            System.exit(0);
        }
    }
}
