package model;

import java.util.ArrayList;

public class Piattaforma {
    private String nomePiattaforma;
    private ArrayList<Utente> utenti = new ArrayList<>();

    public Piattaforma(String nomePiattaforma) {
        this.setNomePiattaforma(nomePiattaforma);
    }

    public String getNomePiattaforma() {
        return nomePiattaforma;
    }

    public void setNomePiattaforma(String nomePiattaforma) {
        this.nomePiattaforma = nomePiattaforma;
    }

    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(ArrayList<Utente> utenti) {
        this.utenti = utenti;
    }

    private void addutente(Utente utente) {
        this.utenti.add(utente);
    }

    public void acquisisciVoti() {}

    public void pubblicaClassifica() {}
}
