package controller.listeners;

import controller.MazeController;
import model.GeneratorType;
import model.SolverType;
import view.MazeSolverView;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AlgorithmSelectListener extends MouseAdapter {
    private MazeController controller;
    private MazeSolverView view;
    private boolean enabled;

    public AlgorithmSelectListener(MazeController controller, MazeSolverView view) {
        super();
        this.controller = controller;
        this.view = view;
        enabled = false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        synchronized (view.selectionFrame) {
            if (enabled) {
                Button pressedButton = (Button) e.getSource();
                String buttonLabel = pressedButton.getLabel();

                switch (buttonLabel) {
                    case "A Star (A*)":
                        controller.setMazeSolver(SolverType.AStar);
                        break;
                    case "Depth First Search (DFS)":
                        controller.setMazeSolver(SolverType.DFS);
                        break;
                    case "Breadth First Search (BFS)":
                        controller.setMazeSolver(SolverType.BFS);
                        break;
                }
                view.selectionFrame.selectorFrame.dispose();
            }
            view.selectionFrame.notify();
        }
    }

    public void enable() {
        this.enabled = true;
    }
}
