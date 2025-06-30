package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamForm extends JFrame{
    private JPanel panel;
    private JTextField nomeInput;
    private JButton addBtn;
    private Controller controller;
    private String titoloHackaton;

    public TeamForm(String titoloHackaton, Controller controller) {
        this.setTitle("Crea Team");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);

        this.titoloHackaton = titoloHackaton;
        this.controller = controller;

        handleClicks();
    }

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
