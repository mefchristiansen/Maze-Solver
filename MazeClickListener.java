import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeClickListener extends MouseAdapter {
	private MazeDisplay panel;
	boolean enabled;

	public MazeClickListener(MazeDisplay panel) {
		super();
		this.panel = panel;
		enabled = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (enabled) {
			panel.setPoint(e.getX(), e.getY());

			if (panel.disableMouseListener()) {
				this.enabled = false;
			}
		}
	}

	public void enable() {
		this.enabled = true;
	}

}