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
    private JScrollPane feedbackScrollPane;
    private JScrollPane classificaScrollPane;
    private JPanel classificaTab;
    private Controller controller;
    private Integer hackatonId;
    private LocalDate dataInizio;

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
        aggiornaVisibilitaPulsanti(isRegistrazioneAperte, deadline, numMaxIscritti);
        setTableGiudici(giudici);
        handleApriRegistraioni(apriRegistrazioniButton);
        handleRegistrati(registratiBtn);
        setRegistraBtnText();
        setPubblicaDescrizione(descrizioneProblema);
        handlePubblicazioneDescrizione();
        setGiudiciTabView(dataInizio, dataFine);
        setClassificaTabView(dataFine);
    }

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
                dataFineFormatted,
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

    private void aggiornaVisibilitaPulsanti(boolean isRegistrazioneAperte, LocalDate deadline, int numMaxIscritti) {
        registratiBtn.setVisible(false);
        tabbedPane.remove(organizerToolTab);
        tabbedPane.remove(giudiciToolTab);

        if(controller.isOrganizzatore()) {
            tabbedPane.add("Tool per organizzatori", organizerToolTab);
            if(deadline != null) {
                deadlineRegistrazioniField.setText(deadline.format(DATE_TIME_FORMATTER));
                disableDeadlineField();
            }
        } else if(controller.isPartecipante()) {
            if(isRegistrazioneAperte && controller.getNumeroUtentiRegistrati(hackatonId) < numMaxIscritti) registratiBtn.setVisible(true);
        } else {
            tabbedPane.add("Tool per giudici", giudiciToolTab);
        }
    }

    private void handleApriRegistraioni(JButton apriRegistrazioniButton) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        apriRegistrazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.apriRegistrazioni(hackatonId, LocalDate.parse(deadlineRegistrazioniField.getText(), formatter));
                disableDeadlineField();
            }
        });
    }

    private void handleRegistrati(JButton registratiBtn ) {
        registratiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.registraUtenteHackaton(hackatonId);
                setRegistraBtnText();
            }
        });
    }

    private void setRegistraBtnText() {
        if(controller.isUtenteRegistrato(hackatonId)) {
            registratiBtn.setText("Registrato ✓");
            registratiBtn.setEnabled(false);
            registratiBtn.setForeground(Color.getColor("#013220"));
        }
    }

    private void disableDeadlineField() {
        deadlineRegistrazioniField.setEditable(false);
        apriRegistrazioniButton.setEnabled(false);
    }

    private void setPubblicaDescrizione(String descrizioneProblema) {
        if(descrizioneProblema != null || LocalDate.now().isBefore(dataInizio)) {
            descrizioneProblemaTextArea.setText(descrizioneProblema);
            descrizioneProblemaTextArea.setEnabled(false);
            saveDescrizioneBtn.setEnabled(false);
            setDescrizioneProblemaLabel(descrizioneProblema);
        }
    }

    private void handlePubblicazioneDescrizione() {
        saveDescrizioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descirizione = descrizioneProblemaTextArea.getText();
                controller.setDescrizioneProblema(hackatonId, descirizione);
                setPubblicaDescrizione(descirizione);
            }
        });
    }

    private void setDescrizioneProblemaLabel(String descrizioneProblema) {
        if(descrizioneProblema != null) {
            descrizioneProblemaLabel.setText(descrizioneProblema);
            descrizioneProblemaLabel.setFont(descrizioneProblemaLabel.getFont().deriveFont(Font.BOLD));
        }
    }

    private void setGiudiciTabView(LocalDate dataInizio, LocalDate dataFine) {
        LocalDate now = LocalDate.now();
        feedbackScrollPane.setBorder(BorderFactory.createEmptyBorder());
        if (!now.isBefore(dataInizio) && !now.isAfter(dataFine)) {
            List<Documento> documenti = controller.getDocumentiByHackatonId(hackatonId);
            if(!documenti.isEmpty()) {
                feedbackScrollPane.setViewportView(new FeedbackPanel(controller, documenti).getContainerPanel());
            }
        } else if(now.isAfter(dataFine) && !controller.giudiceHaVotatoInHackaton(hackatonId)) {
            feedbackScrollPane.setViewportView(new VotiPanel(controller, controller.getTeamByHackaton(hackatonId)).getPanel());
        } else {
            tabbedPane.remove(giudiciToolTab);
        }
    }

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
