package controller.listeners;

import controller.MazeChangeListener;
import controller.MazeController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

public class MazeAnimationSpeedSliderListener extends MazeChangeListener {

    public MazeAnimationSpeedSliderListener(MazeController mazeController) {
        super(mazeController);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int animationSpeed = source.getValue();
            mazeController.setAnimationSpeed(animationSpeed);
        }
    }
}
