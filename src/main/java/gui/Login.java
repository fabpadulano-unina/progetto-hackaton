package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Finestra di login dell'applicazione hackaton.
 * Gestisce l'autenticazione e reigstrazione dell'utente.
 */
public class Login extends JFrame {
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton loginButton;
    private JPanel panel;
    private JLabel registerLabel;
    private JLabel credentialsErrorLabel;
    private Controller controller;

    /**
     * Costruttore. Inizializza l'interfaccia e configura gli event listener.
     */
    public Login() {
        this.setTitle("Login");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        credentialsErrorLabel.setVisible(false);
        this.controller = new Controller();

        handleClicks();
    }

    /**
     * Configura tutti gli event listener.
     */
    private void handleClicks() {
        handleLoginBtnClick();
        handleRegisterLabelClick();
    }

    /**
     * Punto di ingresso dell'applicazione.
     */
    public static void main(String[] args) {
        new Login();
    }

    /**
     * Gestisce il click del pulsante login.
     * Autentica l'utente e apre la home in caso di successo.
     */
    private void handleLoginBtnClick() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.loginUtente(emailInput.getText(), new String(passwordInput.getPassword()))) {
                    controller.openHomeFrame();
                    Login.this.dispose();
                } else {
                    credentialsErrorLabel.setVisible(true);
                }
            }
        });
    }


    /**
     * Gestisce il click della label registrazione.
     * Apre la finestra di registrazione.
     */
    private void handleRegisterLabelClick() {
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    controller.openRegisterForm(Login.this);
            }
        });
    }

    /**
     * Imposta l'email nel campo di input.
     *
     * @param email l'email da impostare
     */
    public void setEmailInput(String email) {
        this.emailInput.setText(email);
    }

    /**
     * Imposta la password nel campo di input.
     *
     * @param pw la password da impostare
     */
    public void setPasswordInput(String pw) {
        this.passwordInput.setText(pw);
    }

}
