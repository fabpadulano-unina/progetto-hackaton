package model;

import java.time.LocalDate;

public class Hackaton {
    public String titolo;
    public String sede;
    public LocalDate dataInizio;
    public LocalDate dataFine;
    public int numMaxIscritti;
    public int dimMaxTeam;

    public Organizzatore organizzatore;

    public Hackaton(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, Organizzatore organizzatore) {
        this.titolo = titolo;
        this.sede = sede;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.numMaxIscritti = numMaxIscritti;
        this.dimMaxTeam = dimMaxTeam;
        this.organizzatore = organizzatore;
    }

    public Hackaton(Organizzatore organizzatore) {
        this.organizzatore = organizzatore;
    }
}
