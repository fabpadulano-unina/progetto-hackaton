package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedbackPanel  {
    public static final String DIALOG = "Dialog";

    private JPanel containerPanel;

    public FeedbackPanel() {
        inizializzaContainerPanel();
    }

    private void inizializzaContainerPanel() {
        if(containerPanel != null) {
            // container dove ogni feedback andrà uno sotto l'altro (Y-AXIS)
            containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
            // rimozione del bordo nero di default
            containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            aggiungiEsempiFeedback();
        }
    }
    private void aggiungiEsempiFeedback() {
        aggiungiPannelloFeedback("Team Alpha - Initial Concept");
        aggiungiPannelloFeedback("Team Alpha - Prototype Development");
        aggiungiPannelloFeedback("Team Alpha - Final Submission");
    }

    private void aggiungiPannelloFeedback(String titolo) {
        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBackground(Color.WHITE);

        feedbackPanel.setPreferredSize(new Dimension(0, 160));

        JPanel intestazionePanel = getIntestazione(titolo);

        JTextArea feedbackTextArea = getTextArea();

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton jButton = setAndHandleInviaBtn(titolo, feedbackTextArea);
        bottomPanel.add(jButton);


        feedbackPanel.add(intestazionePanel, BorderLayout.NORTH);
        feedbackPanel.add(feedbackTextArea, BorderLayout.CENTER);
        feedbackPanel.add(bottomPanel, BorderLayout.SOUTH);

        containerPanel.add(feedbackPanel);
    }

    private JButton setAndHandleInviaBtn(String titolo, JTextArea feedbackTextArea) {
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

    private JPanel getIntestazione(String titolo) {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);

        // Icona e titolo
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel("📄");
        iconLabel.setForeground(new Color(100, 100, 100));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

        JLabel titleLabel = new JLabel(titolo);
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font(DIALOG, Font.BOLD, 13));

        titlePanel.add(iconLabel);
        titlePanel.add(titleLabel);

        headerPanel.add(titlePanel, BorderLayout.WEST);
        return headerPanel;
    }


}
