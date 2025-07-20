package dao;


import java.util.List;

/**
 * Interfaccia DAO per la gestione degli utenti nel database.
 * Fornisce metodi per registrazione, autenticazione e gestione giudici.
 */
public interface UtenteDAO {
    /**
     * Registra un nuovo utente nel sistema.
     *
     * @param nome il nome dell'utente
     * @param cognome il cognome dell'utente
     * @param email l'email dell'utente
     * @param password la password dell'utente
     * @param tipoUtente il tipo di utente (Organizzatore/Partecipante)
     */
    void addUtente(String nome, String cognome, String email, String password, String tipoUtente);

    /**
     * Autentica un utente e recupera i suoi dati.
     *
     * @param id array contenente l'ID dell'utente trovato
     * @param email l'email per l'autenticazione
     * @param password la password per l'autenticazione
     * @param nome StringBuilder per memorizzare il nome
     * @param cognome StringBuilder per memorizzare il cognome
     * @param tipoUtente StringBuilder per memorizzare il tipo utente
     */
    void getUtente(Integer[] id, String email, String password,
                       StringBuilder nome,
                       StringBuilder cognome,
                       StringBuilder tipoUtente);

    /**
     * Invita un utente come giudice per un hackathon.
     *
     * @param id l'ID dell'utente da invitare
     * @param hackatonId l'ID dell'hackathon
     */
    void invitaGiudice(Integer id, Integer hackatonId);

    /**
     * Recupera la lista dei giudici disponibili.
     *
     * @param ids lista per memorizzare gli ID dei giudici
     * @param nomi lista per memorizzare i nomi dei giudici
     * @param cognomi lista per memorizzare i cognomi dei giudici
     * @param email lista per memorizzare le email dei giudici
     */
    void leggiGiudici(List<Integer> ids, List<String> nomi, List<String> cognomi, List<String> email);

}
