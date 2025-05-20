package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hackaton {
    private String titolo;
    private String sede;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numMaxIscritti;
    private int dimMaxTeam;

    private Organizzatore organizzatore;
    private ArrayList<Utente> utenti = new ArrayList<>();

    public Hackaton(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, Organizzatore organizzatore) {
        this.setTitolo(titolo);
        this.setSede(sede);
        this.setDataInizio(dataInizio);
        this.setDataFine(dataFine);
        this.setNumMaxIscritti(numMaxIscritti);
        this.setDimMaxTeam(dimMaxTeam);
        this.setOrganizzatore(organizzatore);
    }

    public Hackaton(Organizzatore organizzatore) {
        this.setOrganizzatore(organizzatore);
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getSede() {
        return sede;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public int getNumMaxIscritti() {
        return numMaxIscritti;
    }

    public void setNumMaxIscritti(int numMaxIscritti) {
        this.numMaxIscritti = numMaxIscritti;
    }

    public int getDimMaxTeam() {
        return dimMaxTeam;
    }

    public void setDimMaxTeam(int dimMaxTeam) {
        this.dimMaxTeam = dimMaxTeam;
    }

    public Organizzatore getOrganizzatore() {
        return organizzatore;
    }

    public void setOrganizzatore(Organizzatore organizzatore) {
        this.organizzatore = organizzatore;
    }

    public void acquisisciVoti() {}

    public void pubblicaClassifica() {}

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
