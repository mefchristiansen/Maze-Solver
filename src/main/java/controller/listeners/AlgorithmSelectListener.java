package controller.listeners;

import controller.MazeController;
import view.MazeView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgorithmSelectListener extends MouseAdapter {
    private MazeController controller;
    private MazeView view;

    public AlgorithmSelectListener(MazeController controller, MazeView view) {
        super();
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("MOUSE CLICK");

        Button pressedButton = (Button) e.getSource();
        String buttonLabel = pressedButton.getLabel();

        System.out.println(buttonLabel);

        switch (buttonLabel) {
            case "A Star (A*)":
                controller.setMazeSolver("AStar");
                System.out.println("A*");
                break;
            case "Depth First Search (DFS)":
                controller.setMazeSolver("DFS");
                System.out.println("DFS");
                break;
            case "Breadth First Search (BFS)":
                controller.setMazeSolver("BFS");
                System.out.println("BFS");
                break;
        }
        view.selectionFrame.selectorFrame.dispose();
    }
}
