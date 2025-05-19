package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel mainPanel;
    private JButton organizzaHackatonButton;
    private JTable table1;
    private static JFrame frameHome;
    private static Controller controller;

    public static void main(String[] args) {
        frameHome = new JFrame("Home");
        frameHome.setContentPane(new Home().mainPanel);
        frameHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameHome.pack();
        frameHome.setVisible(true);

        controller.openLoginForm();


    }

    public Home() {
        controller = new Controller(frameHome);
        // Add action listeners or other initialization code here
        String[] colonne = {"ID", "Nome", "Cognome"};
        Object[][] dati = {
                {1, "Mario", "Rossi"},
                {2, "Luca", "Bianchi"}
        };
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("1");
        model.addColumn("2");

        handleClicks();


    }

    private void handleClicks() {
        handleAddHackaton();
    }

    private void handleAddHackaton() {
        organizzaHackatonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openHackatonForm();
            }
        });
    }
}
