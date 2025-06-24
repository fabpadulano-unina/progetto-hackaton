package model;

public class Partecipante extends Utente {

    public Partecipante(String nome, String cognome, String email, String password ) {
        super(nome, cognome, email, password, "PARTECIPANTE");
    }


    public void registra(Hackaton hackaton) {
        hackaton.addPartecipante(this);
    }

    public void creaTeam() {}
}
