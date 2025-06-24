package implementazionePostgresDAO;

import dao.HackatonDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

        try {
            PreparedStatement createTableHackatonPS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Hackaton (" +
                            "id SERIAL PRIMARY KEY, " +
                            "titolo VARCHAR(100) NOT NULL, " +
                            "sede VARCHAR(100) NOT NULL, " +
                            "data_inizio DATE NOT NULL, " +
                            "data_fine DATE NOT NULL, " +
                            "num_max_iscritti INTEGER NOT NULL, " +
                            "eta_min INTEGER NOT NULL, " +
                            "eta_max INTEGER NOT NULL, " +
                            "id_organizzatore INTEGER NOT NULL, " +
                            "FOREIGN KEY (id_organizzatore) REFERENCES Organizzatore(id)" +
                            ")"
            );
            createTableHackatonPS.executeUpdate();
            PreparedStatement insertHackatonPS = connection.prepareStatement(String.format(
                            "INSERT INTO Hackaton (titolo, sede, data_inizio, data_fine, num_max_iscritti, dim_max_team, id_organizzatore) " +
                                    "VALUES ('%s', '%s', '%s', '%s', %d, %d, %d);",
                            nome,
                            sede,
                            dataInizio.toString(),
                            dataFine.toString(),
                            numMaxIscritti,
                            dimMaxTeam,
                            idOrganizzatore
                    )
            );
            insertHackatonPS.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore durante l'aggiunta dell'Hackaton", e);

            return false;
        }



        return true;
    }
}
