package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JPanel mainPanel;
    private JButton organizzaHackatonButton;
    private JTable table;
    private JTabbedPane tabbedPane1;
    private JPanel homeTab;
    private JPanel teamTab;
    private JComboBox<String> teamSelect;
    private JList<String> progressiList;
    private JButton caricaNuovoProgressoButton;
    private JButton uniscitiButton;
    private JButton addTeamBtn;
    private Controller controller;



    public Home(Controller controller) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        controller.setHomeFrame(this);

        this.organizzaHackatonButton.setVisible(controller.utente.isOrganizzatore());
        setTable();
        handleClicks();
    }



    private void handleClicks() {
        handleAddHackaton();
        handleAddTeam();
        handleProgress();

    }

    private void handleAddHackaton() {
        organizzaHackatonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openHackatonForm();
            }
        });
    }

    private void handleAddTeam() {
        addTeamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openTeamForm();
            }
        });
    }

    private void handleProgress() {
        caricaNuovoProgressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openProgress();
            }
        });
    }


    private void setTable() {


        String[] columnNames = {"Descrizione", "Data Inizio", "Data Fine", "Dettaglio"};
        Object[][] data = controller.getHackatons();
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };
        table.setModel(model);
        table.setRowHeight(30);
        for (int i = 0; i < 3; i++) {
            table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }

        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), controller));

    }
}
