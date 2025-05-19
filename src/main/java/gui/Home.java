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
    private JTable table;
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
        setTable();
        handleClicks();
    }

    private void setTable() {
        String[] columnNames = {"Campo Testuale", "Azione"};
        Object[][] data = {
                {"Testo 1", "Apri"},
                {"Testo 2", "Apri"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        table.setModel(model);

        // Renderer e editor per il JTextField
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));

        // Renderer e editor per il JButton
        table.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor(new JCheckBox()));
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
