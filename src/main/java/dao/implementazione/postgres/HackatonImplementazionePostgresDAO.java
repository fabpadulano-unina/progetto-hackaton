package dao.implementazione.postgres;

import dao.HackatonDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HackatonImplementazionePostgresDAO implements HackatonDAO {

    private final Connection connection;

    public HackatonImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public Integer addHackaton(String titolo, String sede, LocalDate dataInizio, LocalDate dataFine,
                               int numMaxIscritti, int dimMaxTeam, int idOrganizzatore) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertPS = null;
        ResultSet generatedKeys = null;

        try {
            createTablePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Hackaton (" +
                            "id SERIAL PRIMARY KEY, " +
                            "titolo VARCHAR(100) NOT NULL, " +
                            "sede VARCHAR(100) NOT NULL, " +
                            "data_inizio DATE NOT NULL, " +
                            "data_fine DATE NOT NULL, " +
                            "num_max_iscritti INTEGER NOT NULL, " +
                            "dim_max_team INTEGER NOT NULL, " +
                            "id_organizzatore INTEGER NOT NULL, " +
                            "FOREIGN KEY (id_organizzatore) REFERENCES Utente(id)" +
                            ");"
            );
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Errore nella creazione della tabella Hackaton", e);
        } finally {
            try {
                if (createTablePS != null) createTablePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "Errore nella chiusura dello statement create table", e);
            }
        }

        try {
            String insertSQL = "INSERT INTO Hackaton (titolo, sede, data_inizio, data_fine, " +
                    "num_max_iscritti, dim_max_team, id_organizzatore) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            insertPS = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertPS.setString(1, titolo);
            insertPS.setString(2, sede);
            insertPS.setDate(3, java.sql.Date.valueOf(dataInizio));
            insertPS.setDate(4, java.sql.Date.valueOf(dataFine));
            insertPS.setInt(5, numMaxIscritti);
            insertPS.setInt(6, dimMaxTeam);
            insertPS.setInt(7, idOrganizzatore);

            insertPS.executeUpdate();

            generatedKeys = insertPS.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Errore nell'inserimento dell'Hackaton", e);
        } finally {
            try {
                if (generatedKeys != null) generatedKeys.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "Errore nella chiusura del ResultSet", e);
            }
            try {
                if (insertPS != null) insertPS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                        "Errore nella chiusura dello statement insert", e);
            }
        }

        return null;
    }

    @Override
    public void getHackatons(
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<LocalDate> dateInizio,
            List<LocalDate> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Integer> idOrganizzatori
    ) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT id, titolo, sede, data_inizio, data_fine, num_max_iscritti, dim_max_team, id_organizzatore FROM Hackaton";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                titoli.add(rs.getString("titolo"));
                sedi.add(rs.getString("sede"));
                dateInizio.add(rs.getDate("data_inizio").toLocalDate());
                dateFine.add(rs.getDate("data_fine").toLocalDate());
                numMaxIscritti.add(rs.getInt("num_max_iscritti"));
                dimMaxTeam.add(rs.getInt("dim_max_team"));
                idOrganizzatori.add(rs.getInt("id_organizzatore"));
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore lettura hackaton", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore chiusura risorse", e);
            }
        }
    }


    @Override
    public void leggiInvitiGiudice(List<Integer> idHackaton, List<Integer> idGiudice) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT id_hackaton, id_giudice FROM giudici_hackaton";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                idHackaton.add(rs.getInt("id_hackaton"));
                idGiudice.add(rs.getInt("id_giudice"));
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore lettura inviti giudice", e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura", e);
            }
        }
    }

}
