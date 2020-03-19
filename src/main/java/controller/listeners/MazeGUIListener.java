package controller.listeners;

import controller.MazeController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MazeGUIListener implements ActionListener {
    protected MazeController mazeController;

    public MazeGUIListener(MazeController mazeController) {
        this.mazeController = mazeController;
    }

    @Override
    public void actionPerformed(ActionEvent mazeGUIButtonClick) {
        String buttonIntention = mazeGUIButtonClick.getActionCommand();

        switch(buttonIntention) {
            case "generate":
                new Thread(() -> mazeController.initMaze()).start();
                break;
            case "solve":
                new Thread(() -> mazeController.solveMaze()).start();
                break;
            case "reset":
                mazeController.resetMaze();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                break;
        }
    }
}