package model;

public class Commento {
    private final String feedback;
    private final Giudice giudice;
    private final Documento documento;

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

    public void setGiudice(Giudice giudice) {
        this.giudice = giudice;
    }

    public Documento getDocumento() {
        return documento;
    }

}
