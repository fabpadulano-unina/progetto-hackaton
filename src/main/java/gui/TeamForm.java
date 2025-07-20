package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra di dialogo per la creazione di un nuovo team.
 * Permette all'utente di inserire il nome del team
 * e crearlo per un hackathon specifico.
 */
public class TeamForm extends JFrame{
    private JPanel panel;
    private JTextField nomeInput;
    private JButton addBtn;
    private JLabel hackatonLabel;
    private Controller controller;
    private String titoloHackaton;

    /**
     * Costruttore del form di creazione team.
     * Inizializza la finestra, imposta il titolo dell'hackathon
     * e configura i gestori di eventi per i pulsanti.
     *
     * @param titoloHackaton il titolo dell'hackathon per cui creare il team
     * @param controller il controller principale dell'applicazione
     */
    public TeamForm(String titoloHackaton, Controller controller) {
        this.setTitle("Crea Team");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);

        this.titoloHackaton = titoloHackaton;
        hackatonLabel.setText("HACKATON: " + titoloHackaton);
        this.controller = controller;

        handleClicks();
    }


    /**
     * Configura i gestori di eventi per i pulsanti del form.
     * Imposta il listener per il pulsante di creazione team
     * che salva il nuovo team e chiude la finestra.
     */
    private void handleClicks() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.addTeam(titoloHackaton, nomeInput.getText());
                Controller.dispose(TeamForm.this);
            }
        });
    }
}
