package implementazionePostgresDAO;

import dao.HackatonDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HackatonImplementazionePostgresDAO implements HackatonDAO {

    private final Connection connection;

    public HackatonImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDatabase.getInstance().getConnection();
    }
    @Override
    public boolean addHackaton(String nome, String sede, LocalDate dataInizio, LocalDate dataFine, int numMaxIscritti, int dimMaxTeam, int idOrganizzatore)  {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Hackaton (" +
                "id SERIAL PRIMARY KEY, " +
                "titolo VARCHAR(100) NOT NULL, " +
                "sede VARCHAR(100) NOT NULL, " +
                "data_inizio DATE NOT NULL, " +
                "data_fine DATE NOT NULL, " +
                "num_max_iscritti INTEGER NOT NULL, " +
                "dim_max_team INTEGER NOT NULL, " +
                "id_organizzatore INTEGER NOT NULL, " +
                "FOREIGN KEY (id_organizzatore) REFERENCES Organizzatore(id)" +
                ");";

        String insertSQL = String.format(
                "INSERT INTO Hackaton (titolo, sede, data_inizio, data_fine, num_max_iscritti, dim_max_team, id_organizzatore) " +
                        "VALUES ('%s', '%s', '%s', '%s', %d, %d, %d);",
                nome,
                sede,
                dataInizio.toString(),
                dataFine.toString(),
                numMaxIscritti,
                dimMaxTeam,
                idOrganizzatore
        );

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(createTableSQL);
            statement.executeUpdate(insertSQL);
            statement.close();
        } catch (SQLException e) {
            return false;
        }
        finally {
            try {
                if  (statement != null) statement.close();
            } catch (SQLException e) {
                //per opprimere sonar warnig
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement", e);
            }
        }


        return true;
    }
}
