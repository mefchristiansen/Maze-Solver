package controller.listeners;

import view.MazeSolverView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeWaypointClickListener extends MouseAdapter {
	private MazeSolverView view;
	private boolean enabled;

	public MazeWaypointClickListener(MazeSolverView view) {
		super();
		this.view = view;
		enabled = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		synchronized (view) {
			if (enabled) {
				this.view.mazePanel.setPoint(e.getX(), e.getY());
			}
		    view.notify();
		}
	}

	public void enable() {
		this.enabled = true;
	}

}