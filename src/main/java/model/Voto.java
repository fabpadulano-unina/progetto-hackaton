package model;

public class Voto {
    public Team team;
    public Giudice giudice;
    public int voto;

    public Voto(Team team, Giudice giudice, int voto) {
        this.team = team;
        this.giudice = giudice;
        this.voto = voto;
    }
}
