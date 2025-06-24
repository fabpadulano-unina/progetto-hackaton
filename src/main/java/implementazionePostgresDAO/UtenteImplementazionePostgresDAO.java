package implementazionePostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
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


        try {
            PreparedStatement createTableUtentePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Utente (" +
                            "id SERIAL PRIMARY KEY, " +
                            "nome VARCHAR(100) NOT NULL, " +
                            "cognome VARCHAR(100) NOT NULL, " +
                            "email VARCHAR(100) NOT NULL UNIQUE," +
                            "password VARCHAR(100) NOT NULL" +
                            ");"
            );
            createTableUtentePS.executeUpdate();
            PreparedStatement insertUtentePS = connection.prepareStatement(String.format(
                    "INSERT INTO Utente (nome, cognome, email, password) " +
                            "VALUES ('%s', '%s', '%s','%s');",
                    nome.replace("'", "''"),
                    cognome.replace("'", "''"),
                    email.replace("'", "''"),
                    password.replace("'", "''")
            ) );
            insertUtentePS.executeUpdate();
            connection.close();

            return true;
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore durante l'aggiunta dell'utente", e);
            return false;
        }
    }
}
