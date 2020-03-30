package view.drawable;

import controller.MazeChangeListener;
import controller.listeners.MazeCustomNumColsListener;
import controller.listeners.MazeCustomNumRowsListener;
import model.MazeConstants;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

class MazeSizeInputs extends JPanel {
    public MazeSizeInputs(MazeCustomNumRowsListener mazeCustomNumRowsListener, MazeCustomNumColsListener mazeCustomNumColsListener) {
        setLayout(new GridBagLayout());

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

        addLabeledSpinner(numRowsPanel, numRowsSpinner, numRowsSpinnerLabel, mazeCustomNumRowsListener);
        addLabeledSpinner(numColsPanel, numColsSpinner, numColsSpinnerLabel, mazeCustomNumColsListener);

        Insets insets = new Insets(0, 0, 0, 0);
        addComponent(this, numRowsPanel, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        addComponent(this, numColsPanel, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
    }

    private static void addLabeledSpinner(Container container, SpinnerModel model, String label, MazeChangeListener mazeChangeListener) {
        JLabel spinnerLabel = new JLabel(label);
        container.add(spinnerLabel);

        JSpinner spinner = new JSpinner(model);
        spinner.addChangeListener(mazeChangeListener);
        spinnerLabel.setLabelFor(spinner);
        container.add(spinner);
    }
}