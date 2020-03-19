package controller;

import model.Maze;

import java.awt.event.ActionListener;

public abstract class MazeActionListener implements ActionListener {
    protected MazeController mazeController;

    public MazeActionListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }
}