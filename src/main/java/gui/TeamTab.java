package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamTab extends JPanel {
    private JComboBox<String> teamProgressiCb;
    private JList<String> progressiList;
    private JButton caricaNuovoProgressoButton;
    private JButton uniscitiButton;
    private JButton addTeamBtn;
    private JPanel mainPanel;
    private JComboBox<String> hackatonCb;
    private JComboBox<String> teamCb;
    private Controller controller;

    public TeamTab(Controller controller) {
        this.controller = controller;
        //aggiungo il mainpanel al root del panel esteso
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        handleClicks();
        setTeamProgressiCbData();
        setHackatonCbChangeListener();
        setHackatonCbData();

    }

    private void setTeamProgressiCbData() {
        for(String titolo : controller.getTeamByPartecipanteCb()) {
            teamProgressiCb.addItem(titolo);
        }
    }

    private void setHackatonCbChangeListener() {
        hackatonCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTeamCbData();
            }
        });

    }

    private void setHackatonCbData() {
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
                controller.openTeamForm(getSelectedHackaton());
                setTeamCbData();
            }
        });
    }

    private void setTeamCbData() {
        for(String nome : controller.getTeamByHackaton(getSelectedHackaton())) {
            teamCb.addItem(nome);
        }
    }

    private String getSelectedHackaton() {
        return (String) hackatonCb.getSelectedItem();
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
