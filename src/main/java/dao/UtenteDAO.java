package dao;


public interface UtenteDAO {
    boolean addUtente(String nome, String cognome, String email, String password);
}
