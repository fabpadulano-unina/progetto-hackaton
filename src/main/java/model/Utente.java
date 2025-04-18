package model;

public class Utente {
    public String nome;
    public String cognome;
    public Piattaforma piattaforma;

   public Utente(String nome, String cognome, Piattaforma piattaforma) {
       this.nome = nome;
       this.cognome = cognome;
       this.piattaforma = piattaforma;
   }

   public Utente(Piattaforma piattaforma) {
       this.piattaforma = piattaforma;
   }



}
