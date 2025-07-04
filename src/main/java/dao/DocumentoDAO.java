package dao;

import java.util.List;

public interface DocumentoDAO {
    void addDocumento(Integer idTeam, String descrizione);
    void getDocumentiByTeam(Integer idTeam, List<Integer> ids, List<String> descrizioni);
}
