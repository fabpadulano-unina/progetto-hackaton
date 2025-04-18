package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {
    public List<Hackaton> hackaton = new ArrayList<>();

    public Organizzatore(String nome, String cognome, Piattaforma piattaforma, Hackaton hackaton) {
        super(nome, cognome, piattaforma);
        this.hackaton.add(hackaton);
    }

    public Organizzatore(String nome, String cognome, Piattaforma piattaforma) {
        super(nome, cognome, piattaforma);
        this.hackaton.add(new Hackaton(this));
    }

    public void invitaGiudici(List<Giudice> giudici) {}

    public void apriRegisteazioni() {}
}
