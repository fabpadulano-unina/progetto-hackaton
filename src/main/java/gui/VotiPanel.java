package gui;

import controller.Controller;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Pannello per la gestione dei voti dei giudici agli hackathon.
 * Permette ai giudici di assegnare voti da 0 a 10 ai team partecipanti.
 */
public class VotiPanel extends JPanel {
    public static final String FONT = "Arial";
    private JPanel panel;
    private final Controller controller;
    private final List<Team> teams;
    private final List<JComboBox<Integer>> cbs = new ArrayList<>();

    /**
     * Costruttore del pannello di voto.
     * Inizializza il pannello con i team da valutare e configura
     * l'interfaccia per l'assegnazione dei voti.
     *
     * @param controller il controller principale dell'applicazione
     * @param teams lista dei team da valutare
     */
    public VotiPanel(Controller controller, List<Team> teams) {
        this.controller = controller;
        this.teams = teams;
        setPanel();
    }

    /**
     * Configura e inizializza il layout del pannello di voto.
     * Crea la struttura con titolo, lista dei team con combo box per i voti
     * e pulsante per confermare la votazione.
     */
    private void setPanel() {
        panel = new JPanel();
        getPanel().setLayout(new BorderLayout());

        getTitolo();

        // Pannello centrale con i team
        JPanel teamsPanel = new JPanel();
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));
        teamsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        getPanel().add(teamsPanel, BorderLayout.CENTER);

        for(Team team : teams) {
            aggiungiTeamConVoto(teamsPanel, team.getNome());
        }

        // Pannello bottone
        JPanel buttonPanel = getBottone();
        getPanel().add(buttonPanel, BorderLayout.SOUTH);

        add(getPanel());
    }

    public JPanel getPanel() {
        return panel;
    }

    /**
     * Crea e posiziona il titolo del pannello di voto.
     * Aggiunge un'etichetta "Team Rankings" centrata nella parte superiore.
     */
    private void getTitolo() {
        JLabel titoloLabel = new JLabel("Team Rankings");
        titoloLabel.setFont(new Font(FONT, Font.BOLD, 20));
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getPanel().add(titoloLabel, BorderLayout.NORTH);
    }

    /**
     * Crea il pannello con il pulsante per inviare i voti.
     * Configura il pulsante "Invia voti" con lo stile appropriato
     * e collega l'event handler per l'invio.
     *
     * @return il pannello contenente il pulsante di invio
     */
    private JPanel getBottone() {
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton inviaVotiBtn = new JButton("Invia voti");
        inviaVotiBtn.setPreferredSize(new Dimension(200, 35));
        inviaVotiBtn.setForeground(Color.BLACK);
        inviaVotiBtn.setFont(new Font(FONT, Font.BOLD, 14));
        inviaVotiBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(11, 10, 10), 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)  // padding
        ));
        inviaVotiBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));


        handleInviaVoti(inviaVotiBtn);

        btnPanel.add(inviaVotiBtn);
        return btnPanel;
    }


    /**
     * Aggiunge un team al pannello con la relativa combo box per il voto.
     * Crea una riga con il nome del team a sinistra e il selettore del voto a destra.
     *
     * @param container il pannello contenitore dove aggiungere il team
     * @param teamName il nome del team da visualizzare
     */
    private void aggiungiTeamConVoto(JPanel container, String teamName) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        teamPanel.setBackground(Color.WHITE);
        teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Nome team
        JLabel nameLabel = new JLabel(teamName);
        nameLabel.setFont(new Font(FONT, Font.BOLD, 16));

        // Pannello per voto con combobox
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.WHITE);

        JLabel scoreLabel = new JLabel("Voto: ");
        scoreLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(100, 100, 100));

        // ComboBox per il voto
        Integer[] scores = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox<Integer> cb = new JComboBox<>(scores);
        cb.setPreferredSize(new Dimension(70, 25));
        cbs.add(cb);

        scorePanel.add(scoreLabel);
        scorePanel.add(cb);

        teamPanel.add(nameLabel, BorderLayout.WEST);
        teamPanel.add(scorePanel, BorderLayout.EAST);

        // Linea separatrice
        teamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        container.add(teamPanel);
    }



    /**
     * Configura l'event handler per il pulsante di invio dei voti.
     * Quando premuto, salva tutti i voti assegnati ai team e disabilita il pulsante.
     *
     * @param inviaVotiBtn il pulsante a cui collegare l'event handler
     */
    private void handleInviaVoti(JButton inviaVotiBtn) {
        inviaVotiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 0; i < teams.size(); i++) {
                    Team team = teams.get(i);
                    Integer voto = (Integer) cbs.get(i).getSelectedItem();
                    controller.salvaVoto(team.getId(), voto);
                    inviaVotiBtn.setEnabled(false);
                }
            }
        });
    }
}

