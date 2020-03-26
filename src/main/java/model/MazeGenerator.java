package model;

import controller.MazeController;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public abstract class MazeGenerator {
    private List<ChangeListener> listenerList = new ArrayList<>();
	protected Maze maze;
    protected MazeController mazeController;

	public MazeGenerator(Maze maze, MazeController mazeController) {
		this.maze = maze;
		this.mazeController = mazeController;
	}

    public synchronized void addChangeListener(ChangeListener listener) {
        if(!listenerList.contains(listener)) {
            listenerList.add(listener);
        }
    }

    public synchronized void removeChangeListener(ChangeListener listener) {
        listenerList.remove(listener);
    }

    protected void fireStateChanged() {
        if (listenerList != null && listenerList.size() > 0) {
            ChangeEvent event = new ChangeEvent(this);
            for (ChangeListener listener : listenerList) {
                listener.stateChanged(event);
            }
        }
    }

	public abstract boolean generateMaze();
}