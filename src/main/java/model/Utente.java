package model;




public class Utente {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private TipoUtente tipoUtente;

    public Utente(Integer id, String nome, String cognome, String email, String password, String tipoUtente) {
        this.setId(id);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPassword(password);
        this.setTipoUtente(tipoUtente);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setTipoUtente(String tipoUtente) {
        this.tipoUtente = TipoUtente.fromString(tipoUtente);
    }

    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    public boolean isOrganizzatore() {
        return tipoUtente == TipoUtente.ORGANIZZATORE;
    }

    public boolean isPartecipante() {
        return tipoUtente == TipoUtente.PARTECIPANTE;
    }

    public boolean isGiudice() {
        return tipoUtente == TipoUtente.GIUDICE;
    }
}
