package controller;

import gui.Login;

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

    public void backToHomeFrame(JFrame frame) {
        homeFrame.setVisible(true);
        frame.setVisible(false);
        frame.dispose();
    }

}
