package model;

import java.util.ArrayList;
import java.util.List;

public class Giudice extends Utente {
    private List<Hackaton> hackatonGiudicati = new ArrayList<>();

    public Giudice(String nome, String cognome, String email, Hackaton hackaton) {
        super(nome, cognome, email);

    }

    public void pubblicaDescProblema(String descProblema) {}

    public void esaminaDocumento(Documento documento) {}

    private void addHackaton(Hackaton hackaton) {
        hackatonGiudicati.add(hackaton);
        hackaton.addGiudice(this);
    }
}
