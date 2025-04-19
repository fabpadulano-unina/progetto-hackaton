package model;

public class Giudice extends Utente {
    public Giudice(String nome, String cognome, Piattaforma piattaforma) {
        super(nome, cognome, piattaforma);
    }

    public void pubblicaDescProblema(String descProblema) {}

    public void esaminaDocumento(Documento documento) {}

}
