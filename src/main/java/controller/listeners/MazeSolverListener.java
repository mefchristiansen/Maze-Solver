package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;
import model.MazeState;

import java.awt.event.ActionEvent;

public class MazeSolverListener extends MazeActionListener {
    private Thread solverThread;

    public MazeSolverListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (!canSolve() || solverThread != null) {
            return;
        }

        mazeController.initSolve();
        solverThread = new Thread(mazeController::solveMaze);
        solverThread.start();
    }

    public void resetSolver() {
        if (solverThread == null) {
            return;
        }

        try {
            solverThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solverThread = null;
    }

    private boolean canSolve() {
        MazeState mazeState = mazeController.getState();
        return mazeState == MazeState.GENERATED;
    }
}