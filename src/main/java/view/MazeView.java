package view;

import model.Maze;
import model.MazeGenerator;
import model.MazeSolver;
import view.drawable.MazeDisplay;
import view.drawable.SelectionFrame;

import java.util.Observable;

public class MazeView implements java.util.Observer {

    public MazeDisplay mazeDisplay;
    public SelectionFrame selectionFrame;

    public MazeView(Maze maze, int scale, int margin, long generationSleep, long solveSleep, long solutionSleep) {
    	super();
        this.mazeDisplay = new MazeDisplay(maze, scale, margin, "generate", generationSleep, solveSleep, solutionSleep);
    }

    public void setDisplayState(String displayState) {
        this.mazeDisplay.setDisplayState(displayState);
    }

    public void update(Observable o, Object arg) {
        // Who sent us a notification?
        // if (o.getClass() == MazeGenerator.class) {
            // We can directly call the maze display since we know that it has inherited the same maze object as the model.
            this.mazeDisplay.generationAnimate();
        // }
        // else if (o.getClass() == MazeSolver.class) {
            // if (arg != null) {
                // this.mazeDisplay.solveAnimate();
            // } else {
                // this.mazeDisplay.solutionAnimate();
            // }
        // }

    }
}