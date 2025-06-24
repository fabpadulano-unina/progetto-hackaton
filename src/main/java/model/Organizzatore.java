package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {

    private List<Hackaton> hackatonOrganizzati = new ArrayList<>();

    public Organizzatore(String nome, String cognome, String email, String password, Hackaton hackaton) {
        super(nome, cognome, email, password, "ORGANIZZATORE");
        addHackaton(hackaton);
    }

    public Organizzatore(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password, "ORGANIZZATORE");
    }



    public void addHackaton(Hackaton hackaton) {
        hackaton.setOrganizzatore(this);
        hackatonOrganizzati.add(hackaton);
    }

    public List<Hackaton> getHackatonOrganizzati() {
        return hackatonOrganizzati;
    }

}
