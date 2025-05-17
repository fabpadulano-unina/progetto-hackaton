package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login {
    public JFrame frame;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JPanel panel;
    private JLabel registerLabel;
    private JFrame homeFrame;
    private Controller controller;

    public Login(JFrame homeFrame, Controller controller) {
        this.frame = new JFrame("Login");
        this.frame.setContentPane(panel);
        frame.setContentPane(frame.getContentPane());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        this.homeFrame = homeFrame;
        this.controller = controller;

        handleClicks(homeFrame);
    }

    private void handleClicks(JFrame homeFrame) {
        handleLoginBtnClick(homeFrame);
        handleRegisterLabelClick();
    }

    public JFrame getFrame() {
        return frame;
    }

    private void handleLoginBtnClick(JFrame homeFrame) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.backToHomeFrame(frame);
            }
        });
    }



    private void handleRegisterLabelClick() {
        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                backToHomeFrame();
            }
        });
    }


}
