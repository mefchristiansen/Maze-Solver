package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeGeneratorListener extends MazeActionListener {
    public MazeGeneratorListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> mazeController.initMaze()).start();
    }
}