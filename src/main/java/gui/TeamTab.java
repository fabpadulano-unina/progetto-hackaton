package gui;

import controller.Controller;
import model.Commento;

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
    private JButton refreshButton;
    private final Controller controller;

    public TeamTab(Controller controller) {
        this.controller = controller;
        //aggiungo il mainpanel al root del panel esteso
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        handleClicks();

        setTeamProgressiCbData();
        setHackatonCbData();

        handleCbChangeListeners();
    }

    private void handleCbChangeListeners() {
        setTeamProgressCbChangeListener();
        setHackatonCbChangeListener();
        setTeamCbChangeListener();
    }

    private void setTeamProgressiCbData() {
        // svuoto per il caso di refresh
        teamProgressiCb.removeAllItems();

        teamProgressiCb.addItem("-- Seleziona un team a cui appartieni--");
        for(String titolo : controller.getTeamByPartecipanteCb()) {
            teamProgressiCb.addItem(titolo);
        }
    }

    private void setHackatonCbChangeListener() {
        hackatonCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTeamCbData();
                addTeamBtn.setEnabled(hackatonCb.getSelectedIndex() != 0);
            }
        });
    }

    private void setTeamCbChangeListener() {
        teamCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uniscitiButton.setEnabled(teamCb.getSelectedIndex() != 0);
            }
        });
    }

    private void setTeamProgressCbChangeListener() {
        teamProgressiCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProgressiList();
                caricaNuovoProgressoButton.setEnabled(teamProgressiCb.getSelectedIndex() != 0);
            }
        });
    }

    private void setProgressiList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        int teamIndex = getTeamIndex();

        if(teamIndex >= 0) {
            for (Commento docCommento : controller.getDocumentoAndFeedbackByTeam(teamIndex)) {
                String descrizione = "<html>Descrizione: " + docCommento.getDocumento().getDescrizione();
                if(docCommento.getFeedback() != null) {
                    descrizione += "<span style='color: black; font-weight:bold;'>  | " +
                            docCommento.getGiudice().getNome() + " " + docCommento.getGiudice().getCognome() +
                            "</span> ha commentato:  <span style='color: red;font-weight:bold;'>" +
                            docCommento.getFeedback() +
                            "</span>";
                }

                descrizione += "</html>";
                model.addElement(descrizione);

            }
        }

        progressiList.setModel(model);
    }

    private void setHackatonCbData() {
        // svuoto per il caso di refresh
        hackatonCb.removeAllItems();

        hackatonCb.addItem("-- Seleziona un hackaton --");
        for(String titolo : controller.getHackatonsNamesForCombobox()) {
            hackatonCb.addItem(titolo);
        }
    }

    private void handleClicks() {
        this.handleAddTeam();
        this.handleProgress();
        this.handleUnisciti();
        this.handleRefresh();
    }

    private void handleRefresh() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTeamProgressiCbData();
                setHackatonCbData();
                setProgressiList();
                setTeamCbData();
            }
        });
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

    private void handleUnisciti() {
        uniscitiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addPartecipanteAlTeam(getSelectedTeam(), getSelectedHackaton());
                setTeamCbData();
            }
        });

    }



    private void setTeamCbData() {
        //svuoto i team selezioanbili precedentemente
        teamCb.removeAllItems();

        teamCb.addItem("-- Seleziona un team a cui unirti per l'hackaton selezionato--");
        String selectedHackaton = getSelectedHackaton();
        if(selectedHackaton != null) {
            for(String nome : controller.getTeamByHackaton(selectedHackaton)) {
                teamCb.addItem(nome);
            }
        }
    }

    private String getSelectedHackaton() {
        return (String) hackatonCb.getSelectedItem();
    }

    private String getSelectedTeam() {
        return (String) teamCb.getSelectedItem();
    }

    private void handleProgress() {
        caricaNuovoProgressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openProgress(getTeamIndex());
            }
        });
    }

    private int getTeamIndex() {
        return teamProgressiCb.getSelectedIndex() - 1; // -1 perchè il primo è il placebolder
    }
}
