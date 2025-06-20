package controller;

import dao.HackatonDAO;
import dao.UtenteDAO;
import gui.*;
import implementazionePostgresDAO.HackatonImplementazionePostgresDAO;
import implementazionePostgresDAO.UtenteImplementazionePostgresDAO;
import model.Giudice;
import model.Organizzatore;
import model.Utente;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Controller {
    private JFrame homeFrame;
    private HackatonDAO hackatonDAO;
    private UtenteDAO utenteDAO;
    private Utente utente;

    public Controller(JFrame homeFrame) {
        this.homeFrame = homeFrame;
        try {
            hackatonDAO = new HackatonImplementazionePostgresDAO();
            utenteDAO = new UtenteImplementazionePostgresDAO();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        if(utente instanceof Organizzatore) {
            new HackatonForm(this);
        }
    }

    public void openTeamForm() {
        new TeamForm(this);
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
        hackatonDAO.addHackaton(titolo, sede, dataInizio, dataFine, numMaxIscritti, dimMaxIscritti, utente.getId());
        //todo invita giudici
    }

    public void saveUser(String nome, String cognome, String email) {
        utente = new Utente(nome, cognome, email);
        utenteDAO.addUtente(nome, cognome, email);
    }

    public Object[][] getHackatons() {
        return new Object[][]{
                {"Hackathon 1", "2025-01-01", "2025-01-10", "Dettaglio", 0},
                {"Hackathon 2", "2025-02-15", "2025-02-20", "Dettaglio", 1}
        };
    }
    public Object[][] getGiudici() {
        return new Object[][]{
                {"Antonio", "Esposito", "antesp@gmail.com"},
                {"Ciro", "Esposito", "cirosp@gmail.com"},
        };
    }




    public void openHackatonDetail() {
        new HackatonDetails(this);

    }

    public void invitaGiudici(List<Giudice> giudici) {}

    public void apriRegisteazioni() {}

    public void chiudiRegistrazioni() {}

    public void openProgress() {
        new Progress(this);
    }
}
