package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationForm extends JFrame {
    private JPanel panel;
    private JTextField nomeInput;
    private JTextField cognomeinput;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton registerBtn;
    private JList<String> tipoUtenteSelect;

    private Login loginFrame;
    private Controller controller;

    public RegistrationForm(Login loginFrame, Controller controller) {
        this.setTitle("Registra");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.loginFrame = loginFrame;
        this.controller = controller;

        handleClicks();

        String[] tipiUtente = { "ORGANIZZATORE", "PARTECIPANTE", "GIUDICE"};
        tipoUtenteSelect.setVisibleRowCount(tipiUtente.length);
        tipoUtenteSelect.setListData(tipiUtente);
    }

    private void handleClicks() {
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = new String(passwordInput.getPassword());
                controller.saveUser(nomeInput.getText(), cognomeinput.getText(), emailInput.getText(), password, tipoUtenteSelect.getSelectedValue());
                loginFrame.setEmailInput(emailInput.getText());
                loginFrame.setPasswordInput(password);
                Controller.dispose(RegistrationForm.this);
            }
        });
    }


}
