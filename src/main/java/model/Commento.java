package model;

public class Commento {
    private String commento;
    private Giudice giudice;
    private Documento documento;

    public Commento(String commento, Giudice giudice, Documento documento) {
        this.commento = commento;
        this.giudice = giudice;
        this.documento = documento;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
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

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }
}
