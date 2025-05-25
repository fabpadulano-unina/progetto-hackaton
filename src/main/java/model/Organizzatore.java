package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {

    private List<Hackaton> hackatonOrganizzati = new ArrayList<>();

    public Organizzatore(String nome, String cognome, String email, Hackaton hackaton) {
        super(nome, cognome, email);
        addHackaton(hackaton);
    }

    public Organizzatore(String nome, String cognome, String email) {
        super(nome, cognome, email);
    }



    public void addHackaton(Hackaton hackaton) {
        hackaton.setOrganizzatore(this);
        hackatonOrganizzati.add(hackaton);
    }

    public List<Hackaton> getHackatonOrganizzati() {
        return hackatonOrganizzati;
    }

}
