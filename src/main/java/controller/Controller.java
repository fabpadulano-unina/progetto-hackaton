package controller;

import gui.HackatonForm;
import gui.Login;
import gui.RegistrationForm;
import model.Hackaton;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

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

    public void openHackatonForm() {
//        homeFrame.setVisible(false);
        new HackatonForm(this);
    }

    public void backToHomeFrame(JFrame frame) {
        homeFrame.setVisible(true);
        dispose(frame);
    }

    public static void dispose(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }

    public void saveHackaton(String titolo, String sede, LocalDate dataInizio,
                             LocalDate dataFine, int numMaxIscritti, int dimMaxIscritti,
                             List<String> giudici
                             ) {
        //invita giudici
    }

}
