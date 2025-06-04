package gui;

import controller.Controller;

import javax.swing.*;

public class HackatonDetails extends JFrame {
    private JButton registratiBtn;
    private JTabbedPane tabbedPane1;
    private JPanel overviewTab;
    private JPanel giudiciTab;
    private JTable table1;
    private JPanel orgazinerToolTab;
    private JPanel mainPanel;
    private Controller controller;

    public HackatonDetails(Controller controller) {
        this.setTitle("Dettaglio Hackaton");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;


        aggiornaVisibilitaPulsanti();

    }
    private void aggiornaVisibilitaPulsanti() {
        String ruolo = null;

        boolean isPartecipante = "PARTECIPANTE".equals(ruolo);
        boolean isGiudice = "GIUDICE".equals(ruolo);
        boolean isOrganizzatore = "ORGANIZZATORE".equals(ruolo);

    }
}
