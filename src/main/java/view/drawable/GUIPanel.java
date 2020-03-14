package view.drawable;

import javax.swing.*;
import java.awt.*;

public class GUIPanel extends JPanel {
    public GUIPanel() {
        setLayout(new GridLayout());

        JButton a = new JButton("Tell Me");

        add(a);

        setVisible(true);
    }
}
