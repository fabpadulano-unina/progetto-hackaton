package gui;

import controller.Controller;
import model.Commento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Pannello per la gestione dei team negli hackathon.
 * Permette ai partecipanti di creare team, unirsi a team esistenti
 * e caricare progressi durante lo svolgimento degli hackathon.
 */
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


    /**
     * Costruttore del pannello team.
     * Inizializza l'interfaccia grafica, imposta i dati delle combo box
     * e configura i listener per gestire le interazioni dell'utente.
     *
     * @param controller il controller principale dell'applicazione
     */
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

    /**
     * Popola la combo box dei team di cui l'utente fa parte.
     * Carica i team del partecipante corrente e li inserisce
     * nella combo box per la selezione dei progressi.
     */
    private void setTeamProgressiCbData() {
        // svuoto per il caso di refresh
        teamProgressiCb.removeAllItems();

        teamProgressiCb.addItem("-- Seleziona un team a cui appartieni--");
        for(String titolo : controller.getTeamByPartecipanteCb()) {
            teamProgressiCb.addItem(titolo);
        }
    }

    /**
     * Imposta il listener per i cambiamenti nella combo box degli hackathon.
     * Quando viene selezionato un hackathon, aggiorna la lista dei team
     * e abilita il pulsante per creare un nuovo team.
     */
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

    /**
     * Imposta il listener per i cambiamenti nella combo box dei progressi team.
     * Quando viene selezionato un team, carica i suoi progressi
     * e abilita il pulsante per caricare nuovi progressi.
     */
    private void setTeamProgressCbChangeListener() {
        teamProgressiCb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setProgressiList();
                caricaNuovoProgressoButton.setEnabled(teamProgressiCb.getSelectedIndex() != 0);
            }
        });
    }

    /**
     * Carica e visualizza i progressi del team selezionato.
     * Recupera documenti e feedback per il team e li formatta
     * in HTML per la visualizzazione nella lista dei progressi.
     */
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

    /**
     * Popola la combo box con la lista degli hackathon disponibili.
     * Svuota la combo box e aggiunge un'opzione di default seguita
     * dai titoli di tutti gli hackathon.
     */
    private void setHackatonCbData() {
        // svuoto per il caso di refresh
        hackatonCb.removeAllItems();

        hackatonCb.addItem("-- Seleziona un hackaton --");
        for(String titolo : controller.getHackatonsNamesForCombobox()) {
            hackatonCb.addItem(titolo);
        }
    }

    /**
     * Configura tutti i gestori di eventi per i pulsanti.
     * Imposta i listener per i pulsanti di creazione team,
     * caricamento progressi, unione al team e refresh.
     */
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

    /**
     * Configura il gestore per il pulsante di creazione team.
     * Quando premuto, apre il form per creare un nuovo team
     * per l'hackathon selezionato.
     */
    private void handleAddTeam() {
        addTeamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openTeamForm(getSelectedHackaton());
            }
        });
    }

    /**
     * Configura il gestore per il pulsante di unione al team.
     * Quando premuto, aggiunge il partecipante corrente
     * al team selezionato e aggiorna la lista dei team.
     */
    private void handleUnisciti() {
        uniscitiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addPartecipanteAlTeam(getSelectedTeam(), getSelectedHackaton());
                setTeamCbData();
            }
        });

    }


    /**
     * Popola la combo box dei team disponibili per l'hackathon selezionato.
     * Carica tutti i team dell'hackathon corrente e permette
     * all'utente di selezionarne uno per unirsi.
     */
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

    /**
     * Restituisce l'hackathon attualmente selezionato nella combo box.
     *
     * @return il nome dell'hackathon selezionato
     */
    private String getSelectedHackaton() {
        return (String) hackatonCb.getSelectedItem();
    }

    /**
     * Restituisce il team attualmente selezionato nella combo box.
     *
     * @return il nome del team selezionato
     */
    private String getSelectedTeam() {
        return (String) teamCb.getSelectedItem();
    }


    /**
     * Configura il gestore per il pulsante di caricamento progressi.
     * Quando premuto, apre il form per caricare un nuovo progresso
     * per il team selezionato.
     */
    private void handleProgress() {
        caricaNuovoProgressoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.openProgress(getTeamIndex());
            }
        });
    }


    /**
     * Restituisce l'indice del team selezionato nella combo box progressi.
     * Sottrae 1 per compensare l'elemento placeholder nella combo box.
     *
     * @return l'indice del team selezionato, -1 se nessun team valido
     */
    private int getTeamIndex() {
        return teamProgressiCb.getSelectedIndex() - 1; // -1 perchè il primo è il placebolder
    }
}
