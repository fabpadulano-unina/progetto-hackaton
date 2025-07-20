package controller;

import dao.DocumentoDAO;
import dao.HackatonDAO;
import dao.TeamDAO;
import dao.UtenteDAO;
import dao.implementazione.postgres.DocumentoImplementazionePostgresDAO;
import dao.implementazione.postgres.TeamImplementazionePostgresDAO;
import gui.*;
import dao.implementazione.postgres.HackatonImplementazionePostgresDAO;
import dao.implementazione.postgres.UtenteImplementazionePostgresDAO;
import model.*;

import javax.swing.*;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller che gestisce la logica dell'applicazione.
 * Coordina le interazioni tra interfaccia utente e database.
 */
public class Controller {
    private Home homeFrame;
    private HackatonDAO hackatonDAO;
    private UtenteDAO utenteDAO;
    private TeamDAO teamDAO;
    private DocumentoDAO documentoDAO;
    private Utente utente;
    private final List<Hackaton> hackatons = new ArrayList<>();

    /**
     * Costruttore del controller principale.
     * Inizializza tutti i DAO necessari per l'accesso ai dati
     * e stabilisce la connessione con il database PostgreSQL.
     *
     * @throws SQLException se si verifica un errore nella connessione al database
     */
    public Controller() {
        try {
            hackatonDAO = new HackatonImplementazionePostgresDAO();
            utenteDAO = new UtenteImplementazionePostgresDAO();
            teamDAO = new TeamImplementazionePostgresDAO();
            documentoDAO = new DocumentoImplementazionePostgresDAO();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella connessione con il db", e);
        }
    }


    public void setHomeFrame(Home homeFrame) {
        this.homeFrame = homeFrame;
    }


    /**
     * Apre la finestra di registrazione.
     *
     * @param login finestra di login di riferimento
     */
    public void openRegisterForm(Login login) {
        new RegistrationForm(login, this);
    }

    /**
     * Apre il form per creare un nuovo hackathon.
     * Controlla che l'utente sia un organizzatore e passa la lista
     * dei giudici disponibili per la selezione.
     */
    public void openHackatonForm() {
        if(utente instanceof Organizzatore) {
            new HackatonForm(this, getListaGiudici());
        }
    }

    /**
     * Apre il form per la creazione di un nuovo team.
     * Crea e visualizza una finestra di dialogo per permettere
     * all'utente di creare un team per l'hackathon specificato.
     *
     * @param titoloHackaton il titolo dell'hackathon per cui creare il team
     */
    public void openTeamForm(String titoloHackaton) {
        new TeamForm(titoloHackaton, this);
    }

    /**
     * Back to home frame.
     *
     * @param frame the frame
     */
    public void backToHomeFrame(JFrame frame) {
        //riaggiorno la tabella degli hackaton
        homeFrame.setHackatonsTable();

        homeFrame.setVisible(true);
        dispose(frame);
    }


    /**
     * Chiude e libera le risorse di una finestra JFrame.
     * @param frame la finestra da chiudere e liberare
     */
    public static void dispose(JFrame frame) {
        frame.setVisible(false);
        frame.dispose();
    }

    /**
     * Salva un nuovo hackathon nel database e invita i giudici selezionati.
     * Crea l'hackathon solo se l'utente è un organizzatore e aggiorna le associazioni.
     *
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dove si svolgerà l'evento
     * @param dataInizio la data di inizio dell'hackathon
     * @param dataFine la data di fine dell'hackathon
     * @param numMaxIscritti il numero massimo di partecipanti
     * @param dimMaxTeam la dimensione massima dei team
     * @param giudici la lista dei giudici da invitare
     */
    public void saveHackaton(String titolo, String sede, LocalDate dataInizio,
                             LocalDate dataFine, int numMaxIscritti, int dimMaxTeam,
                             List<Giudice> giudici
                             ) {
        if(utente instanceof Organizzatore organizzatore) {
            Integer id = hackatonDAO.addHackaton(titolo, sede, dataInizio, dataFine, numMaxIscritti, dimMaxTeam, utente.getId());
            Hackaton hackaton = new Hackaton(id, titolo, sede, dataInizio,dataFine, numMaxIscritti, dimMaxTeam, organizzatore, giudici);

            for (Giudice giudice : giudici) {
                giudice.addHackaton(hackaton);
            }

            invitaGiudici(giudici, id);
        }
    }

