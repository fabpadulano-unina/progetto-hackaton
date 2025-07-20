package gui;

import controller.Controller;
import model.Documento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Finestra per visualizzare i dettagli completi di un hackathon.
 * Mostra informazioni, gestisce registrazioni e fornisce strumenti
 * specifici per organizzatori e giudici in base al ruolo dell'utente.
 */
public class HackatonDetails extends JFrame {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JButton registratiBtn;
    private JTabbedPane tabbedPane;
    private JPanel organizerToolTab;
    private JPanel mainPanel;
    private JTable tableGiudici;
    private JLabel titoloLabel;
    private JLabel nomeHackatonLabel;
    private JLabel descrizioneProblemaLabel;
    private JLabel organizzatoreLabel;
    private JLabel dataInizioLabel;
    private JLabel dataFineLabel;
    private JPanel giudiciToolTab;
    private JButton apriRegistrazioniButton;
    private JTextField deadlineRegistrazioniField;
    private JLabel registrazioniOpenedLabel;
    private JTextArea descrizioneProblemaTextArea;
    private JButton saveDescrizioneBtn;
    private JScrollPane giudiciScrollPane;
    private JScrollPane classificaScrollPane;
    private JPanel classificaTab;
    private final Controller controller;
    private final Integer hackatonId;
    private final LocalDate dataInizio;

    /**
     * Costruttore della finestra dettagli hackathon.
     * Inizializza l'interfaccia con tutte le informazioni dell'evento
     * e configura la visibilità dei componenti in base al ruolo utente.
     *
     * @param controller il controller per gestire le operazioni
     * @param idHackaton l'ID univoco dell'hackathon
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dell'evento
     * @param dataInizio la data di inizio dell'hackathon
     * @param dataFine la data di fine dell'hackathon
     * @param numMaxIscritti il numero massimo di iscritti
     * @param dimMaxTeam la dimensione massima del team
     * @param isRegistrazioneAperte se le registrazioni sono aperte
     * @param deadline la scadenza per le registrazioni
     * @param descrizioneProblema la descrizione del problema da risolvere
     * @param nomeOrganizzatore il nome dell'organizzatore
     * @param cognomeOrganizzatore il cognome dell'organizzatore
     * @param giudici la matrice dei dati dei giudici
     */
    public HackatonDetails(
            Controller controller,
            Integer idHackaton,
            String titolo,
            String sede,
            LocalDate dataInizio,
            LocalDate dataFine,
            int numMaxIscritti,
            int dimMaxTeam,
            boolean isRegistrazioneAperte,
            LocalDate deadline,
            String descrizioneProblema,
            String nomeOrganizzatore,
            String cognomeOrganizzatore,
            Object[][] giudici)
    {
        this.setTitle("Dettaglio Hackaton");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        this.hackatonId = idHackaton;
        this.dataInizio = dataInizio;

        setDettagli(titolo, sede, dataInizio, dataFine, numMaxIscritti, dimMaxTeam, isRegistrazioneAperte, descrizioneProblema, nomeOrganizzatore, cognomeOrganizzatore);
        aggiornaVisibilita(isRegistrazioneAperte, deadline, numMaxIscritti);
        setTableGiudici(giudici);
        handleApriRegistrazioni(apriRegistrazioniButton);
        handleRegistrati(registratiBtn);
        setRegistraBtnText();
        setPubblicaDescrizione(descrizioneProblema);
        handlePubblicazioneDescrizione();
        setGiudiciTabView(dataInizio, dataFine);
        setClassificaTabView(dataFine);
    }

