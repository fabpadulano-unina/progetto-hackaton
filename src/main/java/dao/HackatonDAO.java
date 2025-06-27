package dao;


import java.time.LocalDate;
import java.util.List;

public interface HackatonDAO {
    Integer addHackaton(String nome, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam,  int idOrganizzatore);
    void getHackatons(
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<LocalDate> dateInizio,
            List<LocalDate> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Boolean> registrazioniAperte,
            List<String> nomiOrganizzatori,
            List<String> cognomiOrganizzatori
    );

    void leggiInvitiGiudice(List<Integer> idHackaton, List<Integer> idGiudice);
    void apriRegistrazioni(Integer hackatonId, LocalDate deadline);
    void registraUtente(Integer idUtente, Integer idHackaton);
    boolean isUtenteRegistrato(Integer idUtente, Integer idHackaton);

}
