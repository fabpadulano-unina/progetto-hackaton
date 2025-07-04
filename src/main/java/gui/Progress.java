package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Progress extends JFrame {
    private JPanel mainPanel;
    private JTextArea descrizioneProgressiTextArea;
    private JButton caricaButton;
    private JList<String> utentiList;
    private JButton salvaButton;
    private JLabel teamLabel;
    private JLabel hackatonLabel;
    private JLabel numeroMembriLabel;
    private Controller controller;

    public Progress(Controller controller, String hackaton, String team, List<String> partecipantiTeam) {
        this.setTitle("Progressi");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        teamLabel.setText("TEAM: " + team);
        hackatonLabel.setText(hackaton);
        numeroMembriLabel.setText(String.valueOf(partecipantiTeam.size()));
        setUtentiList(partecipantiTeam);

        handleSalva();
    }

    private void handleSalva() {
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setUtentiList(List<String> partecipantiTeam) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : partecipantiTeam) {
            model.addElement(s);
        }

        utentiList.setModel(model);
    }

}
