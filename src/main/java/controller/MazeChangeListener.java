package controller;

import javax.swing.event.ChangeListener;

public abstract class MazeChangeListener implements ChangeListener {
    protected MazeController mazeController;

    public MazeChangeListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}
