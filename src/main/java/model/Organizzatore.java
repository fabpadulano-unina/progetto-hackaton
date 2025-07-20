package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un organizzatore di hackathon.
 * Estende Utente e gestisce gli hackathon organizzati.
 */
public class Organizzatore  extends Utente {

    private final List<Hackaton> hackatonOrganizzati = new ArrayList<>();

    /**
     * Costruttore con hackathon iniziale.
     *
     * @param id ID dell'organizzatore
     * @param nome nome dell'organizzatore
     * @param cognome cognome dell'organizzatore
     * @param email email dell'organizzatore
     * @param password password dell'organizzatore
     * @param hackaton hackathon da aggiungere
     */
    public Organizzatore(Integer id, String nome, String cognome, String email, String password, Hackaton hackaton) {
        super(id, nome, cognome, email, password, "ORGANIZZATORE");
        addHackaton(hackaton);
    }

    /**
     * Costruttore senza hackathon iniziale.
     *
     * @param id ID dell'organizzatore
     * @param nome nome dell'organizzatore
     * @param cognome cognome dell'organizzatore
     * @param email email dell'organizzatore
     * @param password password dell'organizzatore
     */
    public Organizzatore(Integer id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password, "ORGANIZZATORE");
    }


    /**
     * Aggiunge un hackathon alla lista e si imposta come organizzatore dell'hackaton.
     *
     * @param hackaton hackathon da aggiungere
     */
    public void addHackaton(Hackaton hackaton) {
        hackaton.setOrganizzatore(this);
        hackatonOrganizzati.add(hackaton);
    }

}
