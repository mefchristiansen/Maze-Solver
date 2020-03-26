package controller.listeners;

import controller.MazeController;
import model.Maze;
import model.MazeState;
import view.MazeSolverView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeWaypointClickListener extends MouseAdapter {
	private MazeSolverView view;
	private MazeController mazeController;
	private boolean enabled;

	public MazeWaypointClickListener(MazeSolverView view, MazeController mazeController) {
		super();
		this.view = view;
		this.mazeController = mazeController;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (canSet()) {
			this.view.mazePanel.setPoint(e.getX(), e.getY());
		}

		return;

		/*
			synchronized (view) {
				if (canSet()) {
					this.view.mazePanel.setPoint(e.getX(), e.getY());
				}
				view.notify();
			}
		*/
	}

	private boolean canSet() {
		MazeState mazeState = mazeController.getState();
		return mazeState == MazeState.GENERATED;
	}

}