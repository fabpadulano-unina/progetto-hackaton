package controller;

import dao.HackatonDAO;
import dao.UtenteDAO;
import gui.*;
import dao.implementazione.postgres.HackatonImplementazionePostgresDAO;
import dao.implementazione.postgres.UtenteImplementazionePostgresDAO;
import model.*;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private JFrame homeFrame;
    private HackatonDAO hackatonDAO;
    private UtenteDAO utenteDAO;
    private Utente utente;

    public Controller() {
        try {
            hackatonDAO = new HackatonImplementazionePostgresDAO();
            utenteDAO = new UtenteImplementazionePostgresDAO();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella connessione con il db", e);
        }
    }

    public void setHomeFrame(JFrame homeFrame) {
        this.homeFrame = homeFrame;
    }


    public void openRegisterForm(Login login) {
        new RegistrationForm(login, this);
    }

    public void openHackatonForm() {
        if(utente instanceof Organizzatore) {
            new HackatonForm(this, new ArrayList<>());
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
                             LocalDate dataFine, int numMaxIscritti, int dimMaxTeam,
                             List<Giudice> giudici
                             ) {
        if(utente instanceof Organizzatore organizzatore) {
            Integer id = hackatonDAO.addHackaton(titolo, sede, dataInizio, dataFine, numMaxIscritti, dimMaxTeam, utente.getId());
            var hackaton = new Hackaton(id, titolo, sede, dataInizio,dataFine, numMaxIscritti, dimMaxTeam, organizzatore, giudici);

            for (Giudice giudice : giudici) {
                giudice.addHackaton(hackaton);
            }

            invitaGiudici(giudici, id);
        }
    }

    public void saveUser(String nome, String cognome, String email, String password, String tipoUtente) {
        utenteDAO.addUtente(nome, cognome, email, password, tipoUtente);
    }

    public boolean loginUtente(String email, String password) {
        Integer[] id = new Integer[1];
        StringBuilder nome = new StringBuilder();
        StringBuilder cognome = new StringBuilder();
        StringBuilder tipoUtente = new StringBuilder();

        utenteDAO.getUtente(id, email, password, nome, cognome, tipoUtente);

        if (!nome.isEmpty()) {  // se login riuscito
            setUtente(id[0], nome.toString(), cognome.toString(), email, password, tipoUtente.toString());
            return true;
        }

        return false;
    }

    private void setUtente(Integer id, String nome, String cognome, String email, String password, String tipoUtente) {
        switch (tipoUtente) {
            case "ORGANIZZATORE":
                utente = new Organizzatore(id, nome, cognome, email, password);
                break;
            case "PARTECIPANTE":
                utente = new Partecipante(id, nome, cognome, email, password);
                break;
            default:
                utente = new Utente(id, nome, cognome, email, password, tipoUtente);

        }
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

    public void invitaGiudici(List<Giudice> giudici, Integer hackatonId) {
        for (Giudice giudice : giudici) {
            utenteDAO.invitaGiudice(giudice.getId(), hackatonId);
        }
    }

    public void apriRegisteazioni() {}

    public void chiudiRegistrazioni() {}

    public void openHomeFrame() {
        new Home(this);
    }

    public void openProgress() {
        new Progress(this);
    }

    public Utente getUtente() {
        return utente;
    }
}
