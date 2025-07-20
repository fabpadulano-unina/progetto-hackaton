package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

/**
 * Finestra per caricare i progressi di un team durante un hackathon.
 * Permette di inserire una descrizione e allegare un file documento
 * per aggiornare i giudici sui progressi del lavoro.
 */
public class Progress extends JFrame {
    private JPanel mainPanel;
    private JTextArea descrizioneProgressiTextArea;
    private JButton caricaButton;
    private JList<String> utentiList;
    private JButton salvaButton;
    private JLabel teamLabel;
    private JLabel hackatonLabel;
    private JLabel numeroMembriLabel;
    private Controller controller;
    private final Integer idTeam;
    private File file;

    /**
     * Crea e visualizza la finestra per l'inserimento di un progresso.
     * Inizializza l'interfaccia con i dati del team e dell'hackathon,
     * mostra i membri del team e configura i controlli per il caricamento.
     *
     * @param controller il controller principale dell'applicazione
     * @param hackaton il titolo dell'hackathon
     * @param idTeam l'identificativo del team
     * @param team il nome del team
     * @param partecipantiTeam lista dei membri del team
     */
    public Progress(Controller controller, String hackaton, Integer idTeam, String team, List<String> partecipantiTeam) {
        this.setTitle("Progressi");
        this.setContentPane(mainPanel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;
        this.idTeam = idTeam;

        teamLabel.setText("TEAM: " + team);
        hackatonLabel.setText(hackaton);
        numeroMembriLabel.setText(String.valueOf(partecipantiTeam.size()));
        setUtentiList(partecipantiTeam);

        handleFile();
        handleSalva();
    }

    /**
     * Configura l'event handler per il pulsante di caricamento file.
     * Apre un file chooser per selezionare il documento da allegare
     * e abilita il pulsante di salvataggio quando viene scelto un file.
     */
    private void handleFile() {
        caricaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    // se viene selezionato correttamente un file
                    file = fileChooser.getSelectedFile();
                    salvaButton.setEnabled(true);
                }
            }
        });
    }

    /**
     * Configura l'event handler per il pulsante di salvataggio.
     * Salva il progresso con descrizione e file allegato tramite il controller
     * e chiude la finestra una volta completato il salvataggio.
     */
    private void handleSalva() {
        salvaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addDocumento(idTeam, descrizioneProgressiTextArea.getText(), file);
                Controller.dispose(Progress.this);
            }
        });
    }

    /**
     * Popola la lista degli utenti con i membri del team.
     * Crea un modello per la JList e vi aggiunge tutti i partecipanti.
     *
     * @param partecipantiTeam lista dei nomi dei membri del team
     */
    private void setUtentiList(List<String> partecipantiTeam) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : partecipantiTeam) {
            model.addElement(s);
        }

        utentiList.setModel(model);
    }

}
