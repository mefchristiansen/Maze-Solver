package view.drawable;

import controller.listeners.MazeSolverSelectionRadioListener;
import model.SolverType;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel of maze solver options radio
 */
class SolveMethodRadioPanel extends JPanel {
	public SolveMethodRadioPanel(MazeSolverSelectionRadioListener solverSelectionRadioListener,
								 SolverType mazeSolverType) {
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createTitledBorder("Solve Method"));

		Box solveMethodRadioBox = Box.createVerticalBox();
		ButtonGroup solveMethodRadioButtonGroup = new ButtonGroup();

        /*
        	Iterates through all the solver types and creates a corresponding radio option for the user to pick
         */
		for (SolverType solverType : SolverType.values()) {
			JRadioButton solverTypeOption = new JRadioButton(solverType.getName());
			solverTypeOption.addActionListener(solverSelectionRadioListener);

			if (solverType == mazeSolverType) { // Defaults to the initial maze SolverType set by the controller
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








