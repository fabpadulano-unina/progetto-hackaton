package dao.implementazione.postgres;

import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.ConnessioneDatabase.closePs;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private final Connection connection;

    public UtenteImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public void addUtente(String nome, String cognome, String email, String password, String tipoUtente) {
        PreparedStatement createEnumTipoUtentePS = null;
        PreparedStatement createTableUtentePS = null;
        PreparedStatement insertUtentePS = null;

        try {
            createEnumTipoUtentePS = connection.prepareStatement(
                    "CREATE TYPE  tipo_utente AS ENUM ('ORGANIZZATORE', 'PARTECIPANTE', 'GIUDICE')"
            );
            createEnumTipoUtentePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Errore nella creazione del tipo ENUM stato_hackaton", e);
        } finally {
            closePs(createEnumTipoUtentePS);
        }



        try {
            createTableUtentePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Utente (" +
                            "id SERIAL PRIMARY KEY, " +
                            "nome VARCHAR(100) NOT NULL, " +
                            "cognome VARCHAR(100) NOT NULL, " +
                            "email VARCHAR(100) NOT NULL UNIQUE," +
                            "password VARCHAR(100) NOT NULL," +
                            "tipo_utente VARCHAR(100) NOT NULL" +
                            ");"
            );
            createTableUtentePS.executeUpdate();
        }
        catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione della tabella utente", e);
        }
        finally {
            try {
                if(createTableUtentePS != null) createTableUtentePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement create table", e);
            }
        }

        try {
            String insertSQL = "INSERT INTO Utente (nome, cognome, email, password, tipo_utente) " +
                    "VALUES (?, ?, ?, ?, ?)";

            insertUtentePS = connection.prepareStatement(insertSQL);
            insertUtentePS.setString(1, nome);
            insertUtentePS.setString(2, cognome);
            insertUtentePS.setString(3, email);
            insertUtentePS.setString(4, password);
            insertUtentePS.setString(5, tipoUtente);

            insertUtentePS.executeUpdate();
        }
        catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento dell'utente", e);
        }
        finally {
            try {
                if(insertUtentePS != null) insertUtentePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement insert utente", e);
            }
        }

    }

    @Override
    public void getUtente(Integer[] id, String email, String password,
                              StringBuilder nome,
                              StringBuilder cognome,
                              StringBuilder tipoUtente) {
        PreparedStatement loginUtentePS = null;
        try {
            loginUtentePS = connection.prepareStatement(
                    "SELECT id, nome, cognome, tipo_utente FROM Utente WHERE email = ? AND password = ?"
            );
            loginUtentePS.setString(1, email);
            loginUtentePS.setString(2, password);

            var rs = loginUtentePS.executeQuery();
            if (rs.next()) {
                id[0] = rs.getInt("id");
                nome.append(rs.getString("nome"));
                cognome.append(rs.getString("cognome"));
                tipoUtente.append(rs.getString("tipo_utente"));
            }

            rs.close();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel login", e);
        } finally {
            try {
                if (loginUtentePS != null) loginUtentePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura della connection", e);
            }
        }
    }

    @Override
    public void invitaGiudice(Integer idGiudice, Integer hackatonId) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertPS = null;

        try {
            createTablePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Giudici_Hackaton (" +
                            "id SERIAL PRIMARY KEY, " +
                            "id_giudice INTEGER NOT NULL, " +
                            "id_hackaton INTEGER NOT NULL, " +
                            "FOREIGN KEY (id_giudice) REFERENCES Utente(id), " +
                            "FOREIGN KEY (id_hackaton) REFERENCES Hackaton(id), " +
                            "UNIQUE (id_giudice, id_hackaton)" +
                            ");"
            );
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione della tabella Giudici_Hackaton", e);
        } finally {
            try {
                if (createTablePS != null) createTablePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement create table", e);
            }
        }

        try {
            String insertSQL = "INSERT INTO Giudici_Hackaton (id_giudice, id_hackaton) VALUES (?, ?)";
            insertPS = connection.prepareStatement(insertSQL);
            insertPS.setInt(1, idGiudice);
            insertPS.setInt(2, hackatonId);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento del giudice nell'hackaton", e);
        } finally {
            try {
                if (insertPS != null) insertPS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement insert", e);
            }
        }
    }

    @Override
    public void leggiGiudici(List<Integer> ids, List<String> nomi, List<String> cognomi, List<String> email) {
        PreparedStatement selectGiudiciPS = null;
        ResultSet rs = null;

        try {
            String selectSQL = "SELECT id, nome, cognome, email FROM Utente WHERE tipo_utente = ?";
            selectGiudiciPS = connection.prepareStatement(selectSQL);
            selectGiudiciPS.setString(1, "GIUDICE");

            rs = selectGiudiciPS.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                nomi.add(rs.getString("nome"));
                cognomi.add(rs.getString("cognome"));
                email.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella selezione dei giudici", e);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura del ResultSet", e);
            }
            try {
                if (selectGiudiciPS != null) selectGiudiciPS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement select", e);
            }
        }
    }
}
