package model;

/**
 * Rappresenta un documento di progresso caricato da un team durante un hackathon.
 * Contiene la descrizione del documento e il riferimento al team che lo ha creato.
 */
public class Documento {
    private Integer id;
    private String descrizione;
    private Team team;

    /**
     * Costruttore per creare un documento con ID e descrizione.
     * Inizializza un documento con i dati essenziali forniti.
     *
     * @param id identificativo univoco del documento
     * @param descrizione descrizione del contenuto del documento
     */
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
