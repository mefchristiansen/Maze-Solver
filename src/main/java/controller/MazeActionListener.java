package controller;

import java.awt.event.ActionListener;

public abstract class MazeActionListener implements ActionListener {
    protected final MazeController mazeController;

    protected MazeActionListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}