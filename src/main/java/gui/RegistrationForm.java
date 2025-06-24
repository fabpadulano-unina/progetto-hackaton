package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegistrationForm extends JFrame {
    private JPanel panel;
    private JTextField nomeInput;
    private JTextField cognomeinput;
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton registerBtn;

    private Login loginFrame;
    private Controller controller;

    public RegistrationForm(Login loginFrame, Controller controller) {
        this.setTitle("Registra");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.loginFrame = loginFrame;
        this.controller = controller;

        handleClicks(loginFrame);
    }

    private void handleClicks(JFrame homeFrame) {
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO HARDCODATO ORGANIZZATORE
                String password = Arrays.toString(passwordInput.getPassword());
                controller.saveUser(nomeInput.getText(), cognomeinput.getText(), emailInput.getText(), password, "ORGANIZZATORE");
                loginFrame.setEmailInput(emailInput.getText());
                loginFrame.setPasswordInput(password);
                Controller.dispose(RegistrationForm.this);
            }
        });
    }


}
