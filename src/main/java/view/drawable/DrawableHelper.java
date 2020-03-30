package view.drawable;

import java.awt.*;

class DrawableHelper {
    public static void addComponent(Container container, Component component, int gridx, int gridy, int gridwidth,
                                     int gridheight, int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                1.0, 1.0, anchor, fill, insets, 0, 0);
        container.add(component, gbc);
    }
}
