package model;

import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Partecipante> partecipanti = new ArrayList<>();
    private ArrayList<Documento> documenti = new ArrayList<>();

    public Team(String nome, Partecipante partecipante) {
        this.setName(nome);
        this.getPartecipanti().add(partecipante);
        partecipante.setTeam(this);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Partecipante> getPartecipanti() {
        return partecipanti;
    }

    public ArrayList<Documento> getDocumenti() {
        return documenti;
    }

    private void addDocumento(Documento documento) {
        documenti.add(documento);
    }

    public void setPartecipanti(ArrayList<Partecipante> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void caricaProgresso(Documento documento) {
        this.documenti.add(documento);
    }


}
