package model;

import java.util.ArrayList;
import java.util.List;

public class Giudice extends Utente {
    private final List<Hackaton> hackatonGiudicati = new ArrayList<>();

    public Giudice(Integer id, String nome, String cognome, String email, String password) {
        super(id, nome, cognome, email, password, "GIUDICE");

    }

    public void addHackaton(Hackaton hackaton) {
        hackatonGiudicati.add(hackaton);
        hackaton.addGiudice(this);
    }
}
