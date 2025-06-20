package dao;

import java.time.LocalDate;

public interface UtenteDAO {
    boolean addUtente(String nome, String cognome, String email);

}
