package view.drawable;

import controller.listeners.MazeAnimationSpeedSliderListener;

import javax.swing.*;
import java.awt.*;

import static view.drawable.DrawableHelper.addComponent;

class AnimationSliderPanel extends JPanel {
    public AnimationSliderPanel(MazeAnimationSpeedSliderListener mazeAnimationSpeedSliderListener) {
        setLayout(new GridBagLayout());

        JSlider mazeAnimationSpeedSlider = new JSlider(JSlider.HORIZONTAL, MazeDrawableConstants.MINIMUM_ANIMATION_SLEEP,
                MazeDrawableConstants.MAXIMUM_ANIMATION_SLEEP, MazeDrawableConstants.DEFAULT_ANIMATION_SLEEP);
        mazeAnimationSpeedSlider.setInverted(true);
        mazeAnimationSpeedSlider.addChangeListener(mazeAnimationSpeedSliderListener);

        JLabel mazeAnimationSpeedSliderLabel = new JLabel("Animation Time");

        Insets insets = new Insets(0, 0, 0, 0);

        addComponent(this, mazeAnimationSpeedSliderLabel, 0, 0, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);
        mazeAnimationSpeedSliderLabel.setLabelFor(mazeAnimationSpeedSlider);
        addComponent(this, mazeAnimationSpeedSlider, 0, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets);

    }
}