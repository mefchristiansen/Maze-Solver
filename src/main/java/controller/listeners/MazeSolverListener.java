package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.MazeState;

import java.awt.event.ActionEvent;

public class MazeSolverListener extends MazeActionListener {
    public MazeSolverListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (canSolve()) {
            mazeController.solve();
        }
    }

    private boolean canSolve() {
        MazeState mazeState = mazeController.getState();
        return mazeState == MazeState.GENERATED;
    }
}