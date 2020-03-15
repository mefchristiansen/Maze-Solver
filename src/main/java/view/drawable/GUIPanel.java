package view.drawable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GUIPanel extends JPanel {
    public GUIPanel() {
        initGUIPanel();
    }

    private void initGUIPanel() {
        setLayout(new GridLayout(0,1));

//        GridBagConstraints gbc = new GridBagConstraints();

        // Generate Maze Button
        JButton generateMazeButton = new JButton("Generate");
        add(generateMazeButton);

        // Solve Method Radio
        JPanel solveMethodRadio = initSolveMethodRadio();
        add(solveMethodRadio);

        // Solve Maze Button
        JButton solveMazeButton = new JButton("Solve");
        add(solveMazeButton);

        // Reset Maze Button
        JButton resetMazeButton = new JButton("Reset");
        add(resetMazeButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        add(exitButton);
    }

    private JPanel initSolveMethodRadio() {
        JPanel p = new JPanel();

        TitledBorder solveMethodTitledBorder = new TitledBorder("Solve Method");

        p.setBorder(solveMethodTitledBorder);

        JRadioButton option1 = new JRadioButton("BFS");
        JRadioButton option2 = new JRadioButton("DFS");
        JRadioButton option3 = new JRadioButton("A*");

        Box box1 = Box.createVerticalBox();
        box1.add(option1);
        box1.add(option2);
        box1.add(option3);

        p.add(box1);

        return p;
    }
}
