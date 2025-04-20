package model;

public class Partecipante extends Utente {
    private Team team;
    private ERuoloTeam ruoloTeam;

    public Partecipante(String nome, String cognome, Piattaforma piattaforma, Team team, ERuoloTeam ruoloTeam) {
        super(nome, cognome, piattaforma);
        this.setTeam(team);
        this.setRuoloTeam(ruoloTeam);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public ERuoloTeam getRuoloTeam() {
        return ruoloTeam;
    }

    public void setRuoloTeam(ERuoloTeam ruoloTeam) {
        this.ruoloTeam = ruoloTeam;
    }

    public void registra(Piattaforma piattaforma) {
        // registrazione
    }
}
