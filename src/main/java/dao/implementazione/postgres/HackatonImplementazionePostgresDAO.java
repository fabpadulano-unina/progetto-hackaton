package dao.implementazione.postgres;

import dao.HackatonDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.ConnessioneDatabase.closePs;
import static database.ConnessioneDatabase.closeResources;


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
                            "registrazioni_aperte BOOLEAN NOT NULL DEFAULT FALSE, " +
                            "deadline_registrazioni DATE, " +
                            "descrizione_problema TEXT, " +
                            "id_organizzatore INTEGER NOT NULL, " +
                            "FOREIGN KEY (id_organizzatore) REFERENCES Utente(id)" +
                            ");"
            );
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,
                    "Errore nella creazione della tabella Hackaton", e);
        } finally {
            closePs(createTablePS);
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
            closeResources(insertPS, generatedKeys);
        }

        return null;
    }

    private void popolaListeHackaton(
            ResultSet rs,
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<Date> dateInizio,
            List<Date> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Boolean> registrazioniAperte,
            List<Date> deadlines,
            List<String> descrizioniProblema,
            List<Integer> idOrganizzatori,
            List<String> nomiOrganizzatori,
            List<String> cognomiOrganizzatori
    ) throws SQLException {
        ids.add(rs.getInt("id"));
        titoli.add(rs.getString("titolo"));
        sedi.add(rs.getString("sede"));
        dateInizio.add(rs.getDate("data_inizio"));
        dateFine.add(rs.getDate("data_fine"));
        numMaxIscritti.add(rs.getInt("num_max_iscritti"));
        dimMaxTeam.add(rs.getInt("dim_max_team"));
        registrazioniAperte.add(rs.getBoolean("registrazioni_aperte"));
        deadlines.add(rs.getDate("deadline_registrazioni"));
        descrizioniProblema.add(rs.getString("descrizione_problema"));
        idOrganizzatori.add(rs.getInt("org_id"));
        nomiOrganizzatori.add(rs.getString("nome"));
        cognomiOrganizzatori.add(rs.getString("cognome"));
    }


    @Override
    public void getHackatons(
            List<Integer> ids,
            List<String> titoli,
            List<String> sedi,
            List<Date> dateInizio,
            List<Date> dateFine,
            List<Integer> numMaxIscritti,
            List<Integer> dimMaxTeam,
            List<Boolean> registrazioniAperte,
            List<Date> deadlines,
            List<String> descrizioniProblema,
            List<Integer> idOrganizzatori,
            List<String> nomiOrganizzatori,
            List<String> cognomiOrganizzatori
    ) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT h.id as id, titolo, sede, data_inizio," +
                    " data_fine, num_max_iscritti, dim_max_team," +
                    " registrazioni_aperte, deadline_registrazioni," +
                    " descrizione_problema, o.id as org_id, o.nome as nome, o.cognome as cognome" +
                    " FROM Hackaton h join Utente o on h.id_organizzatore=o.id ";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                popolaListeHackaton(rs, ids, titoli, sedi, dateInizio, dateFine, numMaxIscritti,
                        dimMaxTeam, registrazioniAperte, deadlines, descrizioniProblema, idOrganizzatori, nomiOrganizzatori, cognomiOrganizzatori);
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore lettura hackaton", e);
        } finally {
            closeResources(ps, rs);
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
            closeResources(ps, rs);
        }
    }

    @Override
    public void apriRegistrazioni(Integer hackatonId, LocalDate deadline) {
        PreparedStatement updatePS = null;

        try {
            String updateSQL = "UPDATE Hackaton SET registrazioni_aperte = TRUE, deadline_registrazioni = ? WHERE id = ?";

            updatePS = connection.prepareStatement(updateSQL);
            updatePS.setDate(1, Date.valueOf(deadline));
            updatePS.setInt(2, hackatonId);

            updatePS.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore durante l'apertura delle registrazioni con deadline", e);
        } finally {
            closePs(updatePS);
        }
    }


    @Override
    public void registraUtente(Integer idUtente, Integer idHackaton) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertPS = null;

        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS registrazione_utente (" +
                    "id_hackaton INTEGER NOT NULL, " +
                    "id_utente INTEGER NOT NULL, " +
                    "PRIMARY KEY (id_hackaton, id_utente), " +
                    "FOREIGN KEY (id_hackaton) REFERENCES Hackaton(id), " +
                    "FOREIGN KEY (id_utente) REFERENCES Utente(id)" +
                    ");";

            createTablePS = connection.prepareStatement(createTableSQL);
            createTablePS.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione della tabella registrazione_utente", e);
        } finally {
            closePs(createTablePS);
        }

        try {
            String insertSQL = "INSERT INTO registrazione_utente (id_hackaton, id_utente) VALUES (?, ?);";

            insertPS = connection.prepareStatement(insertSQL);
            insertPS.setInt(1, idHackaton);
            insertPS.setInt(2, idUtente);
            insertPS.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento della registrazione utente", e);
        } finally {
            closePs(insertPS);
        }
    }

    @Override
    public boolean isUtenteRegistrato(Integer idUtente, Integer idHackaton) {
        PreparedStatement checkPS = null;
        ResultSet resultSet = null;

        try {
            String checkSQL = "SELECT 1 FROM registrazione_utente WHERE id_utente = ? AND id_hackaton = ? LIMIT 1";

            checkPS = connection.prepareStatement(checkSQL);
            checkPS.setInt(1, idUtente);
            checkPS.setInt(2, idHackaton);

            resultSet = checkPS.executeQuery();
            return resultSet.next();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel controllo registrazione utente", e);
            return false;

        } finally {
            closeResources(checkPS, resultSet);
        }
    }

    @Override
    public int getNumeroUtentiRegistrati(Integer idHackaton) {
        PreparedStatement countPS = null;
        ResultSet resultSet = null;

        try {
            String countSQL = "SELECT COUNT(*) FROM registrazione_utente WHERE id_hackaton = ?";

            countPS = connection.prepareStatement(countSQL);
            countPS.setInt(1, idHackaton);

            resultSet = countPS.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel conteggio degli utenti registrati", e);
            return 0;

        } finally {
            closeResources(countPS, resultSet);
        }
    }

    @Override
    public void updateDescrizioneProblema(Integer idHackaton, String descrizioneProblema) {
        PreparedStatement updatePS = null;

        try {
            String updateSQL = "UPDATE Hackaton SET descrizione_problema = ? WHERE id = ?";

            updatePS = connection.prepareStatement(updateSQL);
            updatePS.setString(1, descrizioneProblema);
            updatePS.setInt(2, idHackaton);

            updatePS.executeUpdate();

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore durante l'aggiornamento della descrizione problema", e);

        } finally {
            try {
                if (updatePS != null) updatePS.close();
            } catch (SQLException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement update", e);
            }
        }
    }

}
