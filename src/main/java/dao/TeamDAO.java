package dao;

import java.util.List;

public interface TeamDAO {
    Integer addTeam(Integer idHackaton, String nomeTeam);
    void getTeamByHackaton(Integer idHackaton, List<Integer> ids, List<String> nomiTeam, List<Boolean> isFullList);
    void addPartecipanteAlTeam(Integer idPartecipante, Integer idTeam, Integer idHackaton);
    boolean isPartecipanteInTeam(Integer idTeam, Integer idUtente);
    void getTeamByPartecipante(Integer idPartecipante, List<Integer> idTeam, List<String> nomiTeam, List<Boolean> isFullList);
    void deletePartecipanteNelTeam(Integer idPartecipante, Integer idHackaton);
    void getPartecipantiByTeam(Integer idTeam, List<String> nomiPartecipanti, List<String> cognomiPartecipanti);
    Integer getHackatonIdByTeamId(Integer idTeam);
    void addTeamVoto(Integer idGiudice, Integer idTeam, int voto);
    boolean giudiceHaVotatoInHackaton(Integer idGiudice, Integer idHackaton);
    boolean giudiceHaVotatoTeam(Integer idGiudice, Integer idTeam);
    void getVotiDelGiudice(Integer idGiudice, Integer idHackaton, List<String> nomiTeam, List<Integer> voti);
}
