package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeResetListener extends MazeActionListener {
    public MazeResetListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mazeController.resetMaze();
//        new Thread(() -> mazeController.resetMaze()).start();
    }
}