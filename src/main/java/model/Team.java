package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Integer id;
    private String nome;
    private Hackaton hackaton;
    private List<Partecipante> partecipanti = new ArrayList<>();
    private List<Documento> documenti = new ArrayList<>();

    public Team(Integer id, String nome) {
        this.setId(id);
        this.setNome(nome);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Partecipante> getPartecipanti() {
        return partecipanti;
    }

    public List<Documento> getDocumenti() {
        return documenti;
    }

    private void addDocumento(Documento documento) {
        documenti.add(documento);
    }

    public void setPartecipanti(List<Partecipante> partecipanti) {
        this.partecipanti = partecipanti;
    }

    public void caricaProgresso(Documento documento) {
        this.documenti.add(documento);
    }


    public Hackaton getHackaton() {
        return hackaton;
    }

    public void setHackaton(Hackaton hackaton) {
        this.hackaton = hackaton;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
