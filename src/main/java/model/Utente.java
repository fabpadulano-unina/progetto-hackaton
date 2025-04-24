package model;

public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private Piattaforma piattaforma;

   public Utente(String nome, String cognome, String email, Piattaforma piattaforma) {
       this.setNome(nome);
       this.setCognome(cognome);
       this.setEmail(email);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Piattaforma getPiattaforma() {
        return piattaforma;
    }

    public void setPiattaforma(Piattaforma piattaforma) {
        this.piattaforma = piattaforma;
    }
}
