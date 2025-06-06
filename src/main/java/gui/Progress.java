package gui;

import controller.Controller;

import javax.swing.*;

public class Progress extends JFrame {
    private JPanel mainPanel;
    private JTextArea textArea1;
    private JButton caricaButton;
    private JList list2;
    private JButton salvaButton;
    private Controller controller;

    public Progress( Controller controller) {
        this.setTitle("Progressi");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
    }
}
