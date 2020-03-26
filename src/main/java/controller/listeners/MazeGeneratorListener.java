package controller.listeners;

import model.Maze;
import model.MazeState;
import controller.MazeController;
import controller.MazeActionListener;

import java.awt.event.ActionEvent;

public class MazeGeneratorListener extends MazeActionListener {
    private Thread generatorThread;

    public MazeGeneratorListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!canGenerate() || generatorThread != null) {
            return;
        }

        mazeController.resetMaze();
        mazeController.initMaze();
        generatorThread = new Thread(() -> mazeController.generateMaze());
        generatorThread.start();
    }

    public void resetGenerator() {
        if (generatorThread == null) {
            return;
        }

        try {
            generatorThread.join(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        generatorThread = null;
    }

    private boolean canGenerate() {
        MazeState mazeState = mazeController.getState();
        return mazeState == MazeState.INIT || mazeState == MazeState.GENERATED;
    }
}