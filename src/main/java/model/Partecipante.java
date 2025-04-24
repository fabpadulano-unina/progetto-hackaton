package model;

public class Partecipante extends Utente {
    private Team team;
    private String ruoloTeam;

    public Partecipante(String nome, String cognome, String email, Piattaforma piattaforma) {
        super(nome, cognome, email, piattaforma);
        this.setTeam(team);
        this.setRuoloTeam(ruoloTeam);
    }

    public Partecipante(String nome, String cognome, String email, Piattaforma piattaforma, Team team, String ruoloTeam) {
        super(nome, cognome, email, piattaforma);
        this.setTeam(team);
        this.setRuoloTeam(ruoloTeam);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getRuoloTeam() {
        return ruoloTeam;
    }

    public void setRuoloTeam(String ruoloTeam) {
        this.ruoloTeam = ruoloTeam;
    }

    public void registra(Piattaforma piattaforma) {
        // registrazione
    }

    public void creaTeam() {}
}
