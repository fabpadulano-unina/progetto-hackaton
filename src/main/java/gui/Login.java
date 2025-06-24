package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Login extends JFrame {
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton loginButton;
    private JPanel panel;
    private JLabel registerLabel;
    private JLabel credentialsErrorLabel;
    private Controller controller;

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

    private void handleClicks() {
        handleLoginBtnClick();
        handleRegisterLabelClick();
    }

    public static void main(String[] args) {
        new Login();
    }

    private void handleLoginBtnClick() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(controller.loginUtente(emailInput.getText(), Arrays.toString(passwordInput.getPassword()))) {
                    controller.openHomeFrame();
                    Login.this.dispose();
                } else {
                    credentialsErrorLabel.setVisible(true);
                }
            }
        });
    }



    private void handleRegisterLabelClick() {
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    controller.openRegisterForm(Login.this);
            }
        });
    }

    public void setEmailInput(String email) {
        this.emailInput.setText(email);
    }

    public void setPasswordInput(String pw) {
        this.passwordInput.setText(pw);
    }

}
