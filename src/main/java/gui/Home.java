package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra principale dell'applicazione hackathon.
 * Mostra la lista degli hackathon e gestisce la navigazione.
 */
public class Home extends JFrame {
    private JPanel mainPanel;
    private JButton organizzaHackatonButton;
    private JTable hackatonsTable;
    private JTabbedPane tabbedPane1;
    private Controller controller;


    /**
     * Crea e inizializza la finestra principale.
     * Configura l'interfaccia in base al tipo di utente.
     *
     * @param controller il controller dell'applicazione
     */
    public Home(Controller controller) {
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        controller.setHomeFrame(this);

        this.organizzaHackatonButton.setVisible(controller.isOrganizzatore());
        setHackatonsTable();
        handleClicks();

        if(controller.isPartecipante()) tabbedPane1.addTab("Team", new TeamTab(controller));
    }



    private void handleClicks() {
        handleAddHackaton();
    }

    /**
     * Gestisce il click del pulsante organizza hackathon.
     * Apre il form per creare un nuovo hackathon.
     */
    private void handleAddHackaton() {
        organizzaHackatonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openHackatonForm();
            }
        });
    }


    /**
     * Inizializza e configura la tabella degli hackathon.
     * Imposta le colonne e i renderer per i pulsanti.
     */
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
