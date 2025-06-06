package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class HackatonDetails extends JFrame {
    private JButton registratiBtn;
    private JTabbedPane tabbedPane1;
    private JPanel overviewTab;
    private JPanel giudiciTab;
    private JPanel orgazinerToolTab;
    private JPanel mainPanel;
    private JTable table;
    private Controller controller;

    public HackatonDetails(Controller controller) {
        this.setTitle("Dettaglio Hackaton");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;


        aggiornaVisibilitaPulsanti();

        String[] columnNames = {"Nome", "Cognome", "Email"};
        Object[][] data = controller.getGiudici();
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table.setModel(model);
        table.setRowHeight(30);
        for (int i = 0; i < 2; i++) {
            table.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }



    }
    private void aggiornaVisibilitaPulsanti() {
        String ruolo = null;

        boolean isPartecipante = "PARTECIPANTE".equals(ruolo);
        boolean isGiudice = "GIUDICE".equals(ruolo);
        boolean isOrganizzatore = "ORGANIZZATORE".equals(ruolo);

    }
}
