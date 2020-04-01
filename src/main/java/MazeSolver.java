import view.MazeView;

import javax.swing.*;

public class MazeSolver {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MazeView::new);
	}
}