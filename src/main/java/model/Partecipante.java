package model;

public class Partecipante extends Utente {
    private Team team;
    private String ruoloTeam;

    public Partecipante(String nome, String cognome, String email, String password, Hackaton hackaton) {
        super(nome, cognome, email, password, "PARTECIPANTE");
        this.setTeam(team);
        this.setRuoloTeam(ruoloTeam);
        hackaton.addPartecipante(this);
    }

    public Partecipante(String nome, String cognome, String email, String password, Hackaton hackaton, Team team, String ruoloTeam) {
        super(nome, cognome, email, password, "PARTECIPANTE");
        this.setTeam(team);
        this.setRuoloTeam(ruoloTeam);
        hackaton.addPartecipante(this);
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

    public void registra(Hackaton hackaton) {
        // registrazione
    }

    public void creaTeam() {}
}
