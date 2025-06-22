package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UtenteImplementazionePostgresDAO implements UtenteDAO {

    private final Connection connection;

    public UtenteImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public boolean addUtente(String nome, String cognome, String email, String password) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Utente (" +
                "id SERIAL PRIMARY KEY, " +
                "nome VARCHAR(100) NOT NULL, " +
                "cognome VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) NOT NULL UNIQUE" +
                "password VARCHAR(100) NOT NULL" +
                ");";

        String insertSQL = String.format(
                "INSERT INTO Utente (nome, cognome, email, password) " +
                        "VALUES ('%s', '%s', '%s');",
                nome.replace("'", "''"),
                cognome.replace("'", "''"),
                email.replace("'", "''"),
                password.replace("'", "''")

        );

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(createTableSQL);
            statement.executeUpdate(insertSQL);

            return true;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement", e);
            }
        }
    }
}