    /**
     * Salva un nuovo utente nel sistema.
     * Registra i dati dell'utente tramite il DAO specificando il tipo di account.
     *
     * @param nome il nome dell'utente
     * @param cognome il cognome dell'utente
     * @param email l'indirizzo email (utilizzato come username)
     * @param password la password dell'account
     * @param tipoUtente il tipo di utente (partecipante, organizzatore, giudice)
     */
    public void saveUser(String nome, String cognome, String email, String password, String tipoUtente) {
        utenteDAO.addUtente(nome, cognome, email, password, tipoUtente);
    }

    /**
     * Autentica un utente con email e password.
     * Se il login è valido, imposta l'utente corrente.
     *
     * @param email email dell'utente
     * @param password password dell'utente
     * @return true se login riuscito, false altrimenti
     */
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

    /**
     * Crea e imposta l'utente corrente in base al tipo.
     * Per i partecipanti carica anche gli hackathon a cui sono registrati.
     *
     * @param id id dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email email dell'utente
     * @param password password dell'utente
     * @param tipoUtente tipo di utente (ORGANIZZATORE, PARTECIPANTE, etc.)
     */

    private void setUtente(Integer id, String nome, String cognome, String email, String password, String tipoUtente) {
        switch (tipoUtente) {
            case "ORGANIZZATORE":
                utente = new Organizzatore(id, nome, cognome, email, password);
                break;
            case "PARTECIPANTE":
                //gli hackaton a cui l'utente è registrato
                List<Hackaton> hackatonsUtente = getHackatonsPartecipante(id);

                utente = new Partecipante(id, nome, cognome, email, password, hackatonsUtente);
                break;
            case "GIUDICE":
                utente = new Giudice(id, nome, cognome, email, password);
                break;
            default:
                utente = new Utente(id, nome, cognome, email, password, tipoUtente);

        }
    }

    /**
     * Recupera gli hackathon a cui è iscritto un partecipante.
     *
     * @param idPartecipante l'ID del partecipante
     * @return lista degli hackathon a cui è iscritto il partecipante
     */
    private List<Hackaton> getHackatonsPartecipante(Integer idPartecipante) {
        List<Hackaton> hackatonsUtente = new ArrayList<>();
        for (Hackaton h : getListaHackaton()) {
            if (hackatonDAO.isUtenteRegistrato(idPartecipante, h.getId())) {
                hackatonsUtente.add(h);
            }
        }
        return hackatonsUtente;
    }

    /**
     * Recupera gli hackathon e li formatta per la visualizzazione in tabella.
     * Converte le date nel formato dd/MM/yyyy.
     *
     * @return matrice di oggetti contenente i dati degli hackathon
     */
    public Object[][] getHackatons() {
        List<Hackaton> listaHackaton = getListaHackaton();
        Object[][] matrice = new Object[listaHackaton.size()][5];

        for (int i = 0; i < listaHackaton.size(); i++) {
            Hackaton h = listaHackaton.get(i);
            matrice[i][0] = h.getTitolo();
            matrice[i][1] = h.getDataInizio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            matrice[i][2] = h.getDataFine().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            matrice[i][3] = "Dettaglio";
            matrice[i][4] = h.getId();
        }

        return matrice;
    }

    /**
     * Get giudici object [ ] [ ].
     *
     * @param giudici the giudici
     * @return the object [ ] [ ]
     */
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


    /**
     * Apre la finestra dei dettagli di un hackathon specifico.
     * Recupera l'hackathon dalla lista in base alla riga selezionata
     * e crea una nuova finestra con tutte le informazioni dell'evento.
     *
     * @param row l'indice della riga selezionata nella tabella
     */
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

    /**
     * Invita una lista di giudici a partecipare come valutatori in un hackathon.
     * Per ogni giudice nella lista, registra l'invito nel database
     * associandolo all'hackathon specificato.
     *
     * @param giudici lista dei giudici da invitare
     * @param hackatonId identificativo dell'hackathon
     */
     public void invitaGiudici(List<Giudice> giudici, Integer hackatonId) {
        for (Giudice giudice : giudici) {
            utenteDAO.invitaGiudice(giudice.getId(), hackatonId);
        }
    }

