package model;

import java.util.ArrayList;

public class Piattaforma {
    public String nomePiattaforma;
    public ArrayList<Utente> utenti = new ArrayList<>();

    public Piattaforma(String nomePiattaforma) {
        this.nomePiattaforma = nomePiattaforma;
    }

    public void acquisisciVoti() {}

    public void pubblicaClassifica() {}
}
