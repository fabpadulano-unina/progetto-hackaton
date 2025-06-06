package gui;

import controller.Controller;

import javax.swing.*;

public class Progress extends JFrame {
    private JPanel mainPanel;
    private JList list1;
    private JTextArea textArea1;
    private JButton caricaButton;
    private JList list2;
    private Controller controller;

    public Progress( Controller controller) {
        this.setTitle("Progressi");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
    }
}
