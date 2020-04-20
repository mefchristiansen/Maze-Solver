package view.drawable;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

public class StatsPanel extends JPanel {
	private final JLabel numChecks;
	private final JLabel solutionPathLength;

	public StatsPanel(int checks, int solutionLength) {
		setLayout(new GridBagLayout());

		numChecks = new JLabel("Checks: " + checks);
		solutionPathLength = new JLabel("Path Length: " + solutionLength);

		Insets insets = new Insets(0, 2, 0, 2);

		addComponent(this, numChecks, 0, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
		addComponent(this, solutionPathLength, 1, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
	}

	public void setNumChecks(int checks) {
		numChecks.setText("Checks: " + checks);
	}

	public void setSolutionPathLength(int solutionLength) {
		solutionPathLength.setText("Path Length: " + solutionLength);
	}
}
