package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Classifica {
    public static final String FONT = "Arial";
    private final JPanel panel = new JPanel();

    private final List<String> nomiTeam;
    private final List<Integer> punteggi;

    public Classifica(List<String> nomiTeam, List<Integer> punteggi) {
        this.nomiTeam = nomiTeam;
        this.punteggi = punteggi;
        setPanel();
    }

    public JPanel getPanel() {
        return panel;
    }

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
