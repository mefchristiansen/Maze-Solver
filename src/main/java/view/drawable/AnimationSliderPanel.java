package view.drawable;

import controller.listeners.MazeAnimationSpeedSliderListener;
import model.MazeAnimationConstants;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

/**
 * A JPanel of animation slider
 */
class AnimationSliderPanel extends JPanel {
	public AnimationSliderPanel(MazeAnimationSpeedSliderListener mazeAnimationSpeedSliderListener) {
		setLayout(new GridBagLayout());

		JSlider mazeAnimationSpeedSlider = new JSlider(JSlider.HORIZONTAL, MazeAnimationConstants.MINIMUM_ANIMATION_SLEEP,
				MazeAnimationConstants.MAXIMUM_ANIMATION_SLEEP, MazeAnimationConstants.DEFAULT_ANIMATION_SLEEP);
		mazeAnimationSpeedSlider.setInverted(true);
		mazeAnimationSpeedSlider.addChangeListener(mazeAnimationSpeedSliderListener);

		JLabel mazeAnimationSpeedSliderLabel = new JLabel("Animation Speed");

		Insets insets = new Insets(0, 0, 0, 0);

		addComponent(this, mazeAnimationSpeedSliderLabel, 0, 0, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
		mazeAnimationSpeedSliderLabel.setLabelFor(mazeAnimationSpeedSlider);
		addComponent(this, mazeAnimationSpeedSlider, 0, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

	}
}
