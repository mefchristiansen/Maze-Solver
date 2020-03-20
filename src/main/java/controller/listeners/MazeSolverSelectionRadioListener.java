package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.SolverType;

import java.awt.event.ActionEvent;

public class MazeSolverSelectionRadioListener extends MazeActionListener {
    public MazeSolverSelectionRadioListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        SolverType solverTypeChoice = SolverType.fromString(command);

        if (solverTypeChoice == null) {
            return;
        }

        mazeController.setSolverType(solverTypeChoice);
    }
}
