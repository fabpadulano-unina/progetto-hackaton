package model;

import java.util.List;

/**
 * Rappresenta un partecipante agli hackathon.
 * Estende Utente e mantiene la lista degli hackathon a cui è registrato.
 */
public class Partecipante extends Utente {
    /** Lista degli hackathon a cui il partecipante è registrato */
    private List<Hackaton> hackatons;

    /**
     * Costruttore.
     *
     * @param id ID del partecipante
     * @param nome nome del partecipante
     * @param cognome cognome del partecipante
     * @param email email del partecipante
     * @param password password del partecipante
     * @param hackatons lista degli hackathon a cui è registrato
     */
    public Partecipante(Integer id, String nome, String cognome, String email, String password, List<Hackaton> hackatons) {
        super(id, nome, cognome, email, password, "PARTECIPANTE");
        this.hackatons = hackatons;
    }

    /**
     * @return lista degli hackathon a cui è registrato
     */
    public List<Hackaton> getHackatons() {
        return hackatons;
    }

    /**
     * @param hackatons lista degli hackathon a cui è registrato
     */
    public void setHackatons(List<Hackaton> hackatons) {
        this.hackatons = hackatons;
    }

}
