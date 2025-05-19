package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HackatonDetails extends JFrame{
    private JPanel panel;
    private JButton apriRegistrazioniButton1;
    private JButton partecipaAdUnTeamButton;
    private JButton pubblicaDescrizioneButton;
    private JButton registratiButton;
    private JLabel descrizioneHackaton;
    private JButton creaTeamButton;
    private JLabel sede;
    private JLabel dataInizio;
    private JLabel maxPersoneTeam;
    private JLabel descrizioneProblema;
    private JComboBox ruoloSelect;
    private JLabel dataFine;
    private Controller controller;

    public HackatonDetails(Controller controller) {
        this.setTitle("Dettaglio Hackaton");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;

        ruoloSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aggiornaVisibilitaPulsanti();
            }
        });

        setupGraficaComponenti();

        aggiornaVisibilitaPulsanti();
    }

    private void aggiornaVisibilitaPulsanti() {
        String ruolo = (String) ruoloSelect.getSelectedItem();

        boolean isPartecipante = "PARTECIPANTE".equals(ruolo);
        boolean isGiudice = "GIUDICE".equals(ruolo);
        boolean isOrganizzatore = "ORGANIZZATORE".equals(ruolo);

        registratiButton.setVisible(isPartecipante);
        partecipaAdUnTeamButton.setVisible(isPartecipante);
        pubblicaDescrizioneButton.setVisible(isGiudice);
        apriRegistrazioniButton1.setVisible(isOrganizzatore);
    }


    private void setupGraficaComponenti() {
        // Font moderni
        Font titoloFont = new Font("Segoe UI", Font.BOLD, 18);
        Font testoFont = new Font("Segoe UI", Font.PLAIN, 14);

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel[] etichette = {
                descrizioneHackaton, sede, dataInizio, dataFine,
                maxPersoneTeam, descrizioneProblema
        };

        for (JLabel label : etichette) {
            label.setFont(testoFont);
            label.setForeground(Color.DARK_GRAY);
        }

        descrizioneHackaton.setFont(titoloFont);
        descrizioneHackaton.setForeground(new Color(33, 33, 33)); // più scuro

        ruoloSelect.setFont(testoFont);
    }

}
