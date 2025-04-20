package model;

public class Voto {
    private Team team;
    private Giudice giudice;
    private int voto;

    public Voto(Team team, Giudice giudice, int voto) {
        this.setTeam(team);
        this.setGiudice(giudice);
        this.setVoto(voto);
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
        return voto;
    }

    public void setVoto(int voto) {
        this.voto = voto;
    }
}
