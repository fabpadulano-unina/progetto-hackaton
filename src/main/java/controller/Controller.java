package controller;

import gui.*;
import model.Giudice;

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
        new HackatonForm(this);
    }

    public void openTeamForm() {
        new TeamForm(this);
    }

    public void backToHomeFrame(JFrame frame) {
        homeFrame.setVisible(true);
        dispose(frame);
    }

    // non va messo nel codice della gui?
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

    public Object[][] getHackatons() {
        return new Object[][]{
                {"Hackathon 1", "2025-01-01", "2025-01-10", "Dettaglio", 0},
                {"Hackathon 2", "2025-02-15", "2025-02-20", "Dettaglio", 1}
        };
    }

    public void openHackatonDetail(int row) {
        new HackatonDetails(this);

    }

    public void invitaGiudici(List<Giudice> giudici) {}

    public void apriRegisteazioni() {}

    public void chiudiRegistrazioni() {}

    public void openProgress() {
        new Progress(this);
    }
}
