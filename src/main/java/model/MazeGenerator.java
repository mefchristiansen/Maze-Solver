package model;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public abstract class MazeGenerator {
    private List<ChangeListener> listenerList = new ArrayList<>();
	protected Maze maze;

	public MazeGenerator(Maze maze) {
		this.maze = maze;
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

//    public boolean isInterrupted() {
//        // Check for interrupt
//        try {
//            Thread.sleep(15L);
//        } catch (InterruptedException e) {
//            return true;
//        }
//        return false;
//    }

	public abstract void generateMaze();
}