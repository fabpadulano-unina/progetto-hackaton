package controller;

import gui.Login;
import gui.RegistrationForm;

import javax.swing.*;

public class Controller {
    JFrame homeFrame;

    public Controller(JFrame homeFrame) {
        this.homeFrame = homeFrame;
    }

    public void openLoginForm() {
        homeFrame.setVisible(false);
        new Login(homeFrame, this);
    }


    public void openRegisterForm(Login login) {
        homeFrame.setVisible(false);
        new RegistrationForm(login, this);
    }

    public void backToHomeFrame(JFrame frame) {
        homeFrame.setVisible(true);
        dispose(frame);
    }

    public static void dispose(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }

}
