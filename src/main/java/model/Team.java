package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Integer id;
    private String nome;
    private List<Partecipante> partecipanti = new ArrayList<>();
    private boolean isFull;

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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
}
