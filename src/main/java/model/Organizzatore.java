package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {
    private List<Hackaton> hackaton = new ArrayList<>();

    public Organizzatore(String nome, String cognome, Piattaforma piattaforma, Hackaton hackaton) {
        super(nome, cognome, piattaforma);
        this.getHackaton().add(hackaton);
        hackaton.setOrganizzatore(this);
    }

    public Organizzatore(String nome, String cognome, Piattaforma piattaforma) {
        super(nome, cognome, piattaforma);
    }

    public List<Hackaton> getHackaton() {
        return hackaton;
    }

    public void setHackaton(List<Hackaton> hackaton) {
        this.hackaton = hackaton;
    }

    public void addHackaton(Hackaton hackaton) {
        this.hackaton.add(hackaton);
    }

    public void invitaGiudici(List<Giudice> giudici) {}

    public void apriRegisteazioni() {}

    public void chiudiRegistrazioni() {}
}
