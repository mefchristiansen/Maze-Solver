package model;

import controller.MazeController;

import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class MazeGenerator {
    private final List<ChangeListener> listenerList = new ArrayList<>();
	protected final Maze maze;
    protected final MazeController mazeController;

	protected MazeGenerator(Maze maze, MazeController mazeController) {
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
        if (listenerList.size() > 0) {
            ChangeEvent event = new ChangeEvent(this);
            for (ChangeListener listener : listenerList) {
                listener.stateChanged(event);
            }
        }
    }

	public abstract boolean generateMaze();
}