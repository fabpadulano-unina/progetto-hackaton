package dao;


import java.time.LocalDate;

public interface HackatonDAO {
    boolean addHackaton(String nome, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, int idOrganizzatore);
}
