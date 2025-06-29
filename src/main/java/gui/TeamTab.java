package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamTab extends JPanel {
    private JComboBox<String> teamCb;
    private JList<String> progressiList;
    private JButton caricaNuovoProgressoButton;
    private JButton uniscitiButton;
    private JButton addTeamBtn;
    private JPanel mainPanel;
    private JComboBox<String> hackatonCb;
    private Controller controller;

    public TeamTab(Controller controller) {
        this.controller = controller;
        //aggiungo il mainpanel al root del panel esteso
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        handleClicks();
        setTeamComboBoxData();

    }

    private void setTeamComboBoxData() {
        for(String titolo : controller.getHackatonsNamesForCombobox()) {
            hackatonCb.addItem(titolo);
        }
    }

    private void handleClicks() {
        this.handleAddTeam();
        this.handleProgress();
    }

    private void handleAddTeam() {
        addTeamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openTeamForm();
            }
        });
    }


    private void handleProgress() {
        caricaNuovoProgressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openProgress();
            }
        });
    }
}
