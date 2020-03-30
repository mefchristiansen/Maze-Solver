package view.drawable;

import controller.MazeController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static view.drawable.DrawableHelper.addComponent;

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
        JPanel customMazeSizeInputs = new MazeSizeInputs(mazeController.getMazeCustomNumRowsListener(), mazeController.getMazeCustomNumColsListener());
        addComponent(this, customMazeSizeInputs, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Generate Maze Button
        initButton(new JButton("Generate"),"generate", 1, mazeController.getMazeGeneratorListener(), insets);

        // Solve Method Radio
        JPanel solveMethodRadio = new SolveMethodRadioPanel(mazeController.getMazeSolverSelectionRadioListener(),
                mazeController.getSolverType());
        addComponent(this, solveMethodRadio, 0, 2, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Solve Maze Button
        initButton(new JButton("Solve"),"solve", 3, mazeController.getMazeSolverListener(), insets);

        // Speed Slider
        JPanel mazeAnimationSpeedSliderPanel = new AnimationSliderPanel(mazeController.getMazeAnimationSpeedSliderListener());
        addComponent(this, mazeAnimationSpeedSliderPanel, 0, 4, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets);

        // Reset Maze Button
        initButton(new JButton("Reset"),"reset", 5, mazeController.getMazeResetListener(), insets);

        // Exit Button
        initButton(new JButton("Exit"),"exit", 6, this, insets);
    }

    private void initButton(JButton button,
                            String buttonAction,
                            int gridy,
                            ActionListener actionListener,
                            Insets insets) {
        button.setActionCommand(buttonAction);
        button.addActionListener(actionListener);
        addComponent(this, button, 0, gridy, 1, 1,
                GridBagConstraints.CENTER,GridBagConstraints.HORIZONTAL, insets);
    }

    @Override
    public void actionPerformed(ActionEvent mazeGuiButtonClick) {
        String buttonIntention = mazeGuiButtonClick.getActionCommand();

        if (buttonIntention.equals("exit")) {
            System.exit(0);
        }
    }
}
