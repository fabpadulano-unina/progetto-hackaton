package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta un giudice di hackathon.
 * Estende Utente e gestisce gli hackathon che deve giudicare.
 */
public class Giudice extends Utente {
    /** Lista degli hackathon da giudicare */
    private final List<Hackaton> hackatonGiudicati = new ArrayList<>();

    /**
     * Costruttore.
     *
     * @param id ID del giudice
     * @param nome nome del giudice
     * @param cognome cognome del giudice
     * @param email email del giudice
     * @param password password del giudice
     */
    public Giudice(Integer id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password, "GIUDICE");

    }

    /**
     * Aggiunge un hackathon da giudicare e si aggiunge alla lista dei giudici.
     *
     * @param hackaton hackathon da aggiungere
     */
    public void addHackaton(Hackaton hackaton) {
        hackatonGiudicati.add(hackaton);
        hackaton.addGiudice(this);
    }
}
