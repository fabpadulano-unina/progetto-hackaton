package dao.implementazione.postgres;

import dao.DocumentoDAO;
import database.ConnessioneDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
                insertPS.setBytes(3, Files.readAllBytes(file.toPath()));
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

    @Override
    public void getDocumentiByHackaton(Integer idHackaton, List<Integer> idDocumenti, List<String> descrizioni, List<Integer> idTeam, List<String> nomiTeam) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT d.id, d.descrizione, t.id as id_team, t.nome as nome_team " +
                    "FROM Documento d " +
                    "JOIN Team t ON d.id_team = t.id " +
                    "WHERE t.id_hackaton = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idHackaton);
            rs = ps.executeQuery();

            while (rs.next()) {
                idDocumenti.add(rs.getInt("id"));
                descrizioni.add(rs.getString("descrizione"));
                idTeam.add(rs.getInt("id_team"));
                nomiTeam.add(rs.getString("nome_team"));
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero documenti per hackaton", e);
        } finally {
            closeResources(ps, rs);
        }
    }

    @Override
    public void inserisciFeedbackGiudice(Integer idGiudice, Integer idDocumento, String feedback) {
        PreparedStatement psCreate = null;
        PreparedStatement psInsert = null;

        try {
            psCreate = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Feedback_Documento (" +
                            "id_giudice INTEGER NOT NULL, " +
                            "id_documento INTEGER NOT NULL, " +
                            "feedback TEXT NOT NULL, " +
                            "PRIMARY KEY (id_giudice, id_documento), " +
                            "FOREIGN KEY (id_giudice) REFERENCES Utente(id), " +
                            "FOREIGN KEY (id_documento) REFERENCES Documento(id))"
            );
            psCreate.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione tabella Feedback_Documento", e);
        } finally {
            closePs(psCreate);
        }

        try {
            psInsert = connection.prepareStatement(
                    "INSERT INTO Feedback_Documento (id_giudice, id_documento, feedback) VALUES (?, ?, ?)"
            );
            psInsert.setInt(1, idGiudice);
            psInsert.setInt(2, idDocumento);
            psInsert.setString(3, feedback);
            psInsert.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento feedback", e);
        } finally {
            closePs(psInsert);
        }
    }

    @Override
    public boolean haDatoFeedback(Integer idGiudice, Integer idDocumento) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT 1 FROM Feedback_Documento WHERE id_giudice = ? AND id_documento = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, idGiudice);
            ps.setInt(2, idDocumento);

            rs = ps.executeQuery();
            return rs.next();  // se c'è almeno una riga, ha già dato feedback
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel controllo feedback", e);
            return false;
        } finally {
            closeResources(ps, rs);
        }
    }


    @Override
    public void getFeedbackByDocumento(Integer idDocumento, List<String> nomiGiudici, List<String> cognomiGiudici, List<String> feedbacks) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT u.nome, u.cognome, fd.feedback " +
                    "FROM feedback_documento fd " +
                    "JOIN Utente u ON fd.id_giudice = u.id " +
                    "WHERE fd.id_documento = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, idDocumento);

            rs = ps.executeQuery();

            while (rs.next()) {
                nomiGiudici.add(rs.getString("nome"));
                cognomiGiudici.add(rs.getString("cognome"));
                feedbacks.add(rs.getString("feedback"));
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero dei feedback per documento", e);
        } finally {
            closeResources(ps, rs);
        }
    }

}
