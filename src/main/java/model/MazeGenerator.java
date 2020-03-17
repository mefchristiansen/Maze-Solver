package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class MazeGenerator {
	protected Maze maze;
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);;

	public abstract void generateMaze();

	public abstract void addPropertyChangeListener(PropertyChangeListener listener);

	public abstract void removePropertyChangeListener(PropertyChangeListener listener);

	public abstract void updateMaze();
}