package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TeamForm extends JFrame{
    private JPanel panel;
    private JTextField nomeInput;
    private JPanel mainPanel;
    private JButton addBtn;
    private Controller controller;

    public TeamForm( Controller controller) {
        this.setTitle("Crea Team");
        this.setContentPane(panel);
        this.pack();
        this.setVisible(true);

        this.controller = controller;

        handleClicks();
    }

    private void handleClicks() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo controlloer.saveTeam(.......)
                Controller.dispose(TeamForm.this);
            }
        });
    }
}
