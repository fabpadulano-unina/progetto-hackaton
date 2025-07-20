package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Rappresenta un hackathon con tutte le sue proprietà.
 * Gestisce organizzatore, giudici e informazioni dell'evento.
 */
public class Hackaton {
    private Integer id;
    private String titolo;
    private String sede;
    private LocalDate dataInizio;
    private LocalDate dataFine;
    private int numMaxIscritti;
    private int dimMaxTeam;
    private boolean registrazioniAperte;
    private LocalDate deadline;
    private String descrizioneProblema;

    private Organizzatore organizzatore;
    private final List<Giudice> giudici = new ArrayList<>();


    /**
     * Crea un hackathon completo con organizzatore e giudici.
     *
     * @param id l'ID dell'hackathon
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dell'evento
     * @param dataInizio la data di inizio
     * @param dataFine la data di fine
     * @param numMaxIscritti il numero massimo di iscritti
     * @param dimMaxTeam la dimensione massima del team
     * @param organizzatore l'organizzatore dell'evento
     * @param giudici la lista dei giudici
     */
    public Hackaton(
            Integer id,
            String titolo,
            String sede,
            LocalDate dataInizio,
            LocalDate dataFine,
            int numMaxIscritti,
            int dimMaxTeam,
            Organizzatore organizzatore,
            List<Giudice> giudici
    ) {
        this.setId(id);
        this.setTitolo(titolo);
        this.setSede(sede);
        this.setDataInizio(dataInizio);
        this.setDataFine(dataFine);
        this.setNumMaxIscritti(numMaxIscritti);
        this.setDimMaxTeam(dimMaxTeam);
        this.setOrganizzatore(organizzatore);
        this.giudici.addAll(giudici);
    }

    /**
     * Crea un hackathon semplificato per la visualizzazione in tabella.
     *
     * @param id l'ID dell'hackathon
     * @param titolo il titolo dell'hackathon
     * @param sede la sede dell'evento
     * @param dataInizio la data di inizio
     * @param dataFine la data di fine
     * @param numMaxIscritti il numero massimo di iscritti
     */
    public Hackaton(
            Integer id,
            String titolo,
            String sede,
            LocalDate dataInizio,
            LocalDate dataFine,
            int numMaxIscritti

    ) {
        this.setId(id);
        this.setTitolo(titolo);
        this.setSede(sede);
        this.setDataInizio(dataInizio);
        this.setDataFine(dataFine);
        this.setNumMaxIscritti(numMaxIscritti);

    }

    /**
     * Restituisce il titolo dell'hackathon.
     *
     * @return il titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Imposta il titolo dell'hackathon.
     *
     * @param titolo il titolo da impostare
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    /**
     * Restituisce la sede dell'hackathon.
     *
     * @return la sede
     */
    public String getSede() {
        return sede;
    }

    /**
     * Imposta la sede dell'hackathon.
     *
     * @param sede la sede da impostare
     */
    public void setSede(String sede) {
        this.sede = sede;
    }

    /**
     * Restituisce la data di inizio dell'hackathon.
     *
     * @return la data di inizio
     */
    public LocalDate getDataInizio() {
        return dataInizio;
    }


    /**
     * Imposta la data di inizio dell'hackathon.
     *
     * @param dataInizio la data di inizio da impostare
     */
    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Restituisce la data di fine dell'hackathon.
     *
     * @return la data di fine
     */
    public LocalDate getDataFine() {
        return dataFine;
    }


    /**
     * Imposta la data di fine dell'hackathon.
     *
     * @param dataFine la data di fine da impostare
     */
    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Restituisce il numero massimo di iscritti.
     *
     * @return il numero massimo di iscritti
     */
    public int getNumMaxIscritti() {
        return numMaxIscritti;
    }

    /**
     * Imposta il numero massimo di iscritti.
     *
     * @param numMaxIscritti il numero massimo di iscritti da impostare
     */
    public void setNumMaxIscritti(int numMaxIscritti) {
        this.numMaxIscritti = numMaxIscritti;
    }

    /**
     * Restituisce la dimensione massima del team.
     *
     * @return la dimensione massima del team
     */
    public int getDimMaxTeam() {
        return dimMaxTeam;
    }

    /**
     * Imposta la dimensione massima del team.
     *
     * @param dimMaxTeam la dimensione massima del team da impostare
     */
    public void setDimMaxTeam(int dimMaxTeam) {
        this.dimMaxTeam = dimMaxTeam;
    }

    /**
     * Restituisce l'organizzatore dell'hackathon.
     *
     * @return l'organizzatore
     */
    public Organizzatore getOrganizzatore() {
        return organizzatore;
    }

    /**
     * Imposta l'organizzatore dell'hackathon.
     *
     * @param organizzatore l'organizzatore da impostare
     */
    public void setOrganizzatore(Organizzatore organizzatore) {
        this.organizzatore = organizzatore;
    }

    /**
     * Restituisce la lista dei giudici.
     *
     * @return la lista dei giudici
     */
    public List<Giudice> getGiudici() {
        return giudici;
    }

    /**
     * Aggiunge un giudice alla lista.
     *
     * @param giudice il giudice da aggiungere
     */
    public void addGiudice(Giudice giudice) {
        this.giudici.add(giudice);
    }

    /**
     * Restituisce l'ID dell'hackathon.
     *
     * @return l'ID
     */
    public Integer getId() {
        return id;
    }


    /**
     * Imposta l'ID dell'hackathon.
     *
     * @param id l'ID da impostare
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Verifica se le registrazioni sono aperte.
     *
     * @return true se le registrazioni sono aperte, false altrimenti
     */
    public boolean isRegistrazioniAperte() {
        return registrazioniAperte;
    }

    /**
     * Imposta lo stato delle registrazioni.
     *
     * @param registrazioniAperte true per aprire le registrazioni, false per chiuderle
     */
    public void setRegistrazioniAperte(boolean registrazioniAperte) {
        this.registrazioniAperte = registrazioniAperte;
    }

    /**
     * Restituisce la deadline per le registrazioni.
     *
     * @return la deadline
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * Imposta la deadline per le registrazioni.
     *
     * @param deadline la deadline da impostare
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


    /**
     * Restituisce la descrizione del problema.
     *
     * @return la descrizione del problema
     */
    public String getDescrizioneProblema() {
        return descrizioneProblema;
    }

    /**
     * Imposta la descrizione del problema.
     *
     * @param descrizioneProblema la descrizione del problema da impostare
     */
    public void setDescrizioneProblema(String descrizioneProblema) {
        this.descrizioneProblema = descrizioneProblema;
    }

}
