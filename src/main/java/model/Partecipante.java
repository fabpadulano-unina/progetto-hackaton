package model;

import java.util.List;

public class Partecipante extends Utente {
    private List<Hackaton> hackatons;

    public Partecipante(Integer id, String nome, String cognome, String email, String password, List<Hackaton> hackatons) {
        super(id, nome, cognome, email, password, "PARTECIPANTE");
        this.hackatons = hackatons;
    }


    public void registra(Hackaton hackaton) {
        hackaton.addPartecipante(this);
    }

    public void creaTeam() {}

    public List<Hackaton> getHackatons() {
        return hackatons;
    }

}
