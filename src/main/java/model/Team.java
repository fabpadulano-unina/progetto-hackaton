package model;

import java.util.ArrayList;

public class Team {
    public String name;
    ArrayList<Partecipante> partecipanti = new ArrayList<>();
    ArrayList<Documento> documenti = new ArrayList<>();

    public Team(String nome, Partecipante partecipante) {
        this.name = nome;
        this.partecipanti.add(partecipante);
    }


    public Team(String nome, Piattaforma piattaforma) {
        this.name = nome;
        this.partecipanti.add(new Partecipante(piattaforma, this, ERuoloTeam.UNKNOWN));
    }

    public void caricaProgresso(Documento documento) {
        this.documenti.add(documento);
    }

}
