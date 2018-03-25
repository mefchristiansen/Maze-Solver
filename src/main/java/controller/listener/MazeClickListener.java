package controller.listener;

import view.MazeView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class MazeClickListener extends MouseAdapter {
	private MazeView view;
	private boolean enabled;

	public MazeClickListener(MazeView view) {
		super();
		this.view = view;
		enabled = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		synchronized (view) {
			if (enabled) {
				this.view.mazeDisplay.setPoint(e.getX(), e.getY());
			}
		    view.notify();
		}
	}

	public void enable() {
		this.enabled = true;
	}

}