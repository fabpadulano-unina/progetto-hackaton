package model;


/**
 * Rappresenta un utente della piattaforma hackathon.
 * Classe base per organizzatori, partecipanti e giudici.
 */
public class Utente {
    private Integer id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private TipoUtente tipoUtente;

    /**
     * Costruttore completo.
     *
     * @param id ID dell'utente
     * @param nome nome dell'utente
     * @param cognome cognome dell'utente
     * @param email email dell'utente
     * @param password password dell'utente
     * @param tipoUtente tipo di utente come stringa
     */
    public Utente(Integer id, String nome, String cognome, String email, String password, String tipoUtente) {
        this.setId(id);
        this.setNome(nome);
        this.setCognome(cognome);
        this.setEmail(email);
        this.setPassword(password);
        this.setTipoUtente(tipoUtente);
    }

    /**
     * @return ID dell'utente
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id ID dell'utente
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return nome dell'utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome nome dell'utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return cognome dell'utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome cognome dell'utente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return email dell'utente
     */
    public String getEmail() {
        return email;
    }


    /**
     * @param email email dell'utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password password dell'utente
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Imposta il tipo di utente convertendo da stringa.
     *
     * @param tipoUtente tipo di utente come stringa
     */
    public void setTipoUtente(String tipoUtente) {
        this.tipoUtente = TipoUtente.fromString(tipoUtente);
    }

    /**
     * @return tipo di utente
     */
    public TipoUtente getTipoUtente() {
        return tipoUtente;
    }

    /**
     * @return true se l'utente è un organizzatore
     */
    public boolean isOrganizzatore() {
        return tipoUtente == TipoUtente.ORGANIZZATORE;
    }

    /**
     * @return true se l'utente è un partecipante
     */
    public boolean isPartecipante() {
        return tipoUtente == TipoUtente.PARTECIPANTE;
    }

    /**
     * @return true se l'utente è un giudice
     */
    public boolean isGiudice() {
        return tipoUtente == TipoUtente.GIUDICE;
    }
}