    /**
     * Apre la finestra Home dell'applicazione.
     * Passa il controller corrente alla nuova finestra.
     */
    public void openHomeFrame() {
        new Home(this);
    }

    /**
     * Apre la finestra per caricare un progresso per il team selezionato.
     * Recupera i dati del team e dell'hackathon associato, poi crea
     * la finestra di dialogo per l'inserimento del progresso.
     *
     * @param teamIndex indice del team nella lista dei team del partecipante
     */
    public void openProgress(int teamIndex) {
        Team team = getTeamByPartecipante().get(teamIndex);
        Hackaton hackaton = getHackatonById(teamDAO.getHackatonIdByTeamId(team.getId()));
        new Progress(
                this,
                hackaton != null ? hackaton.getTitolo() : "",
                team.getId(),
                team.getNome(),
                getPartecipantiTeam(
                        team.getId()
                )
        );
    }

    /**
     * Gets utente.
     *
     * @return the utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * Gets lista giudici.
     *
     * @return the lista giudici
     */
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

    /**
     * Recupera la lista completa degli hackathon dal database.
     *
     * @return lista di tutti gli hackathon
     */
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

        List<Integer> idOrganizzatori = new ArrayList<>();
        List<String> nomiOrganizzatori = new ArrayList<>();
        List<String> cognomiOrganizzatori = new ArrayList<>();

        List<Integer> idGiudici = new ArrayList<>();
        List<String> nomiGiudici = new ArrayList<>();
        List<String> cognomiGiudici = new ArrayList<>();
        List<String> emailGiudici = new ArrayList<>();

        List<Integer> idHackatonInviti = new ArrayList<>();
        List<Integer> idGiudiciInvitati = new ArrayList<>();

        hackatonDAO.getHackatons(ids, titoli, sedi, dateInizio, dateFine, numMaxIscritti, dimMaxTeam, registrazioniAperte, deadlines, descrizioniProblema, idOrganizzatori, nomiOrganizzatori, cognomiOrganizzatori);
        utenteDAO.leggiGiudici(idGiudici, nomiGiudici, cognomiGiudici, emailGiudici);
        hackatonDAO.leggiInvitiGiudice(idHackatonInviti, idGiudiciInvitati);

