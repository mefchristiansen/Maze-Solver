package controller.listeners;

import controller.MazeController;
import model.MazeState;
import view.drawable.MazePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeWaypointClickListener extends MouseAdapter {
	private MazePanel mazePanel;
	private MazeController mazeController;

	public MazeWaypointClickListener(MazePanel mazePanel, MazeController mazeController) {
		super();
		this.mazePanel = mazePanel;
		this.mazeController = mazeController;
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		if (canSet()) {
			mazePanel.setWaypoint(mouseEvent.getX(), mouseEvent.getY());
		}

		/*
			synchronized (view) {
				if (canSet()) {
					this.view.mazePanel.setWaypoint(e.getX(), e.getY());
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