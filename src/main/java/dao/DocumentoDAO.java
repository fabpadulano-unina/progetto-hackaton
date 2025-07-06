package dao;

import java.io.File;
import java.util.List;

public interface DocumentoDAO {
    void addDocumento(Integer idTeam, String descrizione, File file);
    void getDocumentiByTeam(Integer idTeam, List<Integer> ids, List<String> descrizioni);
    void getDocumentiByHackaton(Integer idHackaton, List<Integer> idDocumenti, List<String> descrizioni, List<Integer> idTeam, List<String> nomiTeam);

}
