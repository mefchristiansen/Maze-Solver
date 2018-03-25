package controller.listener;

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
        Button pressedButton = (Button) e.getSource();
        String buttonLabel = pressedButton.getLabel();
        switch (buttonLabel) {
            case "A Star":                  controller.searchAlgorithm = "A*";
                                            System.out.println("A*");
                                            break;
            case "Depth First Search":      controller.searchAlgorithm = "DFS";
                                            System.out.println("DFS");
                                            break;
            case "Breadth First Search":    controller.searchAlgorithm = "BFS";
                                            System.out.println("BFS");
                                            break;
        }
        view.selectionFrame.selectorFrame.dispose();
    }
}
