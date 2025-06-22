package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame {
    private JTextField emailInput;
    private JPasswordField passwordInput;
    private JButton loginButton;
    private JPanel panel;
    private JLabel registerLabel;
    private JPanel panelCredentialsError;
    private JFrame homeFrame;
    private Controller controller;

    public Login(JFrame homeFrame, Controller controller) {
        this.setTitle("Login");
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.homeFrame = homeFrame;
        this.controller = controller;

        handleClicks(homeFrame);
    }

    private void handleClicks(JFrame homeFrame) {
        handleLoginBtnClick(homeFrame);
        handleRegisterLabelClick();
    }



    private void handleLoginBtnClick(JFrame homeFrame) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.backToHomeFrame(Login.this);
            }
        });
    }



    private void handleRegisterLabelClick() {
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    controller.openRegisterForm(Login.this);
//                backToHomeFrame();
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
