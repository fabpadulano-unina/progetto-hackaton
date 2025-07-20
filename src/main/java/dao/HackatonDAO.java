package dao;


import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia DAO per la gestione degli hackathon nel database.
 * Fornisce metodi per creare, recuperare e gestire hackathon e registrazioni.
 */
public interface HackatonDAO {
    /**
     * Crea un nuovo hackathon nel database.
     *
     * @param nome il nome dell'hackathon
     * @param sede la sede dell'hackathon
     * @param dataInizio la data di inizio
     * @param dataFine la data di fine
     * @param numMaxIscritti il numero massimo di iscritti
     * @param dimMaxTeam la dimensione massima del team
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'hackathon creato
     */
    Integer addHackaton(String nome, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam,  int idOrganizzatore);

    /**
     * Recupera tutti gli hackathon con i relativi organizzatori.
     *
     * @param ids lista per memorizzare gli ID degli hackathon
     * @param titoli lista per memorizzare i titoli
     * @param sedi lista per memorizzare le sedi
     * @param dateInizio lista per memorizzare le date di inizio
     * @param dateFine lista per memorizzare le date di fine
     * @param numMaxIscritti lista per memorizzare il numero massimo di iscritti
     * @param dimMaxTeam lista per memorizzare la dimensione massima del team
     * @param registrazioniAperte lista per memorizzare lo stato delle registrazioni
     * @param deadlines lista per memorizzare le deadline di registrazione
     * @param descrizioniProblema lista per memorizzare le descrizioni dei problemi
     * @param idOrganizzatori lista per memorizzare gli ID degli organizzatori
     * @param nomiOrganizzatori lista per memorizzare i nomi degli organizzatori
     * @param cognomiOrganizzatori lista per memorizzare i cognomi degli organizzatori
     */
    void getHackatons(
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<Date> dateInizio,
            List<Date> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Boolean> registrazioniAperte,
            List<Date> deadlines,
            List<String> descrizioniProblema,
            List<Integer> idOrganizzatori,
            List<String> nomiOrganizzatori,
            List<String> cognomiOrganizzatori
    );

    /**
     * Recupera gli inviti dei giudici per gli hackathon dal database.
     * Popola le liste con gli ID degli hackathon e dei giudici corrispondenti.
     *
     * @param idHackaton lista per memorizzare gli ID degli hackathon
     * @param idGiudice lista per memorizzare gli ID dei giudici invitati
     */
    void leggiInvitiGiudice(List<Integer> idHackaton, List<Integer> idGiudice);

    /**
     * Apre le registrazioni per un hackathon impostando la deadline di chiusura.
     *
     * @param hackatonId l'ID dell'hackathon per cui aprire le registrazioni
     * @param deadline la data di scadenza per le registrazioni
     */
    void apriRegistrazioni(Integer hackatonId, LocalDate deadline);

    /**
     * Registra un utente a un hackathon.
     *
     * @param idUtente l'ID dell'utente
     * @param idHackaton l'ID dell'hackathon
     */
    void registraUtente(Integer idUtente, Integer idHackaton);

    /**
     * Verifica se un utente è registrato a un hackathon.
     *
     * @param idUtente l'ID dell'utente
     * @param idHackaton l'ID dell'hackathon
     * @return true se l'utente è registrato, false altrimenti
     */
    boolean isUtenteRegistrato(Integer idUtente, Integer idHackaton);

    /**
     * Conta il numero di utenti registrati a un hackathon.
     *
     * @param idHackaton l'ID dell'hackathon
     * @return il numero di utenti registrati
     */
    int getNumeroUtentiRegistrati(Integer idHackaton);

    /**
     * Aggiorna la descrizione del problema per un hackathon.
     *
     * @param idHackaton l'ID dell'hackathon
     * @param descrizioneProblema la nuova descrizione del problema
     */
    void updateDescrizioneProblema(Integer idHackaton, String descrizioneProblema);
}
