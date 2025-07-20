package dao;

import java.io.File;
import java.util.List;

public interface DocumentoDAO {

    /**
     * Inserisce un nuovo documento di progresso nel database.
     * Salva la descrizione e il file associato al team specificato.
     *
     * @param idTeam l'identificativo del team proprietario del documento
     * @param descrizione la descrizione testuale del progresso
     * @param file il file documento da salvare
     */
    void addDocumento(Integer idTeam, String descrizione, File file);

    /**
     * Recupera tutti i documenti caricati da un team specifico.
     * Carica dal database gli ID e le descrizioni di tutti i documenti
     * di progresso pubblicati dal team durante l'hackathon.
     *
     * @param idTeam identificativo del team
     * @param ids lista che verrà popolata con gli ID dei documenti
     * @param descrizioni lista che verrà popolata con le descrizioni dei documenti
     */
    void getDocumentiByTeam(Integer idTeam, List<Integer> ids, List<String> descrizioni);


    /**
     * Recupera i documenti di un hackathon per cui il giudice non ha ancora dato feedback.
     */
    void getDocumentiByHackaton(Integer idHackaton, List<Integer> idDocumenti, List<String> descrizioni, List<Integer> idTeam, List<String> nomiTeam);

    /**
     * Salva nel database il commento del giudice corrente
     * associandolo al documento valutato.
     */
    void inserisciFeedbackGiudice(Integer idGiudice, Integer idDocumento, String feedback);

    /**
     * Verifica se un giudice ha già fornito feedback per un documento specifico.
     * Controlla nel database se esiste già una valutazione del giudice
     * per il documento indicato.
     *
     * @param idGiudice l'ID del giudice da verificare
     * @param idDocumento l'ID del documento da controllare
     * @return true se il giudice ha già dato feedback, false altrimenti
     */
    boolean haDatoFeedback(Integer idGiudice, Integer idDocumento);


    /**
     * Recupera tutti i feedback associati a un documento specifico.
     * Carica dal database i dati dei giudici che hanno commentato
     * il documento e i relativi feedback forniti.
     *
     * @param idDocumento identificativo del documento
     * @param nomiGiudici lista che verrà popolata con i nomi dei giudici
     * @param cognomiGiudici lista che verrà popolata con i cognomi dei giudici
     * @param feedbacks lista che verrà popolata con i feedback dei giudici
     */
    void getFeedbackByDocumento(Integer idDocumento, List<String> nomiGiudici, List<String> cognomiGiudici, List<String> feedbacks);

}
