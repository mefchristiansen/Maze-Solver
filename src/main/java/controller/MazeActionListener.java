package controller;


import controller.MazeController;

import java.awt.event.ActionListener;

public abstract class MazeActionListener implements ActionListener {
    protected MazeController mazeController;

    public MazeActionListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}