package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hackaton {
    private String titolo;
    private String sede;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numMaxIscritti;
    private int dimMaxTeam;
    private Organizzatore organizzatore;
    private List<Partecipante> partecipanti = new ArrayList<>();
    private List<Giudice> giudici = new ArrayList<>();


    public Hackaton(
            String titolo,
            String sede,
            LocalDate dataInizio,
            LocalDate dataFine,
            int numMaxIscritti,
            int dimMaxTeam,
            Organizzatore organizzatore,
            List<Giudice> giudici
    ) {
        this.setTitolo(titolo);
        this.setSede(sede);
        this.setDataInizio(dataInizio);
        this.setDataFine(dataFine);
        this.setNumMaxIscritti(numMaxIscritti);
        this.setDimMaxTeam(dimMaxTeam);
        this.setOrganizzatore(organizzatore);
        this.giudici.addAll(giudici);
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

    public List<Partecipante> getPartecipanti() {
        return partecipanti;
    }

    public void addPartecipante(Partecipante partecipante) {
        this.partecipanti.add(partecipante);
    }

    public List<Giudice> getGiudici() {
        return giudici;
    }

    public void addGiudice(Giudice giudice) {
        this.giudici.add(giudice);
    }
}
