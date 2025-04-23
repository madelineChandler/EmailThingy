package tabular;
/*
 * Author: Madeline Chandler
 * Date: 4/22/2025
 * Purpose: Handles the UI for the project
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MainFrame extends JFrame {
    // general properties (font, size, etc.)
    final private Font mainFont = new Font("Arial", Font.BOLD, 18);
    final private int width = 500;
    final private int height = 600;
    final private Color bckgd = new Color(200, 200, 200);
    final private String title = "Test"; 

    // input fields
    JTextField inputTest;

    public void initialize() {
    /* ========= Test Panel ========= */
        // text
        JLabel test = new JLabel(title);
        test.setFont(mainFont);

        // input
        inputTest = new JTextField();
        inputTest.setFont(mainFont);

        // create form panel
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new GridLayout(2, 1, 5, 5));
        testPanel.add(test);
        testPanel.add(inputTest);

    /*========= Buttons Panel =========*/
    JButton btnRUN =  new JButton("Run");
    btnRUN.setFont(mainFont);
    btnRUN.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            test.setText("WAHOO! Button works :)  input = " + inputTest.getText());
        }

    });

    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(1, 1, 5, 5));
    buttonsPanel.add(btnRUN);

    /*========= Main Window =========*/
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(bckgd);
        mainPanel.add(testPanel, BorderLayout.NORTH); // creates test panel north of mainframe
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        add(mainPanel);

        setTitle(title);
        setSize(width, height);
        setMinimumSize(new Dimension(width/2, height/2));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
