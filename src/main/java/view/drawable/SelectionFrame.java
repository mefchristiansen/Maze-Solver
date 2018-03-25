package view.drawable;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SelectionFrame {

    // Make public so controller can listen in on button presses
    public Button aStarButton;
    public Button dfsButton;
    public Button bfsButton;

    public Frame selectorFrame;

    public SelectionFrame() {
        Frame selectorFrame = new Frame("Select an Algoirthm");
        Panel selectionPanel = new Panel();
        this.aStarButton = new Button("A Star");
        this.dfsButton = new Button("Depth First Search");
        this.bfsButton = new Button("Breadth First Search");
        selectionPanel.add(aStarButton);
        selectionPanel.add(dfsButton);
        selectionPanel.add(bfsButton);

        selectorFrame.add(selectionPanel);

        selectorFrame.addWindowListener(new CloseListener());
        selectorFrame.setSize(200,125);
        selectorFrame.setLocation(100,100);
        selectorFrame.setVisible(true);

        this.selectorFrame = selectorFrame;
    }

    private static class CloseListener extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            e.getWindow().setVisible(false);
            System.exit(0);
        }
    }
}
