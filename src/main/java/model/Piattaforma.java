package model;

import java.util.ArrayList;

public class Piattaforma {
    private ArrayList<Utente> utenti = new ArrayList<>();



    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    private void addutente(Utente utente) {
        this.utenti.add(utente);
    }


}
