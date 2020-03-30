package controller;

import java.awt.event.ActionListener;

public abstract class MazeActionListener implements ActionListener {
    protected MazeController mazeController;

    public MazeActionListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}