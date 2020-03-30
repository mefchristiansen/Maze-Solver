package view.drawable;

import controller.listeners.MazeSolverSelectionRadioListener;
import model.SolverType;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

class SolveMethodRadioPanel extends JPanel {
    public SolveMethodRadioPanel(MazeSolverSelectionRadioListener solverSelectionRadioListener, SolverType mazeSolverType) {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Solve Method"));

        Box solveMethodRadioBox = Box.createVerticalBox();
        ButtonGroup solveMethodRadioButtonGroup = new ButtonGroup();

        for (SolverType solverType : SolverType.values()) {
            JRadioButton solverTypeOption = new JRadioButton(solverType.getName());
            solverTypeOption.addActionListener(solverSelectionRadioListener);

            if (solverType == mazeSolverType) {
                solverTypeOption.setSelected(true);
            }

            solveMethodRadioBox.add(solverTypeOption);
            solveMethodRadioButtonGroup.add(solverTypeOption);
        }

        Insets insets = new Insets(2, 2, 2, 2);
        addComponent(this, solveMethodRadioBox, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

        Dimension guiDimension = new Dimension(125, 100);
        setMinimumSize(guiDimension);
        setPreferredSize(guiDimension);
    }
}








