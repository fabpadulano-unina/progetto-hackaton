package model;

public enum TipoUtente {
    ORGANIZZATORE, PARTECIPANTE, GIUDICE;

    public static TipoUtente fromString(String value) {
        return valueOf(value);
    }
}