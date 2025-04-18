package model;

public class Partecipante extends Utente {
    public Team team;
    public ERuoloTeam ruoloTeam;

    public Partecipante(String nome, String cognome, Piattaforma piattaforma, Team team, ERuoloTeam ruoloTeam) {
        super(nome, cognome, piattaforma);
        this.team = team;
        this.ruoloTeam = ruoloTeam;
    }

    public Partecipante(Piattaforma piattaforma, Team team, ERuoloTeam ruoloTeam) {
        super(piattaforma);
        this.team = team;
        this.ruoloTeam = ruoloTeam;
    }

    public void registra(Piattaforma piattaforma) {
        // registrazione
    }
}
