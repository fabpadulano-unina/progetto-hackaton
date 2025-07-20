package model;

/**
 * Rappresenta un commento o feedback di un giudice su un documento.
 * Contiene il feedback testuale, il giudice che lo ha scritto
 * e il documento a cui si riferisce il commento.
 */
public class Commento {
    private final String feedback;
    private final Giudice giudice;
    private final Documento documento;

    /**
     * Costruttore per creare un commento completo.
     * Inizializza un commento con feedback, giudice e documento associato.
     *
     * @param commento il testo del feedback del giudice
     * @param giudice il giudice che ha scritto il commento
     * @param documento il documento a cui si riferisce il commento
     */
    public Commento(String commento, Giudice giudice, Documento documento) {
        this.feedback = commento;
        this.giudice = giudice;
        this.documento = documento;
    }

    public String getFeedback() {
        return feedback;
    }

    public Giudice getGiudice() {
        return giudice;
    }

    public Documento getDocumento() {
        return documento;
    }

}
