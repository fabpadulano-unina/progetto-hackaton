package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Voti extends JPanel {
    public static final String FONT = "Arial";
    private JPanel panel;
    private String[] teamNames;
    private JComboBox<Integer>[] scoreBoxes;

    public Voti() {
        // Array semplice di nomi team
        teamNames = new String[5];
        teamNames[0] = "Project Alpha";
        teamNames[1] = "Code Warriors";
        teamNames[2] = "Tech Titans";
        teamNames[3] = "Data Dynamos";
        teamNames[4] = "Cyber Ninjas";

        // Array di combobox
        scoreBoxes = new JComboBox[5];

        setPanel();
    }

    private void setPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Team Rankings");
        titleLabel.setFont(new Font(FONT, Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // serve a centrare la scritta Team Rankings
        panel.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale con i team
        JPanel teamsPanel = new JPanel();
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));
        teamsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));
        panel.add(teamsPanel, BorderLayout.CENTER);

        // Aggiungi i team con combobox
        for (int i = 0; i < teamNames.length; i++) {
            aggiungiTeamConVoto(teamsPanel, teamNames[i], i);
        }

        // Pannello bottone
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitButton = new JButton("Submit Rankings");
        submitButton.setPreferredSize(new Dimension(200, 35));
        submitButton.setBackground(new Color(144, 202, 249));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFont(new Font(FONT, Font.BOLD, 14));
        submitButton.setBorder(BorderFactory.createEmptyBorder());


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonPanel.add(submitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void aggiungiTeamConVoto(JPanel container, String teamName, int index) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        teamPanel.setBackground(Color.WHITE);
        teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Nome team
        JLabel nameLabel = new JLabel(teamName);
        nameLabel.setFont(new Font(FONT, Font.BOLD, 16));

        // Pannello per score con combobox
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.WHITE);

        JLabel scoreLabel = new JLabel("Score: ");
        scoreLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(100, 100, 100));

        // ComboBox per il voto
        Integer[] scores = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        scoreBoxes[index] = new JComboBox<>(scores);
        scoreBoxes[index].setPreferredSize(new Dimension(60, 25));

        scorePanel.add(scoreLabel);
        scorePanel.add(scoreBoxes[index]);

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

