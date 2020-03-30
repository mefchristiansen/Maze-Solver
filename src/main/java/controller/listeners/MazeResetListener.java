package controller.listeners;

import controller.MazeActionListener;
import controller.MazeController;

import java.awt.event.ActionEvent;

public class MazeResetListener extends MazeActionListener {
    public MazeResetListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mazeController.resetMaze();
    }
}