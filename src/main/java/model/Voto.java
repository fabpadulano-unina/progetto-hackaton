package model;

public class Voto {
    private Team team;
    private Giudice giudice;
    private int votoAssegnato;

    public Voto(Team team, Giudice giudice, int votoAssegnato) {
        this.setTeam(team);
        this.setGiudice(giudice);
        this.setVoto(votoAssegnato);
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Giudice getGiudice() {
        return giudice;
    }

    public void setGiudice(Giudice giudice) {
        this.giudice = giudice;
    }

    public int getVoto() {
        return votoAssegnato;
    }

    public void setVoto(int votoAssegnato) {
        this.votoAssegnato = votoAssegnato;
    }
}
