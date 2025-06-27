package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HackatonDetails extends JFrame {
    public static final String DIALOG = "Dialog";
    public static final String FONT = "Arial";
    private JButton registratiBtn;
    private JTabbedPane tabbedPane;
    private JPanel overviewTab;
    private JPanel giudiciTab;
    private JPanel organizerToolTab;
    private JPanel mainPanel;
    private JTable tableGiudici;
    private JPanel containerPanel;
    private JScrollPane ScrollPanPrinc;
    private JPanel MainCont;
    private JPanel classificaTab;
    private JLabel descrizioneLabel;
    private JLabel nomeHackatonLabel;
    private JLabel sedeLabel;
    private JLabel organizzatoreLabel;
    private JLabel dataInizioLabel;
    private JLabel dataFineLabel;
    private JPanel giudiciToolTab;
    private JButton apriRegistrazioniButton;
    private JTextField deadlineRegistrazioniField;
    private Controller controller;
    private Integer hackatonId = null;


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
            String nomeOrganizzatore,
            String cognomeOrganizzatore,
            Object[][] giudici) {
        this.setTitle("Dettaglio Hackaton");
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        this.hackatonId = idHackaton;

        setDettagli(titolo, sede, dataInizio, dataFine, numMaxIscritti, dimMaxTeam, nomeOrganizzatore, cognomeOrganizzatore);
        aggiornaVisibilitaPulsanti(isRegistrazioneAperte, numMaxIscritti);
        setTableGiudici(giudici);
        inizializzaContainerPanel();
        aggiungiEsempiFeedback();
        inizializzaClassificaTab();
        handleApriRegistraioni(apriRegistrazioniButton);
        handleRegistrati(registratiBtn);
        setRegistraBtnText();
    }



    private void setDettagli(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, String nomeOrganizzatore, String cognomeOrganizzatore) {
        nomeHackatonLabel.setText(titolo);
        descrizioneLabel.setText(String.format(
                "L'hackaton \"%s\" si terrà presso la sede di %s dal %s al %s. " +
                        "Il numero massimo di iscritti è %d e ogni team potrà essere composto da un massimo di %d partecipanti.",
                titolo,
                sede,
                dataInizio.toString(),
                dataFine.toString(),
                numMaxIscritti,
                dimMaxTeam
        ));

        sedeLabel.setText(sede);
        dataInizioLabel.setText(dataInizio.toString());
        dataFineLabel.setText(dataFine.toString());
        organizzatoreLabel.setText(nomeOrganizzatore + " " + cognomeOrganizzatore);
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

    private void inizializzaContainerPanel() {
        // Imposta il layout del container per i pannelli dinamici
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
    private void aggiungiEsempiFeedback() {
        // Esempi di feedback come nelle immagini
        aggiungiPannelloFeedback("Team Alpha - Initial Concept", "Oct 20, 2024",
                "Provide feedback to Team Alpha's Initial Concept");

        aggiungiPannelloFeedback("Team Alpha - Prototype Development", "Oct 27, 2024",
                "Provide feedback to Team Alpha's Prototype Development");

        aggiungiPannelloFeedback("Team Alpha - Final Submission", "Oct 28, 2024",
                "Provide feedback to Team Alpha's Final Submission");
        aggiungiPannelloFeedback("Team Alpha - Final Submission", "Oct 28, 2024",
                "Provide feedback to Team Alpha's Final Submission");
        aggiungiPannelloFeedback("Team Alpha - Final Submission", "Oct 28, 2024",
                "Provide feedback to Team Alpha's Final Submission");
    }
    private void aggiornaVisibilitaPulsanti(boolean isRegistrazioneAperte, int numMaxIscritti) {
        String ruolo = controller.getUtente().getTipoUtente();
        registratiBtn.setVisible(false);
        tabbedPane.remove(organizerToolTab);
        tabbedPane.remove(giudiciToolTab);

        switch (ruolo) {
            case "ORGANIZZATORE":
                tabbedPane.add("Tool per organizzatori", organizerToolTab);
                break;
            case "PARTECIPANTE":
                if(isRegistrazioneAperte && controller.getNumeroUtentiRegistrati(hackatonId) < numMaxIscritti) registratiBtn.setVisible(true);
                break;
            case "GIUDICE":
                tabbedPane.add("Tool per giudici", giudiciToolTab);
                break;
            default:
                break;
        }

    }

    private void aggiungiPannelloFeedback(String titolo, String data, String descrizione) {
        // Crea il pannello principale per questo feedback
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BorderLayout());
        feedbackPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 63, 65), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        feedbackPanel.setBackground(Color.WHITE);

        // Imposta dimensione fissa per il pannello
        feedbackPanel.setPreferredSize(new Dimension(0, 160));
        feedbackPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        feedbackPanel.setMinimumSize(new Dimension(0, 160));

        // Pannello superiore con titolo e data
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Icona e titolo
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setBackground(Color.WHITE);

        // Icona documento (simulata con un carattere)
        JLabel iconLabel = new JLabel("📄");
        iconLabel.setForeground(new Color(100, 100, 100));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        JLabel titleLabel = new JLabel(titolo);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font(DIALOG, Font.BOLD, 13));

        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        // Data
        JLabel dateLabel = new JLabel(data);
        dateLabel.setForeground(new Color(100, 100, 100));
        dateLabel.setFont(new Font("Dialog", Font.PLAIN, 11));

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(dateLabel, BorderLayout.EAST);

        // Area di testo per il feedback
        JTextArea feedbackTextArea = new JTextArea(descrizione);
        feedbackTextArea.setBackground(new Color(250, 250, 250));
        feedbackTextArea.setForeground(Color.BLACK);
        feedbackTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        feedbackTextArea.setFont(new Font("Dialog", Font.PLAIN, 12));
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setRows(3);

        // Pannello inferiore con il pulsante
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton submitButton = new JButton("Submit Feedback");
        submitButton.setBackground(new Color(75, 110, 175));
        submitButton.setForeground(Color.WHITE);
        submitButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Dialog", Font.PLAIN, 11));

        // Aggiungi hover effect
        submitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                submitButton.setBackground(new Color(85, 120, 185));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                submitButton.setBackground(new Color(75, 110, 175));
            }
        });

        // Action listener per il pulsante
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackTextArea.getText().trim();
                if (!feedback.isEmpty()) {
                    // Qui puoi gestire l'invio del feedback
                    JOptionPane.showMessageDialog(feedbackPanel,
                            "Feedback inviato per: " + titolo,
                            "Feedback Inviato",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Opzionalmente puoi chiamare un metodo del controller
                    // controller.inviaFeedback(titolo, feedback);
                } else {
                    JOptionPane.showMessageDialog(feedbackPanel,
                            "Inserisci un feedback prima di inviare",
                            "Errore",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        bottomPanel.add(submitButton);

        // Aggiungi tutti i componenti al pannello principale
        feedbackPanel.add(headerPanel, BorderLayout.NORTH);
        feedbackPanel.add(feedbackTextArea, BorderLayout.CENTER);
        feedbackPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Aggiungi il pannello al contenitore principale
        containerPanel.add(feedbackPanel);

        // Aggiungi spazio tra i pannelli
        containerPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Aggiorna la visualizzazione
        containerPanel.revalidate();
        containerPanel.repaint();
    }

    // Metodo per aggiungere un nuovo pannello dinamicamente
    public void aggiungiNuovoFeedback(String titolo, String data, String descrizione) {
        aggiungiPannelloFeedback(titolo, data, descrizione);
    }

    // Metodo per rimuovere tutti i pannelli
    public void rimuoviTuttiFeedback() {
        containerPanel.removeAll();
        containerPanel.revalidate();
        containerPanel.repaint();
    }

    private void inizializzaClassificaTab() {
        // classificaTab = new JPanel(); non ho capito perchè va tolto
        classificaTab.setLayout(new BorderLayout());

        // Titolo
        JLabel titleLabel = new JLabel("Top Teams");
        titleLabel.setFont(new Font(FONT, Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        classificaTab.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale con la classifica
        JPanel teamsPanel = new JPanel();
        teamsPanel.setLayout(new BoxLayout(teamsPanel, BoxLayout.Y_AXIS));
        teamsPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        // Aggiungi i team (puoi ottenere questi dati dal controller)
        aggiungiTeamInClassifica(teamsPanel, "Team Innovators", 95);
        aggiungiTeamInClassifica(teamsPanel, "Code Wizards", 92);
        aggiungiTeamInClassifica(teamsPanel, "Tech Titans", 88);
        aggiungiTeamInClassifica(teamsPanel, "Digital Dynamos", 85);

        // Aggiungi scroll pane se necessario
        JScrollPane scrollPane = new JScrollPane(teamsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        classificaTab.add(scrollPane, BorderLayout.CENTER);

        // Aggiungi la tab al tuo JTabbedPane
        // tabbedPane1.addTab("Classifica", classificaTab);
    }

    private void aggiungiTeamInClassifica(JPanel container, String teamName, int score) {
        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BorderLayout());
        teamPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        teamPanel.setBackground(Color.WHITE);
        teamPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        // Nome team
        JLabel nameLabel = new JLabel(teamName);
        nameLabel.setFont(new Font(FONT, Font.BOLD, 16));

        // Punteggio
        JLabel scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(100, 100, 100));

        // Pannello per allineare il punteggio a destra
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.add(scoreLabel);

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


    private void handleApriRegistraioni(JButton apriRegistrazioniButton) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        apriRegistrazioniButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.apriRegistrazioni(hackatonId, LocalDate.parse(deadlineRegistrazioniField.getText(), formatter));
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

}
