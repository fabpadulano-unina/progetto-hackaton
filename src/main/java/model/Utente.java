package model;

public class Utente {
    private String nome;
    private String cognome;
    private Piattaforma piattaforma;

   public Utente(String nome, String cognome, Piattaforma piattaforma) {
       this.setNome(nome);
       this.setCognome(cognome);
       this.setPiattaforma(piattaforma);
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

    public Piattaforma getPiattaforma() {
        return piattaforma;
    }

    public void setPiattaforma(Piattaforma piattaforma) {
        this.piattaforma = piattaforma;
    }
}
