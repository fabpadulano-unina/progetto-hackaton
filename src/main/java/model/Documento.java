package model;

public class Documento {
    private Integer id;
    private String descrizione;
    private Team team;

    public Documento(Integer id, String descrizione) {
        this.setId(id);
        this.setDescrizione(descrizione);
    }

    public String getDescrizione() {
        return descrizione;
    }

    private void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
