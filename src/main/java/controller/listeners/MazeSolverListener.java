package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeSolverListener extends MazeActionListener {
    private Thread solverThread;

    public MazeSolverListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        solverThread = new Thread(() -> mazeController.solveMaze());
        solverThread.start();
    }
}