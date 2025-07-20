package gui;

import controller.Controller;
import model.Documento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pannello per la gestione dei feedback sui documenti durante l'hackathon.
 * Permette ai giudici di visualizzare i documenti caricati dai team
 * e di inserire commenti e valutazioni sui progressi.
 */
public class FeedbackPanel {
    public static final String DIALOG = "Dialog";

    private JPanel containerPanel;
    private Controller controller;

    /**
     * Costruttore del pannello feedback per i giudici.
     * Inizializza il pannello e carica tutti i documenti dell'hackathon
     * per permettere ai giudici di valutarli e commentarli.
     *
     * @param controller il controller per gestire le operazioni
     * @param documenti la lista dei documenti da valutare
     */
    public FeedbackPanel(Controller controller, List<Documento> documenti) {
        this.controller = controller;
        inizializzaContainerPanel();
        aggiungiProgressi(documenti);
    }


    public JPanel getContainerPanel() {
        return containerPanel;
    }

    /**
     * Aggiunge un pannello di feedback per ogni documento nell'hackathon.
     * Crea un'interfaccia separata per ogni documento caricato dai team
     * permettendo ai giudici di valutarli singolarmente.
     *
     * @param documenti la lista dei documenti per cui creare i pannelli
     */
    private void aggiungiProgressi(List<Documento> documenti) {
        for(Documento documento : documenti) {
            aggiungiPannelloFeedback(documento);
        }
    }

    /**
     * Inizializza il pannello contenitore per i feedback dei giudici.
     * Configura il layout verticale per disporre i feedback uno sotto l'altro
     * e rimuove il bordo predefinito per un aspetto più pulito.
     */
    private void inizializzaContainerPanel() {
        if(getContainerPanel() != null) {
            // container dove ogni feedback andrà uno sotto l'altro
            getContainerPanel().setLayout(new BoxLayout(getContainerPanel(), BoxLayout.Y_AXIS));
            // rimozione del bordo nero di default
            getContainerPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        }
    }

    private void aggiungiPannelloFeedback(Documento documento) {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBackground(Color.WHITE);

        feedbackPanel.setPreferredSize(new Dimension(0, 160));

        JPanel intestazionePanel = getIntestazione(documento.getTeam().getNome(), documento.getDescrizione() );

        JTextArea feedbackTextArea = getTextArea();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton jButton = setAndHandleInviaBtn(documento.getId(), feedbackTextArea);
        bottomPanel.add(jButton);


        feedbackPanel.add(intestazionePanel, BorderLayout.NORTH);
        feedbackPanel.add(feedbackTextArea, BorderLayout.CENTER);
        feedbackPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Linea separatrice
        feedbackPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        getContainerPanel().add(feedbackPanel);
    }

    /**
     * Crea e configura il pulsante per inviare feedback sui documenti.
     * Imposta lo stile del pulsante e gestisce l'invio del feedback,
     * disabilitando il pulsante dopo l'invio per evitare invii multipli.
     *
     * @param idDocumento l'ID del documento per cui inviare il feedback
     * @param feedbackTextArea l'area di testo contenente il feedback
     * @return il pulsante configurato per l'invio del feedback
     */
    private JButton setAndHandleInviaBtn(Integer idDocumento, JTextArea feedbackTextArea) {
        JButton submitButton = new JButton("Invia Feedback");
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(8, 16, 8, 16)  // padding
        ));
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // cursore a manina
        submitButton.setPreferredSize(new Dimension(140, 35));


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackTextArea.getText().trim();
                if (!feedback.isEmpty()) {
                    submitButton.setEnabled(false);
                    controller.inserisciFeedbackGiudice(idDocumento, feedback);
                }
            }
        });

        return submitButton;
    }

    private static JTextArea getTextArea() {
        JTextArea feedbackTextArea = new JTextArea();
        feedbackTextArea.setBackground(new Color(250, 250, 250));
        feedbackTextArea.setForeground(Color.BLACK);
        feedbackTextArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        feedbackTextArea.setFont(new Font(DIALOG, Font.PLAIN, 12));
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        return feedbackTextArea;
    }

    private JPanel getIntestazione(String nomeTeam, String descrizione) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel("📄");
        iconLabel.setForeground(new Color(100, 100, 100));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        JLabel teamLabel = new JLabel(nomeTeam);
        teamLabel.setForeground(Color.BLACK);
        teamLabel.setFont(new Font(DIALOG, Font.BOLD, 14));

        JLabel descriptionLabel = new JLabel(" - " + descrizione);
        descriptionLabel.setForeground(new Color(80, 80, 80));
        descriptionLabel.setFont(new Font(DIALOG, Font.PLAIN, 13));

        titlePanel.add(iconLabel);
        titlePanel.add(teamLabel);
        titlePanel.add(descriptionLabel);

        headerPanel.add(titlePanel);
        return headerPanel;
    }




}
