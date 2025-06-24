package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private final Connection connection;

    public UtenteImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public void addUtente(String nome, String cognome, String email, String password, String tipoUtente) {
        PreparedStatement createTableUtentePS = null;
        PreparedStatement insertUtentePS = null;
        try {
            createTableUtentePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Utente (" +
                            "id SERIAL PRIMARY KEY, " +
                            "nome VARCHAR(100) NOT NULL, " +
                            "cognome VARCHAR(100) NOT NULL, " +
                            "email VARCHAR(100) NOT NULL UNIQUE," +
                            "password VARCHAR(100) NOT NULL," +
                            "tipo_utente TEXT NOT NULL," +
                            "ruolo_nel_team TEXT" + //solo per partecipante
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
            insertUtentePS = connection.prepareStatement(String.format(
                    "INSERT INTO Utente (nome, cognome, email, password, tipo_utente)" +
                            "VALUES ('%s', '%s', '%s','%s', '%s');",
                    nome.replace("'", "''"),
                    cognome.replace("'", "''"),
                    email.replace("'", "''"),
                    password.replace("'", "''"),
                    tipoUtente
            ));
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
    public void getUtente(String email, String password,
                              StringBuilder nome,
                              StringBuilder cognome,
                              StringBuilder tipoUtente) {
        PreparedStatement loginUtentePS = null;
        try {
            loginUtentePS = connection.prepareStatement(
                    "SELECT nome, cognome, tipo_utente FROM Utente WHERE email = ? AND password = ?"
            );
            loginUtentePS.setString(1, email);
            loginUtentePS.setString(2, password);

            var rs = loginUtentePS.executeQuery();
            if (rs.next()) {
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
}
