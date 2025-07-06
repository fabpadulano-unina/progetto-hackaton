package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private final Integer idTeam;
    private File file;

    public Progress(Controller controller, String hackaton, Integer idTeam, String team, List<String> partecipantiTeam) {
        this.setTitle("Progressi");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        this.idTeam = idTeam;

        teamLabel.setText("TEAM: " + team);
        hackatonLabel.setText(hackaton);
        numeroMembriLabel.setText(String.valueOf(partecipantiTeam.size()));
        setUtentiList(partecipantiTeam);

        handleFile();
        handleSalva();
    }

    private void handleFile() {
        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // se viene selezionato correttamente un file
                    file = fileChooser.getSelectedFile();
                    salvaButton.setEnabled(true);
                }
            }
        });
    }

    private void handleSalva() {
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addDocumento(idTeam, descrizioneProgressiTextArea.getText(), file);
                Controller.dispose(Progress.this);
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
