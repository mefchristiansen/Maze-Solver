package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeGeneratorListener extends MazeActionListener {
    public Thread generatorThread;

    public MazeGeneratorListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        generatorThread = new Thread(() -> mazeController.initMaze());
        generatorThread.start();
    }
}