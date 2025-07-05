package dao;

import java.io.File;
import java.util.List;

public interface DocumentoDAO {
    void addDocumento(Integer idTeam, String descrizione, File file);
    void getDocumentiByTeam(Integer idTeam, List<Integer> ids, List<String> descrizioni);
}
