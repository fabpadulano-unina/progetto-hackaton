package model;

public class Giudice extends Utente {
    public Giudice(String nome, String cognome, String email, Hackaton hackaton) {
        super(nome, cognome, email, hackaton);
    }

    public void pubblicaDescProblema(String descProblema) {}

    public void esaminaDocumento(Documento documento) {}

}
