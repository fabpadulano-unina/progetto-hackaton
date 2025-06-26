package dao;


import java.time.LocalDate;
import java.util.List;

public interface HackatonDAO {
    Integer addHackaton(String nome, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, int idOrganizzatore);
    void getHackatons(
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<LocalDate> dateInizio,
            List<LocalDate> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Integer> idOrganizzatori
    );

    void leggiInvitiGiudice(List<Integer> idHackaton, List<Integer> idGiudice);

}
