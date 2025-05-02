package gui;
/*
 * Author: Madeline Chandler
 * Date: 4/22/2025
 * Purpose: Handles the UI for the project
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tabular.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    // general properties (font, size, etc.)
    final Font mainFont = new Font("Arial", Font.BOLD, 18);
    final int width = 500;
    final int height = 600;
    final Color bckgd = new Color(200, 200, 200);
    final String title = "Email Classification";

    // input fields
    JTextField emailInput;

    public void initialize(Model model) {
    /* ========= Test Panel ========= */
        // text
        JLabel accuracyLabel = new JLabel("Enter Email to Classify | " + getAccuracy(model));
        accuracyLabel.setFont(mainFont);
        JLabel output = new JLabel();
        output.setFont(mainFont);

        // input
        emailInput = new JTextField();
        emailInput.setFont(mainFont);

        // create form panel
        JPanel testPanel = new JPanel();
        testPanel.setLayout(new GridLayout(3, 1, 5, 5));
        testPanel.add(accuracyLabel);
        testPanel.add(emailInput);
        testPanel.add(output);

    /*========= Buttons Panel =========*/
    JPanel buttonsPanel = new JPanel();

    JButton btnPREDICT =  new JButton("Predict");
    btnPREDICT.setFont(mainFont);
    btnPREDICT.addActionListener(new ActionListener() {
        // changes text when Predict button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            int value = model.predict(emailInput.getText()); // gets prediction from model
            if (value == 1) {
                output.setText("Spam");
            } else if (value == 0) {
                output.setText("Not Spam");
            }
        }
    });

    /* JButton btnRUN =  new JButton("Run");
    btnRUN.setFont(mainFont);
    btnRUN.addActionListener(new ActionListener() {
        // changes text when RUN button is pressed
        @Override
        public void actionPerformed(ActionEvent e) {
            accuracyLabel.setText(getAccuracy(model));
            buttonsPanel.add(btnPREDICT); // adds Predict button to panel
        }
    }); */

    buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
    buttonsPanel.add(btnPREDICT);

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

    public String getAccuracy(Model model) {
        double prediction = model.predict();
        System.out.println(prediction);
        return "Accuracy: " + String.format("%.2f", (prediction*100)) + "%";
    }
}
