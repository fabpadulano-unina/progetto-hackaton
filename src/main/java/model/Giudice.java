package model;

public class Giudice extends Utente {
    public Giudice(String nome, String cognome, String email, Piattaforma piattaforma) {
        super(nome, cognome, email, piattaforma);
    }

    public void pubblicaDescProblema(String descProblema) {}

    public void esaminaDocumento(Documento documento) {}

}
