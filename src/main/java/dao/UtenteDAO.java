package dao;


public interface UtenteDAO {
    void addUtente(String nome, String cognome, String email, String password, String tipoUtente);
    void getUtente(Integer[] id, String email, String password,
                       StringBuilder nome,
                       StringBuilder cognome,
                       StringBuilder tipoUtente);

    void invitaGiudice(Integer id, Integer hackatonId);
}
