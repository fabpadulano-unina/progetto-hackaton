package gui;

import controller.Controller;
import model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VotiPanel extends JPanel {
    public static final String FONT = "Arial";
    private JPanel panel;
    private final Controller controller;
    private final List<Team> teams;
    private final List<JComboBox<Integer>> cbs = new ArrayList<>();

    public VotiPanel(Controller controller, List<Team> teams) {
        this.controller = controller;
        this.teams = teams;
        setPanel();
    }

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


    private void getTitolo() {
        JLabel titoloLabel = new JLabel("Team Rankings");
        titoloLabel.setFont(new Font(FONT, Font.BOLD, 20));
        titoloLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titoloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        getPanel().add(titoloLabel, BorderLayout.NORTH);
    }

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

