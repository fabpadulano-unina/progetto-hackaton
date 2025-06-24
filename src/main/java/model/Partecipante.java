package model;

public class Partecipante extends Utente {

    public Partecipante(Integer id, String nome, String cognome, String email, String password ) {
        super(id, nome, cognome, email, password, "PARTECIPANTE");
    }


    public void registra(Hackaton hackaton) {
        hackaton.addPartecipante(this);
    }

    public void creaTeam() {}
}