    /**
     * Popola i campi della finestra con i dettagli dell'hackathon.
     * Formatta le date e compone il testo descrittivo con tutte
     * le informazioni principali dell'evento.
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dell'evento
     * @param dataInizio la data di inizio dell'hackathon
     * @param dataFine la data di fine dell'hackathon
     * @param numMaxIscritti il numero massimo di iscritti
     * @param dimMaxTeam la dimensione massima del team
     * @param descrizioneProblema la descrizione del problema da risolvere
     * @param nomeOrganizzatore il nome dell'organizzatore
     * @param cognomeOrganizzatore il cognome dell'organizzatore
     */
    private void setDettagli(
            String titolo,
            String sede,
            LocalDate dataInizio,
            LocalDate dataFine,
            int numMaxIscritti,
            int dimMaxTeam,
            boolean registrazioniAperte,
            String descrizioneProblema,
            String nomeOrganizzatore,
            String cognomeOrganizzatore
    ) {
        String dataInizioFormatted = dataInizio.format(DATE_TIME_FORMATTER);
        String dataFineFormatted = dataFine.format(DATE_TIME_FORMATTER);

        nomeHackatonLabel.setText(titolo);
        titoloLabel.setText(String.format(
                "L'hackaton \"%s\" si terrà presso la sede di %s dal %s al %s. " +
                        "Il numero massimo di iscritti è %d e ogni team potrà essere composto da un massimo di %d partecipanti.",
                titolo,
                sede,
                dataInizioFormatted,
                dataFineFormatted,
                numMaxIscritti,
                dimMaxTeam
        ));

        setDescrizioneProblemaLabel(descrizioneProblema);
        dataInizioLabel.setText(dataInizioFormatted);
        dataFineLabel.setText(dataFineFormatted);
        organizzatoreLabel.setText(nomeOrganizzatore + " " + cognomeOrganizzatore);

        if(registrazioniAperte) {
            registrazioniOpenedLabel.setText("LE REGISTRAZIONI SONO APERTE!");
        }
    }

    /**
     * Configura la tabella dei giudici dell'hackathon.
     * Imposta le colonne per nome, cognome ed email dei giudici
     * selezionati dall'organizzatore.
     *
     * @param giudici la matrice con i dati dei giudici da visualizzare
     */
    private void setTableGiudici(Object[][] giudici) {
        String[] columnNames = {"Nome", "Cognome", "Email"};
        DefaultTableModel model = new DefaultTableModel(giudici, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        tableGiudici.setModel(model);
        tableGiudici.setRowHeight(30);
        for (int i = 0; i < 2; i++) {
            tableGiudici.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(new JTextField()));
        }
    }

    /**
     * Gestisce la visibilità dei componenti in base al ruolo utente.
     * Mostra o nasconde pulsanti e tab specifici per organizzatori,
     * partecipanti e giudici dell'hackathon.
     *
     * @param isRegistrazioneAperte se le registrazioni sono attualmente aperte
     * @param deadline la scadenza per le registrazioni
     * @param numMaxIscritti il numero massimo di iscritti consentiti
     */
    private void aggiornaVisibilita(boolean isRegistrazioneAperte, LocalDate deadline, int numMaxIscritti) {
        registratiBtn.setVisible(false);
        tabbedPane.remove(organizerToolTab);
        tabbedPane.remove(giudiciToolTab);

        if(controller.isOrganizzatore() && controller.isOrganizzatoreOfHackaton(hackatonId)) {
            tabbedPane.add("Tool per organizzatori", organizerToolTab);
            if(deadline != null) {
                deadlineRegistrazioniField.setText(deadline.format(DATE_TIME_FORMATTER));
                disableDeadlineField();
            }
        } else if(controller.isPartecipante()) {
            if(isRegistrazioneAperte && controller.getNumeroUtentiRegistrati(hackatonId) < numMaxIscritti) registratiBtn.setVisible(true);
        } else if(controller.isGiudice() && controller.isGiudiceInHackaton(hackatonId)){
            tabbedPane.add("Tool per giudici", giudiciToolTab);
        }
    }

