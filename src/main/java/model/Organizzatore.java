package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {

    private List<Hackaton> hackatonOrganizzati = new ArrayList<>();

    public Organizzatore(Integer id, String nome, String cognome, String email, String password, Hackaton hackaton) {
        super(id, nome, cognome, email, password, "ORGANIZZATORE");
        addHackaton(hackaton);
    }

    public Organizzatore(Integer id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password, "ORGANIZZATORE");
    }



    public void addHackaton(Hackaton hackaton) {
        hackaton.setOrganizzatore(this);
        hackatonOrganizzati.add(hackaton);
    }

    public List<Hackaton> getHackatonOrganizzati() {
        return hackatonOrganizzati;
    }

}
