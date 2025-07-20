package dao;

import java.util.List;

public interface TeamDAO {
    /**
     * Crea un nuovo team per un hackathon specifico.
     * Inserisce il team nel database associandolo all'hackathon
     * e restituisce l'ID del team appena creato.
     *
     * @param idHackaton identificativo dell'hackathon
     * @param nomeTeam nome del nuovo team da creare
     * @return l'ID del team appena creato
     */
    Integer addTeam(Integer idHackaton, String nomeTeam);


    /**
     * Recupera tutti i team partecipanti a un hackathon specifico.
     * Carica dal database le informazioni dei team e li converte
     * in una lista di oggetti Team con tutti i dettagli necessari.
     *
     * @param idHackaton identificativo dell'hackathon
     * @param ids lista che verrà popolata con gli id dei team
     * @param nomiTeam lista che verrà popolata con i nomi dei team
     * @param isFullList lista che indica se ogni team è al completo
     */
    void getTeamByHackaton(Integer idHackaton, List<Integer> ids, List<String> nomiTeam, List<Boolean> isFullList);

    /**
     * Aggiunge un partecipante a un team specifico di un hackathon.
     * Crea l'associazione nel database tra partecipante, team
     * e hackathon per registrare l'appartenenza al team.
     *
     * @param idPartecipante identificativo del partecipante
     * @param idTeam identificativo del team
     * @param idHackaton identificativo dell'hackathon
     */
    void addPartecipanteAlTeam(Integer idPartecipante, Integer idTeam, Integer idHackaton);

    /**
     * Verifica se un partecipante appartiene a un team specifico.
     * Controlla nel database se esiste un'associazione tra
     * il partecipante e il team indicato.
     *
     * @param idTeam identificativo del team
     * @param idUtente identificativo del partecipante
     * @return true se il partecipante appartiene al team, false altrimenti
     */
    boolean isPartecipanteInTeam(Integer idTeam, Integer idUtente);

    /**
     * Recupera tutti i team di cui fa parte un partecipante specifico.
     * Carica dal database gli ID, i nomi e lo stato di completamento
     * dei team associati al partecipante indicato.
     *
     * @param idPartecipante identificativo del partecipante
     * @param idTeam lista che verrà popolata con gli ID dei team
     * @param nomiTeam lista che verrà popolata con i nomi dei team
     * @param isFullList lista che indica se ogni team è al completo
     */
    void getTeamByPartecipante(Integer idPartecipante, List<Integer> idTeam, List<String> nomiTeam, List<Boolean> isFullList);

    /**
     * Rimuove un partecipante da tutti i team di un hackathon specifico.
     * Elimina dal database tutte le associazioni tra il partecipante
     * e i team dell'hackathon indicato.
     *
     * @param idPartecipante identificativo del partecipante da rimuovere
     * @param idHackaton identificativo dell'hackathon
     */
    void deletePartecipanteNelTeam(Integer idPartecipante, Integer idHackaton);

    /**
     * Recupera tutti i partecipanti appartenenti a un team specifico.
     * Carica dal database i nomi e cognomi di tutti i membri
     * del team indicato.
     *
     * @param idTeam identificativo del team
     * @param nomiPartecipanti lista che verrà popolata con i nomi dei partecipanti
     * @param cognomiPartecipanti lista che verrà popolata con i cognomi dei partecipanti
     */
    void getPartecipantiByTeam(Integer idTeam, List<String> nomiPartecipanti, List<String> cognomiPartecipanti);

    /**
     * Recupera l'ID dell'hackathon associato a un team specifico.
     * Cerca nel database l'hackathon a cui appartiene il team indicato.
     *
     * @param idTeam identificativo del team
     * @return l'ID dell'hackathon associato al team
     */
    Integer getHackatonIdByTeamId(Integer idTeam);

    /**
     * Registra il voto di un giudice per un team nel database.
     * Inserisce la valutazione nella tabella dei voti collegando
     * giudice, team e punteggio assegnato.
     *
     * @param idGiudice l'identificativo del giudice che vota
     * @param idTeam l'identificativo del team valutato
     * @param voto il punteggio assegnato (da 0 a 10)
     */
    void addTeamVoto(Integer idGiudice, Integer idTeam, int voto);

    /**
     * Verifica se il giudice corrente ha già espresso i suoi voti per un hackathon.
     * Controlla nel database se il giudice ha completato la valutazione
     * di tutti i team dell'hackathon specificato.
     * @param idGiudice id del giudice
     * @param idHackaton id dell'hackaton
     */
    boolean giudiceHaVotatoInHackaton(Integer idGiudice, Integer idHackaton);

    /**
     * Verifica se un giudice ha già votato un team specifico.
     * Controlla nel database se esiste un voto del giudice
     * per il team indicato.
     * @param idGiudice id del giudice
     * @param idTeam id del team
     */
    boolean giudiceHaVotatoTeam(Integer idGiudice, Integer idTeam);

    /**
     * Recupera tutti i voti assegnati da un giudice in un hackathon specifico.
     * Carica dal database i nomi dei team e i rispettivi voti
     * dati dal giudice durante la valutazione finale.
     *
     * @param idGiudice identificativo del giudice
     * @param idHackaton identificativo dell'hackathon
     * @param nomiTeam lista che verrà popolata con i nomi dei team votati
     * @param voti lista che verrà popolata con i voti assegnati
     */
    void getVotiDelGiudice(Integer idGiudice, Integer idHackaton, List<String> nomiTeam, List<Integer> voti);
}
