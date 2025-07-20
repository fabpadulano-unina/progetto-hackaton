package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;


/**
 * Pannello per visualizzare la classifica finale di un hackathon.
 * Mostra i team ordinati per punteggio con i risultati
 * delle valutazioni dei giudici.
 */
public class Classifica {
    public static final String FONT = "Arial";
    private final JPanel panel = new JPanel();

    private final List<String> nomiTeam;
    private final List<Integer> punteggi;

    /**
     * Costruttore della classifica finale dell'hackathon.
     * Riceve i nomi dei team e i rispettivi punteggi per creare
     * il pannello con la visualizzazione ordinata dei risultati.
     * @param nomiTeam la lista dei nomi dei team
     * @param punteggi la lista dei punteggi corrispondenti
     */
    public Classifica(List<String> nomiTeam, List<Integer> punteggi) {
        this.nomiTeam = nomiTeam;
        this.punteggi = punteggi;
        setPanel();
    }

    /**
     * Restituisce il pannello principale della classifica.
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Configura il layout del pannello e aggiunge tutti i team in classifica.
     * Crea il titolo e organizza i team in ordine di punteggio.
     */
    private void setPanel() {
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Top Teams");
        titleLabel.setFont(new Font(FONT, Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel teamsPanel = new JPanel();
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));
        teamsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        panel.add(teamsPanel, BorderLayout.CENTER);

        for (int i = 0; i < nomiTeam.size(); i++) {
            aggiungiTeamInClassifica(teamsPanel, nomiTeam.get(i), punteggi.get(i));
        }
    }

    /**
     * Aggiunge un team alla visualizzazione della classifica.
     * Crea un pannello con nome del team e punteggio formattato.
     *
     * @param container il pannello contenitore dove aggiungere il team
     * @param teamName il nome del team
     * @param score il punteggio del team
     */
    private void aggiungiTeamInClassifica(JPanel container, String teamName, int score) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBackground(Color.WHITE);
        teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel nameLabel = new JLabel(teamName);
        nameLabel.setFont(new Font(FONT, Font.BOLD, 16));

        JLabel scoreLabel = new JLabel("Punteggio: " + score);
        scoreLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(100, 100, 100));

        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.add(scoreLabel);

        teamPanel.add(nameLabel, BorderLayout.WEST);
        teamPanel.add(scorePanel, BorderLayout.EAST);

        teamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        container.add(teamPanel);
        container.add(Box.createVerticalStrut(5));
    }
}
