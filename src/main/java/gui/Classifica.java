package gui;

import javax.swing.*;
import java.awt.*;

public class Classifica  extends JPanel {
    public static final String FONT = "Arial";
    private JPanel panel;

    public Classifica() {
        setPanel();
    }

    private void setPanel() {
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Top Teams");
        titleLabel.setFont(new Font(FONT, Font.BOLD, 20));

        //per togliere il bordo nero ai lati
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale con la classifica dove ogni panel andrà uno sotto l'altro (Y-AXIS)
        JPanel teamsPanel = new JPanel();
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));

        //per togliere il bordo nero ai lati
        teamsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        panel.add(teamsPanel, BorderLayout.CENTER);

        // Aggiungi i team (puoi ottenere questi dati dal controller)
        aggiungiTeamInClassifica(teamsPanel, "Team Innovators", 95);
        aggiungiTeamInClassifica(teamsPanel, "Code Wizards", 92);
        aggiungiTeamInClassifica(teamsPanel, "Tech Titans", 88);
        aggiungiTeamInClassifica(teamsPanel, "Digital Dynamos", 85);

    }

    private void aggiungiTeamInClassifica(JPanel container, String teamName, int score) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        teamPanel.setBackground(Color.WHITE);
        teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Nome team
        JLabel nameLabel = new JLabel(teamName);
        nameLabel.setFont(new Font(FONT, Font.BOLD, 16));

        // Punteggio
        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(100, 100, 100));

        // Pannello per allineare il punteggio a destra
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.add(scoreLabel);

        teamPanel.add(nameLabel, BorderLayout.WEST);
        teamPanel.add(scorePanel, BorderLayout.EAST);

        // Linea separatrice
        teamPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        container.add(teamPanel);
        container.add(Box.createVerticalStrut(5));
    }

}
