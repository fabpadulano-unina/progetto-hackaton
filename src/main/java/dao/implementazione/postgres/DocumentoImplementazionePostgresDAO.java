package dao.implementazione.postgres;

import dao.DocumentoDAO;
import database.ConnessioneDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.ConnessioneDatabase.closePs;
import static database.ConnessioneDatabase.closeResources;


public class DocumentoImplementazionePostgresDAO implements DocumentoDAO {

    private final Connection connection;

    public DocumentoImplementazionePostgresDAO() throws SQLException {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public void addDocumento(Integer idTeam, String descrizione, File file) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertPS = null;

        try {
            createTablePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Documento (" +
                            "id SERIAL PRIMARY KEY, " +
                            "id_team INTEGER NOT NULL, " +
                            "descrizione TEXT NOT NULL, " +
                            "file BYTEA, " +
                            "FOREIGN KEY (id_team) REFERENCES Team(id)" +
                            ");"
            );
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore creazione tabella Documento", e);
        } finally {
            closePs(createTablePS);
        }

        try {
            insertPS = connection.prepareStatement(
                    "INSERT INTO Documento (id_team, descrizione, file) VALUES (?, ?, ?)"
            );
            insertPS.setInt(1, idTeam);
            insertPS.setString(2, descrizione);
            insertFile(file, insertPS);

            insertPS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore inserimento documento", e);
        } finally {
            closePs(insertPS);
        }
    }

    private void insertFile(File file, PreparedStatement insertPS) throws SQLException {
            try (InputStream fileInputStream = new FileInputStream(file)) {
                insertPS.setBinaryStream(3, fileInputStream, (int) file.length());
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella lettura del file", e);
            }
    }

    @Override
    public void getDocumentiByTeam(Integer idTeam, List<Integer> ids, List<String> descrizioni) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id, descrizione FROM Documento WHERE id_team = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idTeam);
            rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                descrizioni.add(rs.getString("descrizione"));
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore recupero documenti", e);
        } finally {
            closeResources(ps, rs);
        }
    }
}
