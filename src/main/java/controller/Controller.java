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
    private List<Hackaton> hackatons = new ArrayList<>();

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
            new HackatonForm(this, getListaGiudici());
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
        List<Hackaton> listaHackaton = getListaHackaton();
        Object[][] matrice = new Object[listaHackaton.size()][5];

        for (int i = 0; i < listaHackaton.size(); i++) {
            Hackaton h = listaHackaton.get(i);
            matrice[i][0] = h.getTitolo();
            matrice[i][1] = h.getDataInizio().toString();
            matrice[i][2] = h.getDataFine().toString();
            matrice[i][3] = "Dettaglio";
            matrice[i][4] = h.getId();
        }

        return matrice;
    }

    public Object[][] getGiudici(List<Giudice> giudici) {
        Object[][] matrice = new Object[giudici.size()][3];

        for (int i = 0; i < giudici.size(); i++) {
            Giudice g = giudici.get(i);
            matrice[i][0] = g.getNome();
            matrice[i][1] = g.getCognome();
            matrice[i][2] = g.getEmail();
        }

        return matrice;
    }



    public void openHackatonDetail(int row) {
        Hackaton hackaton = this.getListaHackaton().get(row);
        new HackatonDetails(this, getGiudici(hackaton.getGiudici()));
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

    public List<Giudice> getListaGiudici() {
        List<Integer> ids = new ArrayList<>();
        List<String> nomi = new ArrayList<>();
        List<String> cognomi = new ArrayList<>();
        List<String> email = new ArrayList<>();

        utenteDAO.leggiGiudici(ids, nomi, cognomi, email);

        List<Giudice> giudici = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Giudice g = new Giudice(
                    ids.get(i),
                    nomi.get(i),
                    cognomi.get(i),
                    email.get(i),
                    null
            );
            giudici.add(g);
        }

        return giudici;
    }

    private List<Hackaton> getListaHackaton() {
        if(!hackatons.isEmpty()) return hackatons;

        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<String> titoli = new ArrayList<>();
        ArrayList<String> sedi = new ArrayList<>();
        ArrayList<LocalDate> dateInizio = new ArrayList<>();
        ArrayList<LocalDate> dateFine = new ArrayList<>();
        ArrayList<Integer> numMaxIscritti = new ArrayList<>();
        ArrayList<Integer> dimMaxTeam = new ArrayList<>();
        ArrayList<Integer> idOrganizzatori = new ArrayList<>();

        ArrayList<Integer> idGiudici = new ArrayList<>();
        ArrayList<String> nomiGiudici = new ArrayList<>();
        ArrayList<String> cognomiGiudici = new ArrayList<>();
        ArrayList<String> emailGiudici = new ArrayList<>();

        ArrayList<Integer> idHackatonInviti = new ArrayList<>();
        ArrayList<Integer> idGiudiciInvitati = new ArrayList<>();

        hackatonDAO.getHackatons(ids, titoli, sedi, dateInizio, dateFine, numMaxIscritti, dimMaxTeam, idOrganizzatori);
        utenteDAO.leggiGiudici(idGiudici, nomiGiudici, cognomiGiudici, emailGiudici);
        hackatonDAO.leggiInvitiGiudice(idHackatonInviti, idGiudiciInvitati);

        List<Hackaton> lista = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Organizzatore organizzatore = new Organizzatore(
                    idOrganizzatori.get(i),
                    "nome",
                    "cognome",
                    "email",
                    "password"
            );

            List<Giudice> giudici = new ArrayList<>();
            for (int j = 0; j < idHackatonInviti.size(); j++) {
                if (idHackatonInviti.get(j).equals(ids.get(i))) {
                    int idG = idGiudiciInvitati.get(j);
                    int indexG = idGiudici.indexOf(idG);
                    if (indexG != -1) {
                        Giudice g = new Giudice(
                                idGiudici.get(indexG),
                                nomiGiudici.get(indexG),
                                cognomiGiudici.get(indexG),
                                emailGiudici.get(indexG),
                                null
                        );
                        giudici.add(g);
                    }
                }
            }

            Hackaton h = new Hackaton(
                    ids.get(i),
                    titoli.get(i),
                    sedi.get(i),
                    dateInizio.get(i),
                    dateFine.get(i),
                    numMaxIscritti.get(i),
                    dimMaxTeam.get(i),
                    organizzatore,
                    giudici
            );
            lista.add(h);
        }

        return lista;
    }

}
