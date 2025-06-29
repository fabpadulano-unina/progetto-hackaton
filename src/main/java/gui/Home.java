package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JPanel mainPanel;
    private JButton organizzaHackatonButton;
    private JTable hackatonsTable;
    private JTabbedPane tabbedPane1;
    private Controller controller;



    public Home(Controller controller) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        controller.setHomeFrame(this);

        this.organizzaHackatonButton.setVisible(controller.getUtente().isOrganizzatore());
        setHackatonsTable();
        handleClicks();

        tabbedPane1.addTab("Team", new TeamTab(controller));
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


    public void setHackatonsTable() {
        String[] columnNames = {"Descrizione", "Data Inizio", "Data Fine", "Dettaglio"};
        Object[][] data = controller.getHackatons();
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        hackatonsTable.setModel(model);
        hackatonsTable.setRowHeight(30);
        for (int i = 0; i < 3; i++) {
            hackatonsTable.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }

        hackatonsTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        hackatonsTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), controller));

    }
}
