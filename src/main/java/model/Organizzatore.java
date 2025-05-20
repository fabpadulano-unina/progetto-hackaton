package model;

import java.util.ArrayList;
import java.util.List;

public class Organizzatore  extends Utente {


    public Organizzatore(String nome, String cognome, String email, Hackaton hackaton) {
        super(nome, cognome, email, hackaton);
        hackaton.setOrganizzatore(this);
    }

    public Organizzatore(String nome, String cognome, String email) {
        super(nome, cognome, email);
    }



    public void addHackaton(Hackaton hackaton) {
        this.getHackatons().add(hackaton);
    }


}