    /**
     * Gestisce il click sul pulsante per aprire le registrazioni.
     * Permette all'organizzatore di attivare le iscrizioni all'hackathon
     * impostando la deadline di chiusura.
     *
     * @param apriRegistrazioniButton il pulsante per aprire le registrazioni
     */
    private void handleApriRegistrazioni(JButton apriRegistrazioniButton) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        apriRegistrazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.apriRegistrazioni(hackatonId, LocalDate.parse(deadlineRegistrazioniField.getText(), formatter));
                disableDeadlineField();
            }
        });
    }

    /**
     * Gestisce il click sul pulsante di registrazione all'hackathon.
     * Consente ai partecipanti di iscriversi all'evento e aggiorna
     * l'interfaccia per mostrare lo stato della registrazione.
     *
     * @param registratiBtn il pulsante di registrazione
     */
    private void handleRegistrati(JButton registratiBtn ) {
        registratiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registraUtenteHackaton(hackatonId);
                setRegistraBtnText();
            }
        });
    }

    /**
     * Aggiorna il testo del pulsante di registrazione.
     * Mostra "Registrato ✓" se l'utente è già iscritto all'hackathon
     * e disabilita il pulsante per evitare registrazioni multiple.
     */
    private void setRegistraBtnText() {
        if(controller.isUtenteRegistrato(hackatonId)) {
            registratiBtn.setText("Registrato ✓");
            registratiBtn.setEnabled(false);
            registratiBtn.setForeground(Color.getColor("#013220"));
        }
    }

    /**
     * Disabilita i campi per la gestione delle registrazioni.
     * Impedisce modifiche alla deadline una volta che le registrazioni
     * sono state aperte dall'organizzatore.
     */
    private void disableDeadlineField() {
        deadlineRegistrazioniField.setEditable(false);
        apriRegistrazioniButton.setEnabled(false);
    }

    /**
     * Configura l'area di testo per la descrizione del problema.
     * @param descrizioneProblema la descrizione del problema
     */
    private void setPubblicaDescrizione(String descrizioneProblema) {
        if(descrizioneProblema != null || LocalDate.now().isBefore(dataInizio)) {
            descrizioneProblemaTextArea.setText(descrizioneProblema);
            descrizioneProblemaTextArea.setEnabled(false);
            saveDescrizioneBtn.setEnabled(false);
            setDescrizioneProblemaLabel(descrizioneProblema);
        }
    }

    /**
     * Gestisce la pubblicazione della descrizione del problema.
     * Permette ai giudici di salvare la descrizione del problema
     * che i team dovranno affrontare durante l'hackathon.
     */
    private void handlePubblicazioneDescrizione() {
        saveDescrizioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descrizione = descrizioneProblemaTextArea.getText();
                controller.setDescrizioneProblema(hackatonId, descrizione);
                setPubblicaDescrizione(descrizione);
            }
        });
    }

    /**
     * Aggiorna l'etichetta con la descrizione del problema.
     * Mostra il testo della sfida dell'hackathon in grassetto
     * se è già stata pubblicata dai giudici.
     *
     * @param descrizioneProblema il testo della descrizione del problema
     */
    private void setDescrizioneProblemaLabel(String descrizioneProblema) {
        if(descrizioneProblema != null) {
            descrizioneProblemaLabel.setText(descrizioneProblema);
            descrizioneProblemaLabel.setFont(descrizioneProblemaLabel.getFont().deriveFont(Font.BOLD));
        }
    }

    /**
     * Configura la vista del tab per i giudici.
     * Mostra i documenti dei team durante l'hackathon per i feedback
     * o il pannello di voto una volta terminato l'evento.
     *
     * @param dataInizio la data di inizio dell'hackathon
     * @param dataFine la data di fine dell'hackathon
     */
    private void setGiudiciTabView(LocalDate dataInizio, LocalDate dataFine) {
        LocalDate now = LocalDate.now();
        giudiciScrollPane.setBorder(BorderFactory.createEmptyBorder());
        if (!now.isBefore(dataInizio) && !now.isAfter(dataFine)) {
            List<Documento> documenti = controller.getDocumentiByHackatonId(hackatonId);
            if(!documenti.isEmpty()) {
                giudiciScrollPane.setViewportView(new FeedbackPanel(controller, documenti).getContainerPanel());
            }
            else {
                giudiciScrollPane.setVisible(false);
            }
        } else if(now.isAfter(dataFine) && !controller.giudiceHaVotatoInHackaton(hackatonId)) {
            giudiciScrollPane.setViewportView(new VotiPanel(controller, controller.getTeamByHackaton(hackatonId)).getPanel());
        } else {
            tabbedPane.remove(giudiciToolTab);
        }
    }

    /**
     * Configura la vista del tab della classifica.
     * Mostra i risultati finali dell'hackathon con i team
     * ordinati per punteggio una volta terminato l'evento.
     *
     * @param dataFine la data di fine dell'hackathon
     */
    private void setClassificaTabView(LocalDate dataFine) {
        tabbedPane.remove(classificaTab);
        if(LocalDate.now().isAfter(dataFine)) {
            List<String> nomiTeam = new ArrayList<>();
            List<Integer> voti = new ArrayList<>();
            controller.getClassifica(hackatonId, nomiTeam, voti);

            if(!nomiTeam.isEmpty()) {
                tabbedPane.add("Classifica", classificaTab);
                classificaScrollPane.setViewportView(new Classifica(nomiTeam, voti).getPanel());
            }
        }
    }

}
