package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeSolverListener extends MazeActionListener {
    public MazeSolverListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> mazeController.solveMaze()).start();
    }
}