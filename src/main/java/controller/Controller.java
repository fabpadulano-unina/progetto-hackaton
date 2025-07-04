package controller;

import dao.HackatonDAO;
import dao.TeamDAO;
import dao.UtenteDAO;
import dao.implementazione.postgres.TeamImplementazionePostgresDAO;
import gui.*;
import dao.implementazione.postgres.HackatonImplementazionePostgresDAO;
import dao.implementazione.postgres.UtenteImplementazionePostgresDAO;
import model.*;

import javax.swing.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private Home homeFrame;
    private HackatonDAO hackatonDAO;
    private UtenteDAO utenteDAO;
    private TeamDAO teamDAO;
    private Utente utente;
    private List<Hackaton> hackatons = new ArrayList<>();

    public Controller() {
        try {
            hackatonDAO = new HackatonImplementazionePostgresDAO();
            utenteDAO = new UtenteImplementazionePostgresDAO();
            teamDAO = new TeamImplementazionePostgresDAO();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella connessione con il db", e);
        }
    }

    public void setHomeFrame(Home homeFrame) {
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

    public void openTeamForm(String titoloHackaton) {
        new TeamForm(titoloHackaton, this);
    }

    public void backToHomeFrame(JFrame frame) {
        //riaggiorno la tabella degli hackaton
        homeFrame.setHackatonsTable();

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
                //gli hackaton a cui l'utente è registrato
                List<Hackaton> hackatonsUtente = new ArrayList<>();
                for (Hackaton h : getListaHackaton()) {
                    if (hackatonDAO.isUtenteRegistrato(id, h.getId())) {
                        hackatonsUtente.add(h);
                    }
                }

                utente = new Partecipante(id, nome, cognome, email, password, hackatonsUtente);
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
        new HackatonDetails(
                this,
                hackaton.getId(),
                hackaton.getTitolo(),
                hackaton.getSede(),
                hackaton.getDataInizio(),
                hackaton.getDataFine(),
                hackaton.getNumMaxIscritti(),
                hackaton.getDimMaxTeam(),
                hackaton.isRegistrazioniAperte(),
                hackaton.getDeadline(),
                hackaton.getDescrizioneProblema(),
                hackaton.getOrganizzatore().getNome(),
                hackaton.getOrganizzatore().getCognome(),
                getGiudici(hackaton.getGiudici()));
    }

    public void invitaGiudici(List<Giudice> giudici, Integer hackatonId) {
        for (Giudice giudice : giudici) {
            utenteDAO.invitaGiudice(giudice.getId(), hackatonId);
        }
    }

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

        List<Integer> ids = new ArrayList<>();
        List<String> titoli = new ArrayList<>();
        List<String> sedi = new ArrayList<>();
        List<Date> dateInizio = new ArrayList<>();
        List<Date> dateFine = new ArrayList<>();
        List<Integer> numMaxIscritti = new ArrayList<>();
        List<Integer> dimMaxTeam = new ArrayList<>();
        List<Boolean> registrazioniAperte = new ArrayList<>();
        List<Date> deadlines = new ArrayList<>();
        List<String> descrizioniProblema = new ArrayList<>();

        List<String> nomiOrganizzatori = new ArrayList<>();
        List<String> cognomiOrganizzatori = new ArrayList<>();

        List<Integer> idGiudici = new ArrayList<>();
        List<String> nomiGiudici = new ArrayList<>();
        List<String> cognomiGiudici = new ArrayList<>();
        List<String> emailGiudici = new ArrayList<>();

        List<Integer> idHackatonInviti = new ArrayList<>();
        List<Integer> idGiudiciInvitati = new ArrayList<>();

        hackatonDAO.getHackatons(ids, titoli, sedi, dateInizio, dateFine, numMaxIscritti, dimMaxTeam, registrazioniAperte, deadlines, descrizioniProblema, nomiOrganizzatori, cognomiOrganizzatori);
        utenteDAO.leggiGiudici(idGiudici, nomiGiudici, cognomiGiudici, emailGiudici);
        hackatonDAO.leggiInvitiGiudice(idHackatonInviti, idGiudiciInvitati);

        List<Hackaton> lista = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Organizzatore organizzatore = new Organizzatore(
                    null,
                    nomiOrganizzatori.get(i),
                    cognomiOrganizzatori.get(i),
                    null,
                    null
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
                    dateInizio.get(i).toLocalDate(),
                    dateFine.get(i).toLocalDate(),
                    numMaxIscritti.get(i),
                    dimMaxTeam.get(i),
                    organizzatore,
                    giudici
            );
            h.setRegistrazioniAperte(registrazioniAperte.get(i));
            Date deadline = deadlines.get(i);
            if(deadline != null) h.setDeadline(deadline.toLocalDate());
            h.setDescrizioneProblema(descrizioniProblema.get(i));

            lista.add(h);
        }

        return lista;
    }


    public void apriRegistrazioni(Integer idHackaton, LocalDate deadline) {
        hackatonDAO.apriRegistrazioni(idHackaton, deadline);
    }

    public void registraUtenteHackaton(Integer idHackaton) {
        hackatonDAO.registraUtente(this.utente.getId(), idHackaton);
    }

    public boolean isUtenteRegistrato(Integer hackatonId) {
        return hackatonDAO.isUtenteRegistrato(this.utente.getId(), hackatonId);
    }

    public int getNumeroUtentiRegistrati(Integer idHackaton) {
        return hackatonDAO.getNumeroUtentiRegistrati(idHackaton);
    }

    public void setDescrizioneProblema(Integer hackatonId, String descirizione) {
        hackatonDAO.updateDescrizioneProblema(hackatonId, descirizione);
    }

    public List<String> getHackatonsNamesForCombobox() {
        List<String> hackatonsNames = new ArrayList<>();

        //tutti gli hackaton per cui è possibile creare un team (quindi se le registrazioni sono ancora aperte)
        for(Hackaton hackaton : getPartecipanteHackatonList()) {
            if(hackaton.isRegistrazioniAperte()) {
                hackatonsNames.add(hackaton.getTitolo());
            }
        }

        return hackatonsNames;
    }



    public void addTeam(String titoloHackaton, String nomeTeam) {
        Integer idHackaton = getIdHackatonFromName(titoloHackaton);
        Integer idTeam = teamDAO.addTeam(idHackaton, nomeTeam);
        addPartecipanteAlTeam(idTeam, idHackaton);
    }

    private void addPartecipanteAlTeam(Integer idTeam, Integer idHackaton) {
        teamDAO.deletePartecipanteNelTeam(utente.getId(), idHackaton);
        teamDAO.addPartecipanteAlTeam(utente.getId(), idTeam, idHackaton);
    }

    public void addPartecipanteAlTeam(String nomeTeam, String nomeHackaton) {
        Integer idHackaton = getIdHackatonFromName(nomeHackaton);
        addPartecipanteAlTeam(getIdTeamFromName(nomeTeam, idHackaton), idHackaton);
    }

    private Integer getIdHackatonFromName(String titoloHackaton) {
        Integer idHackaton = null;
        for(Hackaton h : getPartecipanteHackatonList()) {
            if(h.getTitolo().equals(titoloHackaton)) {
                idHackaton = h.getId();
            }
        }
        return idHackaton;
    }


    private Integer getIdTeamFromName(String nomeTeam, Integer idHackaton) {
        for(Team team : getTeamByHackaton(idHackaton)) {
            if(team.getNome().equals(nomeTeam)) {
                return team.getId();
            }
        }

        return null;
    }

    private List<Hackaton> getPartecipanteHackatonList() {
        return ((Partecipante) utente).getHackatons();
    }

    //per la combobox, lista di team a cui l'utente può unirsi
    public List<String> getTeamByHackaton(String titoloHackaton) {
        Integer idHackaton = getIdHackatonFromName(titoloHackaton);
        List<String> teamNames = new ArrayList<>();
        if(idHackaton == null) return teamNames;

        List<Team> teamByHackaton = getTeamByHackaton(idHackaton);
        for(Team team : teamByHackaton) {
            if(!teamDAO.isPartecipanteInTeam(team.getId(), utente.getId())) {
                teamNames.add(team.getNome());
            }
        }
        return teamNames;
    }

    //per la combobox, lista di team a cui l'utente è unito
    public List<String> getTeamByPartecipanteCb() {
        List<Team> teamByHackaton = getTeamByPartecipante();
        List<String> teamNames = new ArrayList<>();
        for(Team team : teamByHackaton) {
            teamNames.add(team.getNome());
        }
        return teamNames;
    }


    private List<Team> getTeamByHackaton(Integer idHackaton) {
        List<Integer> ids = new ArrayList<>();
        List<String> nomiTeam = new ArrayList<>();
        teamDAO.getTeamByHackaton(idHackaton, ids, nomiTeam);

        List<Team> teams = new ArrayList<>();
        popolaListaTeam(ids, nomiTeam, teams);

        return teams;
    }

    private List<Team> getTeamByPartecipante() {
        List<Integer> ids = new ArrayList<>();
        List<String> nomiTeam = new ArrayList<>();
        teamDAO.getTeamByPartecipante(utente.getId(), ids, nomiTeam);

        List<Team> teams = new ArrayList<>();
        popolaListaTeam(ids, nomiTeam, teams);

        return teams;
    }

    private void popolaListaTeam(List<Integer> ids, List<String> nomiTeam, List<Team> teams) {
        for (int i = 0; i < ids.size(); i++) {
            Team team = new Team(ids.get(i), nomiTeam.get(i));
            teams.add(team);
            team.getPartecipanti().add(((Partecipante) utente));
        }
    }

}
