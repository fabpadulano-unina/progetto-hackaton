package gui;

import model.Documento;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FeedbackPanel {
    public static final String DIALOG = "Dialog";

    private JPanel containerPanel;

    public FeedbackPanel(List<Documento> documentoList) {
        inizializzaContainerPanel();
        aggiungiProgressi(documentoList);
    }

    public JPanel getContainerPanel() {
        return containerPanel;
    }

    private void aggiungiProgressi(List<Documento> documentoList) {
        for(Documento documento : documentoList) {
            aggiungiPannelloFeedback(documento);
        }
    }

    private void inizializzaContainerPanel() {
        if(getContainerPanel() != null) {
            // container dove ogni feedback andrà uno sotto l'altro (Y-AXIS)
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

    private JButton setAndHandleInviaBtn(Integer idDocumento, JTextArea feedbackTextArea) {
        JButton submitButton = new JButton("Invia Feedback");
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));


        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackTextArea.getText().trim();
                if (!feedback.isEmpty()) {
                    submitButton.setEnabled(false);
                    // TODO controller.inviaFeedback(titolo, feedback);
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
