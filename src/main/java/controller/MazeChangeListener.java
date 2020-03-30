package controller;

import javax.swing.event.ChangeListener;

public abstract class MazeChangeListener implements ChangeListener {
    protected final MazeController mazeController;

    protected MazeChangeListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}
