package model;

public class Documento {
    private String descrizione;
    private Team team;

    public Documento(String descrizione, Team team) {
        this.setDescrizione(descrizione);
        this.setTeam(team);
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
