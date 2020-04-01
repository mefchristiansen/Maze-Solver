package controller.listeners;

import controller.MazeController;
import controller.MazeActionListener;
import model.MazeState;

import java.awt.event.ActionEvent;

public class MazeGeneratorListener extends MazeActionListener {
    public MazeGeneratorListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canGenerate()) {
            mazeController.generate();
        }
    }

    private boolean canGenerate() {
        MazeState mazeState = mazeController.getState();
        return mazeState == MazeState.INIT || mazeState == MazeState.GENERATED;
    }
}