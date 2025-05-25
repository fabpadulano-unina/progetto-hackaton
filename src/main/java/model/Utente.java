package model;

import java.util.ArrayList;
import java.util.List;

public class Utente {
    private String nome;
    private String cognome;
    private String email;

    public Utente(String nome, String cognome, String email) {
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