        List<Hackaton> lista = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Organizzatore organizzatore = new Organizzatore(
                    idOrganizzatori.get(i),
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


    /**
     * Apre le registrazioni per un hackathon impostando la deadline di chiusura.
     * Permette all'organizzatore di attivare le iscrizioni specificando
     * la data limite entro cui i partecipanti possono registrarsi.
     *
     * @param idHackaton l'ID dell'hackathon per cui aprire le registrazioni
     * @param deadline la data di scadenza per le registrazioni
     */
    public void apriRegistrazioni(Integer idHackaton, LocalDate deadline) {
        hackatonDAO.apriRegistrazioni(idHackaton, deadline);
    }

    /**
     * Registra utente hackaton.
     *
     * @param idHackaton the id hackaton
     */
    public void registraUtenteHackaton(Integer idHackaton) {
        if(utente instanceof Partecipante partecipante) {
            hackatonDAO.registraUtente(partecipante.getId(), idHackaton);
            partecipante.setHackatons(getHackatonsPartecipante(partecipante.getId()));
        }
    }

    /**
     * Verifica se l'utente corrente è registrato a un hackathon specifico.
     * Controlla prima che l'utente sia un partecipante, poi interroga
     * il database per verificare l'esistenza della registrazione.
     *
     * @param hackatonId l'ID dell'hackathon da verificare
     * @return true se l'utente è registrato all'hackathon, false altrimenti
     */
    public boolean isUtenteRegistrato(Integer hackatonId) {
        if(isPartecipante()) {
            return hackatonDAO.isUtenteRegistrato(utente.getId(), hackatonId);
        }

        return false;
    }

    /**
     * Restituisce il numero di utenti attualmente registrati a un hackathon.
     * Utilizza il DAO per contare le registrazioni effettuate
     * per l'hackathon specificato.
     *
     * @param idHackaton l'ID dell'hackathon di cui contare gli iscritti
     * @return il numero di utenti registrati all'hackathon
     */
    public int getNumeroUtentiRegistrati(Integer idHackaton) {
        return hackatonDAO.getNumeroUtentiRegistrati(idHackaton);
    }

    /**
     * Imposta la descrizione del problema per un hackathon specifico.
     * Permette ai giudici di pubblicare la sfida che i team
     * dovranno affrontare durante l'evento.
     *
     * @param hackatonId l'ID dell'hackathon per cui impostare la descrizione
     * @param descirizione il testo della descrizione del problema
     */
    public void setDescrizioneProblema(Integer hackatonId, String descirizione) {
        hackatonDAO.updateDescrizioneProblema(hackatonId, descirizione);
    }

    /**
     * Recupera i nomi degli hackathon disponibili per la creazione di team.
     * Filtra gli hackathon a cui partecipa l'utente includendo solo quelli
     * con registrazioni ancora aperte e deadline non scaduta.
     *
     * @return lista dei titoli degli hackathon disponibili per creare team
     */
    public List<String> getHackatonsNamesForCombobox() {
        List<String> hackatonsNames = new ArrayList<>();

        //tutti gli hackaton per cui è possibile creare un team (quindi se le registrazioni sono ancora aperte)
        for(Hackaton hackaton : getPartecipanteHackatonList()) {
            if(hackaton.isRegistrazioniAperte() && !LocalDate.now().isAfter(hackaton.getDeadline())) {
                hackatonsNames.add(hackaton.getTitolo());
            }
        }

        return hackatonsNames;
    }


    /**
     * Crea un nuovo team per un hackathon e vi aggiunge il partecipante corrente.
     * Recupera l'ID dell'hackathon dal titolo, crea il team nel database
     * e automaticamente iscrive l'utente corrente come membro del team.
     *
     * @param titoloHackaton il titolo dell'hackathon
     * @param nomeTeam il nome del nuovo team da creare
     */
    public void addTeam(String titoloHackaton, String nomeTeam) {
        Integer idHackaton = getIdHackatonFromName(titoloHackaton);
        Integer idTeam = teamDAO.addTeam(idHackaton, nomeTeam);
        addPartecipanteAlTeam(idTeam, idHackaton);
    }


    /**
     * Aggiunge il partecipante corrente a un team specifico.
     * Rimuove prima il partecipante da eventuali altri team dello stesso hackathon
     * e poi lo aggiunge al team specificato.
     *
     * @param idTeam identificativo del team a cui aggiungere il partecipante
     * @param idHackaton identificativo dell'hackathon
     */
    private void addPartecipanteAlTeam(Integer idTeam, Integer idHackaton) {
        teamDAO.deletePartecipanteNelTeam(utente.getId(), idHackaton);
        teamDAO.addPartecipanteAlTeam(utente.getId(), idTeam, idHackaton);
    }

    /**
     * Aggiunge il partecipante corrente a un team utilizzando i nomi.
     * Converte i nomi di team e hackathon nei rispettivi ID
     * e aggiunge il partecipante al team specificato.
     *
     * @param nomeTeam nome del team a cui unirsi
     * @param nomeHackaton nome dell'hackathon
     */
    public void addPartecipanteAlTeam(String nomeTeam, String nomeHackaton) {
        Integer idHackaton = getIdHackatonFromName(nomeHackaton);
        addPartecipanteAlTeam(getIdTeamFromName(nomeTeam, idHackaton), idHackaton);
    }

    /**
     * Recupera l'ID di un hackathon dal suo titolo.
     * Cerca tra gli hackathon a cui partecipa l'utente corrente
     * e restituisce l'ID corrispondente al titolo specificato.
     *
     * @param titoloHackaton il titolo dell'hackathon da cercare
     * @return l'ID dell'hackathon trovato, null se non trovato
     */
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

    /**
     * Recupera i nomi dei team disponibili per l'iscrizione in un hackathon.
     * Filtra i team escludendo quelli già completi e quelli a cui
     * il partecipante corrente è già iscritto. Verifica anche che
     * le registrazioni siano ancora aperte per l'hackathon.
     *
     * @param titoloHackaton titolo dell'hackathon
     * @return lista dei nomi dei team disponibili per l'iscrizione
     */
    public List<String> getTeamByHackaton(String titoloHackaton) {
        Integer idHackaton = getIdHackatonFromName(titoloHackaton);
        List<String> teamNames = new ArrayList<>();

        if(idHackaton == null) return teamNames;

        Hackaton hackaton = getHackatonById(idHackaton);
        if(hackaton != null && !hackaton.isRegistrazioniAperte()) return teamNames;

        List<Team> teamByHackaton = getTeamByHackaton(idHackaton);
        for(Team team : teamByHackaton) {
            if(!team.isFull() && !teamDAO.isPartecipanteInTeam(team.getId(), utente.getId())) {
                teamNames.add(team.getNome());
            }
        }
        return teamNames;
    }

    /**
     * Recupera i nomi dei team a cui appartiene il partecipante corrente.
     * Utilizzato per popolare le combo box con i team dell'utente
     * per la gestione dei progressi e altre funzionalità.
     *
     * @return lista dei nomi dei team a cui appartiene il partecipante
     */
    public List<String> getTeamByPartecipanteCb() {
        List<Team> teamByHackaton = getTeamByPartecipante();
        List<String> teamNames = new ArrayList<>();
        for(Team team : teamByHackaton) {
            teamNames.add(team.getNome());
        }
        return teamNames;
    }


    /**
     * Recupera tutti i team partecipanti a un hackathon specifico.
     * Carica dal database le informazioni dei team e li converte
     * in una lista di oggetti Team con tutti i dettagli necessari.
     *
     * @param idHackaton l'ID dell'hackathon di cui recuperare i team
     * @return la lista dei team partecipanti all'hackathon
     */
    public List<Team> getTeamByHackaton(Integer idHackaton) {
        List<Integer> ids = new ArrayList<>();
        List<String> nomiTeam = new ArrayList<>();
        List<Boolean> isFullList = new ArrayList<>();

        teamDAO.getTeamByHackaton(idHackaton, ids, nomiTeam, isFullList);

        List<Team> teams = new ArrayList<>();
        popolaListaTeam(ids, nomiTeam, isFullList, teams);

        return teams;
    }

    /**
     * Recupera tutti i team di cui fa parte il partecipante corrente.
     * Carica dal database i dati dei team e li converte in una lista
     * di oggetti Team con tutte le informazioni necessarie.
     *
     * @return lista dei team a cui appartiene il partecipante
     */
    private List<Team> getTeamByPartecipante() {
        List<Integer> ids = new ArrayList<>();
        List<String> nomiTeam = new ArrayList<>();
        List<Boolean> isFullList = new ArrayList<>();

        teamDAO.getTeamByPartecipante(utente.getId(), ids, nomiTeam, isFullList);

        List<Team> teams = new ArrayList<>();
        popolaListaTeam(ids, nomiTeam, isFullList, teams);

        return teams;
    }

    /**
     * Popola la lista dei team con i dati recuperati dal database.
     * Crea oggetti Team con ID, nome e stato di completamento
     * e li aggiunge alla lista fornita come parametro.
     *
     * @param ids la lista degli ID dei team
     * @param nomiTeam la lista dei nomi dei team
     * @param isFullList la lista degli stati di completamento dei team
     * @param teams la lista da popolare con gli oggetti Team creati
     */
    private void popolaListaTeam(List<Integer> ids, List<String> nomiTeam, List<Boolean> isFullList, List<Team> teams) {
        for (int i = 0; i < ids.size(); i++) {
            Team team = new Team(ids.get(i), nomiTeam.get(i));
            team.setFull(isFullList.get(i));
            teams.add(team);
        }
    }

    /**
     * Cerca un hackathon specifico nella lista degli hackathon disponibili.
     * Scorre tutti gli hackathon per trovare quello con l'ID corrispondente
     * e restituisce null se non viene trovato.
     *
     * @param idHackaton l'ID dell'hackathon da cercare
     * @return l'hackathon trovato o null se non esiste
     */
    private Hackaton getHackatonById(Integer idHackaton) {
        for(Hackaton h : getListaHackaton()) {
            if(h.getId().equals(idHackaton)) {
                return h;
            }
        }

        return null;
    }

    /**
     * Recupera la lista dei partecipanti di un team specifico.
     * Carica nomi e cognomi dal database e li combina in una lista
     * di stringhe formattate per la visualizzazione nell'interfaccia.
     *
     * @param idTeam identificativo del team
     * @return lista dei nomi completi dei partecipanti del team
     */
    private List<String> getPartecipantiTeam(Integer idTeam) {
        List<String> nomi = new ArrayList<>();
        List<String> cognomi = new ArrayList<>();

        teamDAO.getPartecipantiByTeam(idTeam, nomi, cognomi);

        List<String> partecipanti = new ArrayList<>();
        for (int i = 0; i < nomi.size(); i++) {
            partecipanti.add(nomi.get(i) + " " + cognomi.get(i));
        }

        return partecipanti;
    }

    /**
     * Aggiunge un documento di progresso per un team.
     * Salva la descrizione e il file allegato tramite il DAO.
     *
     * @param idTeam l'identificativo del team che carica il progresso
     * @param descrizione la descrizione testuale del progresso
     * @param file il file documento da allegare
     */
    public void addDocumento(Integer idTeam, String descrizione, File file) {
        documentoDAO.addDocumento(idTeam, descrizione, file);
    }

    /**
     * Recupera tutti i documenti e i relativi feedback per un team specifico.
     * Carica i documenti del team e per ciascuno recupera tutti i feedback
     * dei giudici, creando una lista di commenti completa.
     *
     * @param teamIndex indice del team nella lista dei team del partecipante
     * @return lista di commenti contenenti documenti e feedback associati
     */
    public List<Commento> getDocumentoAndFeedbackByTeam(int teamIndex) {
        Integer idTeam = getTeamByPartecipante().get(teamIndex).getId();

        List<Integer> idDocumenti = new ArrayList<>();
        List<String> descrizioni = new ArrayList<>();
        documentoDAO.getDocumentiByTeam(idTeam, idDocumenti, descrizioni);

        List<Commento> commenti = new ArrayList<>();
        for (int i = 0; i < idDocumenti.size(); i++) {


            List<String> nomiGiudici = new ArrayList<>();
            List<String> cognomiGiudici = new ArrayList<>();
            List<String> feedbacks = new ArrayList<>();

            documentoDAO.getFeedbackByDocumento(idDocumenti.get(i), nomiGiudici, cognomiGiudici, feedbacks);
            Documento documento = new Documento(idDocumenti.get(i), descrizioni.get(i));

            if(feedbacks.isEmpty()) {
                commenti.add(new Commento(null, null, documento));
            } else {
                for(int j = 0; j < feedbacks.size(); j++) {
                    Giudice giudice = new Giudice(null, nomiGiudici.get(j), cognomiGiudici.get(j), null, null);
                    Commento commento = new Commento(feedbacks.get(j), giudice, documento );
                    commenti.add(commento);
                }
            }


        }

        return commenti;
    }

    /**
     * Verifica se l'utente corrente è un organizzatore.
     *
     * @return true se l'utente è un organizzatore, false altrimenti
     */
    public boolean isOrganizzatore() {
        return utente.isOrganizzatore();
    }

    /**
     * Verifica se l'utente corrente è un partecipante.
     *
     * @return true se l'utente è un partecipante, false altrimenti
     */
    public boolean isPartecipante() {
        return utente.isPartecipante();
    }

    /**
     * Verifica se l'utente corrente è un giudice.
     *
     * @return true se l'utente è un giudice, false altrimenti
     */
    public boolean isGiudice() {
        return utente.isGiudice();
    }

    /**
     * Recupera i documenti di un hackathon per cui il giudice non ha ancora dato feedback.
     * Carica tutti i documenti dell'hackathon e filtra quelli già valutati
     * dal giudice corrente, restituendo solo quelli ancora da commentare.
     *
     * @param idHackaton l'ID dell'hackathon di cui recuperare i documenti
     * @return la lista dei documenti ancora da valutare dal giudice
     */
    public List<Documento> getDocumentiByHackatonId(Integer idHackaton) {
        List<Integer> idDocumenti = new ArrayList<>();
        List<String> descrizioni = new ArrayList<>();
        List<Integer> idsTeam = new ArrayList<>();
        List<String> nomiTeam = new ArrayList<>();

        documentoDAO.getDocumentiByHackaton(idHackaton, idDocumenti, descrizioni, idsTeam, nomiTeam);

        List<Documento> documenti = new ArrayList<>();

        for (int i = 0; i < idDocumenti.size(); i++) {
            if(documentoDAO.haDatoFeedback(utente.getId(), idDocumenti.get(i))) continue;
            Team team = new Team(idsTeam.get(i), nomiTeam.get(i));

            Documento doc = new Documento(idDocumenti.get(i), descrizioni.get(i));
            doc.setTeam(team);

            documenti.add(doc);
        }

        return documenti;
    }

    /**
     * Inserisce un feedback di un giudice per un documento specifico.
     * Salva nel database il commento del giudice corrente
     * associandolo al documento valutato.
     *
     * @param idDocumento l'ID del documento da commentare
     * @param feedback il testo del feedback del giudice
     */
    public void inserisciFeedbackGiudice(Integer idDocumento, String feedback) {
        documentoDAO.inserisciFeedbackGiudice(utente.getId(), idDocumento, feedback);
    }

    /**
     * Salva il voto assegnato da un giudice a un team.
     * Registra la valutazione nel database collegando giudice, team e punteggio.
     *
     * @param idTeam l'identificativo del team valutato
     * @param voto il punteggio assegnato (da 0 a 10)
     */
    public void salvaVoto(Integer idTeam, Integer voto) {
        teamDAO.addTeamVoto(utente.getId(), idTeam, voto);
    }

    /**
     * Verifica se il giudice corrente ha già espresso i suoi voti per un hackathon.
     * Controlla nel database se il giudice ha completato la valutazione
     * di tutti i team dell'hackathon specificato.
     *
     * @param idHackaton l'ID dell'hackathon da verificare
     * @return true se il giudice ha già votato, false altrimenti
     */
    public boolean giudiceHaVotatoInHackaton(Integer idHackaton) {
        return teamDAO.giudiceHaVotatoInHackaton(utente.getId(), idHackaton);
    }

    /**
     * Genera la classifica finale di un hackathon con i punteggi dei team.
     * Calcola la somma dei voti di tutti i giudici per ogni team
     * e ordina la classifica in base ai punteggi ottenuti.
     *
     * @param idHackaton l'ID dell'hackathon di cui generare la classifica
     * @param teamClassifica la lista da popolare con i nomi dei team ordinati
     * @param punteggi la lista da popolare con i punteggi corrispondenti
     */
    public void getClassifica(Integer idHackaton, List<String> teamClassifica, List<Integer> punteggi) {
        Hackaton hackaton = getHackatonById(idHackaton);
        if (hackaton == null) return;

        List<Giudice> giudici = hackaton.getGiudici();
        List<Team> teamList = getTeamByHackaton(idHackaton);

        if (!tuttiGiudiciHannoVotatoTuttiIGruppi(giudici, teamList)) return;

        inizializzaClassifica(teamList, teamClassifica, punteggi);
        sommaVotiGiudici(giudici, idHackaton, teamClassifica, punteggi);
        ordinaClassifica(teamClassifica, punteggi);
    }


    /**
     * Verifica se tutti i giudici hanno votato tutti i team dell'hackathon.
     * Controlla che ogni giudice abbia espresso un voto per ciascun team
     * prima di poter generare la classifica finale.
     *
     * @param giudici la lista dei giudici dell'hackathon
     * @param teamList la lista dei team da verificare
     * @return true se tutti i giudici hanno votato tutti i team, false altrimenti
     */
    private boolean tuttiGiudiciHannoVotatoTuttiIGruppi(List<Giudice> giudici, List<Team> teamList) {
        for (Giudice giudice : giudici) {
            for (Team team : teamList) {
                if (!teamDAO.giudiceHaVotatoTeam(giudice.getId(), team.getId())) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * Inizializza le liste per la classifica con i nomi dei team e punteggi a zero.
     * Prepara le strutture dati necessarie per calcolare la classifica finale
     * impostando tutti i punteggi iniziali a zero.
     *
     * @param teamList lista dei team partecipanti all'hackathon
     * @param teamClassifica lista dei nomi dei team per la classifica
     * @param punteggi lista dei punteggi inizializzati a zero
     */
    private void inizializzaClassifica(List<Team> teamList, List<String> teamClassifica, List<Integer> punteggi) {
        for (Team team : teamList) {
            teamClassifica.add(team.getNome());
            punteggi.add(0);
        }
    }

    /**
     * Somma i voti di tutti i giudici per calcolare il punteggio finale dei team.
     * Recupera i voti di ogni giudice e li accumula per ciascun team
     * nella classifica finale dell'hackathon.
     *
     * @param giudici lista dei giudici dell'hackathon
     * @param idHackaton identificativo dell'hackathon
     * @param teamClassifica lista dei nomi dei team in classifica
     * @param punteggi lista dei punteggi accumulati per ogni team
     */
    private void sommaVotiGiudici(List<Giudice> giudici, Integer idHackaton, List<String> teamClassifica, List<Integer> punteggi) {
        for (Giudice giudice : giudici) {
            List<String> nomiTeam = new ArrayList<>();
            List<Integer> votiGiudice = new ArrayList<>();
            teamDAO.getVotiDelGiudice(giudice.getId(), idHackaton, nomiTeam, votiGiudice);

            for (int i = 0; i < nomiTeam.size(); i++) {
                String nome = nomiTeam.get(i);
                int voto = votiGiudice.get(i);

                int index = teamClassifica.indexOf(nome);
                if (index != -1) {
                    punteggi.set(index, punteggi.get(index) + voto);
                }
            }
        }
    }

    /**
     * Ordina la classifica dei team in base ai punteggi ottenuti.
     * Riorganizza le liste di team e punteggi in ordine decrescente
     * per mostrare i team vincitori in cima alla classifica.
     *
     * @param teamClassifica lista dei nomi dei team da ordinare
     * @param punteggi lista dei punteggi corrispondenti ai team
     */
    private void ordinaClassifica(List<String> teamClassifica, List<Integer> punteggi) {
        for (int i = 0; i < punteggi.size() - 1; i++) {
            for (int j = i + 1; j < punteggi.size(); j++) {
                if (punteggi.get(j) > punteggi.get(i)) {
                    int tempPunteggio = punteggi.get(i);
                    punteggi.set(i, punteggi.get(j));
                    punteggi.set(j, tempPunteggio);

                    String tempTeam = teamClassifica.get(i);
                    teamClassifica.set(i, teamClassifica.get(j));
                    teamClassifica.set(j, tempTeam);
                }
            }
        }
    }

    /**
     * Verifica se l'utente corrente è l'organizzatore di un hackathon specifico.
     * Controlla che l'hackathon esista e che l'ID dell'organizzatore
     * corrisponda all'ID dell'utente loggato.
     *
     * @param idHackaton l'ID dell'hackathon da verificare
     * @return true se l'utente è l'organizzatore, false altrimenti
     */
    public boolean isOrganizzatoreOfHackaton(Integer idHackaton) {
        Hackaton hackaton = getHackatonById(idHackaton);
        if(hackaton == null) return false;

        return hackaton.getOrganizzatore().getId().equals(utente.getId());
    }


    /**
     * Verifica se l'utente corrente è un giudice dell'hackathon specificato.
     * Controlla che l'hackathon esista e scorre la lista dei giudici
     * per trovare una corrispondenza con l'ID dell'utente loggato.
     *
     * @param idHackaton l'ID dell'hackathon da verificare
     * @return true se l'utente è un giudice dell'hackathon, false altrimenti
     */
    public boolean isGiudiceInHackaton(Integer idHackaton) {
        Hackaton hackaton = getHackatonById(idHackaton);
        if(hackaton == null) return false;

        List<Giudice> giudici = hackaton.getGiudici();
        for (Giudice giudice : giudici) {
            if (giudice.getId().equals(utente.getId())) return true;
        }

        return false;
    }
}
