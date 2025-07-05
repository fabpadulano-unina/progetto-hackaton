package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FeedbackPanel extends JPanel {
    public static final String DIALOG = "Dialog";

    private JPanel containerPanel;

    public FeedbackPanel() {
        inizializzaContainerPanel();
    }

    private void inizializzaContainerPanel() {
        // Imposta il layout del container per i pannelli dinamici
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        aggiungiEsempiFeedback();
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
        dateLabel.setFont(new Font(DIALOG, Font.PLAIN, 11));

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
        feedbackTextArea.setFont(new Font(DIALOG, Font.PLAIN, 12));
        feedbackTextArea.setLineWrap(true);
        feedbackTextArea.setWrapStyleWord(true);
        feedbackTextArea.setRows(3);

        // Pannello inferiore con il pulsante
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton submitButton = new JButton("Submit Feedback");
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.BLACK);
        submitButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font(DIALOG, Font.PLAIN, 11));

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


}
