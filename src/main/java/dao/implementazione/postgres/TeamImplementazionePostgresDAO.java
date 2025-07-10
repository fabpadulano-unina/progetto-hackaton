package dao.implementazione.postgres;

import dao.TeamDAO;
import database.ConnessioneDatabase;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static database.ConnessioneDatabase.closePs;
import static database.ConnessioneDatabase.closeResources;

public class TeamImplementazionePostgresDAO implements TeamDAO {

    private final Connection connection;

    public TeamImplementazionePostgresDAO() throws SQLException {
        this.connection = ConnessioneDatabase.getInstance().getConnection();
    }

    @Override
    public Integer addTeam(Integer idHackaton, String nomeTeam) {
        PreparedStatement createTableTeamPS = null;
        PreparedStatement insertTeamPS = null;
        ResultSet rs = null;
        Integer idTeam = null;

        try {
            createTableTeamPS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Team (" +
                            "id SERIAL PRIMARY KEY, " +
                            "nome VARCHAR(100) NOT NULL, " +
                            "id_hackaton INTEGER NOT NULL, " +
                            "UNIQUE (nome, id_hackaton), " +
                            "FOREIGN KEY (id_hackaton) REFERENCES Hackaton(id)" +
                            ");"
            );
            createTableTeamPS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione della tabella Team", e);
        } finally {
            closePs(createTableTeamPS);
        }

        try {
            String insertSQL = "INSERT INTO Team (nome, id_hackaton) VALUES (?, ?);";
            insertTeamPS = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            insertTeamPS.setString(1, nomeTeam);
            insertTeamPS.setInt(2, idHackaton);
            insertTeamPS.executeUpdate();

            rs = insertTeamPS.getGeneratedKeys();
            if (rs.next()) {
                idTeam = rs.getInt(1);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento del Team", e);
        } finally {
            closeResources(insertTeamPS, rs);
        }

        return idTeam;
    }

    @Override
    public void getTeamByHackaton(Integer idHackaton, List<Integer> ids, List<String> nomiTeam, List<Boolean> isFullList) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // LEFT JOIN permette di contare anche i team senza partecipanti (conta = 0)
            String sql =
                    "SELECT t.id as id, t.nome as nome, " +
                            "       COUNT(pt.id_partecipante) AS partecipanti, " +
                            "       h.dim_max_team as dim_max_team " +
                            "FROM Team t " +
                            "LEFT JOIN Partecipante_Team pt ON t.id = pt.id_team AND pt.id_hackaton = t.id_hackaton " +
                            "JOIN Hackaton h ON t.id_hackaton = h.id " +
                            "WHERE t.id_hackaton = ? " +
                            "GROUP BY t.id, t.nome, h.dim_max_team";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, idHackaton);
            rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("id"));
                nomiTeam.add(rs.getString("nome"));

                int partecipanti = rs.getInt("partecipanti");
                int maxIscritti = rs.getInt("dim_max_team");
                isFullList.add(partecipanti >= maxIscritti);
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella lettura dei team", e);
        } finally {
            closeResources(ps, rs);
        }
    }

    @Override
    public void addPartecipanteAlTeam(Integer idPartecipante, Integer idTeam, Integer idHackaton) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertPS = null;

        try {
            createTablePS = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS Partecipante_Team (" +
                            "id_partecipante INTEGER NOT NULL, " +
                            "id_team INTEGER NOT NULL, " +
                            "id_hackaton INTEGER NOT NULL, " +
                            "UNIQUE (id_partecipante, id_hackaton), " +
                            "FOREIGN KEY (id_partecipante) REFERENCES Utente(id), " +
                            "FOREIGN KEY (id_team) REFERENCES Team(id), " +
                            "FOREIGN KEY (id_hackaton) REFERENCES Hackaton(id)" +
                            ");"
            );
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore creazione tabella Partecipante_Team", e);
        } finally {
            closePs(createTablePS);
        }

        try {
            String insertSQL = "INSERT INTO Partecipante_Team (id_partecipante, id_team, id_hackaton) VALUES (?, ?, ?)";
            insertPS = connection.prepareStatement(insertSQL);
            insertPS.setInt(1, idPartecipante);
            insertPS.setInt(2, idTeam);
            insertPS.setInt(3, idHackaton);
            insertPS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore inserimento relazione Partecipante_Team", e);
        } finally {
            closePs(insertPS);
        }
    }

    @Override
    public boolean isPartecipanteInTeam(Integer idTeam, Integer idUtente) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean presente = false;

        try {
            String checkSQL = "SELECT 1 FROM Partecipante_Team WHERE id_team = ? AND id_partecipante = ?";
            ps = connection.prepareStatement(checkSQL);
            ps.setInt(1, idTeam);
            ps.setInt(2, idUtente);
            rs = ps.executeQuery();
            if (rs.next()) {
                presente = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel check partecipante in team", e);
        } finally {
            closeResources(ps, rs);
        }

        return presente;
    }

    @Override
    public void getTeamByPartecipante(Integer idPartecipante, List<Integer> idTeam, List<String> nomiTeam, List<Boolean> isFullList) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                    "SELECT t.id as id, t.nome as nome, COUNT(pt2.id_partecipante) AS partecipanti, h.dim_max_team as dim_max_team " +
                            "FROM Team t " +
                            "JOIN Partecipante_Team pt ON t.id = pt.id_team AND t.id_hackaton = pt.id_hackaton " +
                            "LEFT JOIN Partecipante_Team pt2 ON t.id = pt2.id_team AND t.id_hackaton = pt2.id_hackaton " +
                            "JOIN Hackaton h ON t.id_hackaton = h.id " +
                            "WHERE pt.id_partecipante = ? AND CURRENT_DATE BETWEEN h.data_inizio AND h.data_fine " +
                            "GROUP BY t.id, t.nome, h.dim_max_team";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, idPartecipante);
            rs = ps.executeQuery();

            while (rs.next()) {
                idTeam.add(rs.getInt("id"));
                nomiTeam.add(rs.getString("nome"));
                int partecipanti = rs.getInt("partecipanti");
                int maxIscritti = rs.getInt("dim_max_team");
                isFullList.add(partecipanti >= maxIscritti);
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero dei team per partecipante", e);
        } finally {
            closeResources(ps, rs);
        }
    }



    @Override
    public void deletePartecipanteNelTeam(Integer idPartecipante, Integer idHackaton) {
        PreparedStatement checkPs = null;
        PreparedStatement deletePs = null;
        ResultSet rs = null;

        try {
            String checkSql = "SELECT 1 FROM Partecipante_Team " +
                    "WHERE id_partecipante = ? AND id_hackaton = ?";
            checkPs = connection.prepareStatement(checkSql);
            checkPs.setInt(1, idPartecipante);
            checkPs.setInt(2, idHackaton);
            rs = checkPs.executeQuery();

            if (rs.next()) {
                String deleteSql = "DELETE FROM Partecipante_Team " +
                        "WHERE id_partecipante = ? AND id_hackaton = ?";
                deletePs = connection.prepareStatement(deleteSql);
                deletePs.setInt(1, idPartecipante);
                deletePs.setInt(2, idHackaton);
                deletePs.executeUpdate();
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella rimozione del partecipante dal team", e);
        } finally {
            closeResources(checkPs, rs);
            closePs(deletePs);
        }
    }


    @Override
    public void getPartecipantiByTeam(
            Integer idTeam,
            List<String> nomiPartecipanti,
            List<String> cognomiPartecipanti
    ) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql =
                    "SELECT u.nome as nome, u.cognome as cognome " +
                            "FROM Utente u " +
                            "JOIN Partecipante_Team pt ON u.id = pt.id_partecipante " +
                            "WHERE pt.id_team = ?";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, idTeam);
            rs = ps.executeQuery();

            while (rs.next()) {
                nomiPartecipanti.add(rs.getString("nome"));
                cognomiPartecipanti.add(rs.getString("cognome"));
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero dei partecipanti del team", e);
        } finally {
            closeResources(ps, rs);
        }
    }

    @Override
    public Integer getHackatonIdByTeamId(Integer idTeam) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT id_hackaton FROM Team WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idTeam);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_hackaton");
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero dell'idHackaton dal team", e);
        } finally {
            closeResources(ps, rs);
        }

        return null;
    }

    @Override
    public void addTeamVoto(Integer idGiudice, Integer idTeam, int voto) {
        PreparedStatement createTablePS = null;
        PreparedStatement insertVotePS = null;

        try {
            String createSQL = "CREATE TABLE IF NOT EXISTS Voto_Team_Giudice (" +
                    "id_giudice INTEGER NOT NULL, " +
                    "id_team INTEGER NOT NULL, " +
                    "voto INTEGER NOT NULL CHECK (voto BETWEEN 0 AND 10), " +
                    "PRIMARY KEY (id_giudice, id_team), " +
                    "FOREIGN KEY (id_giudice) REFERENCES Utente(id), " +
                    "FOREIGN KEY (id_team) REFERENCES Team(id)" +
                    ");";

            createTablePS = connection.prepareStatement(createSQL);
            createTablePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione tabella voto", e);
        } finally {
            closePs(createTablePS);
        }

        try {
            String insertSQL = "INSERT INTO Voto_Team_Giudice (id_giudice, id_team, voto) VALUES (?, ?, ?)";
            insertVotePS = connection.prepareStatement(insertSQL);
            insertVotePS.setInt(1, idGiudice);
            insertVotePS.setInt(2, idTeam);
            insertVotePS.setInt(3, voto);
            insertVotePS.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nell'inserimento voto", e);
        } finally {
            closePs(insertVotePS);
        }
    }

    @Override
    public boolean giudiceHaVotatoInHackaton(Integer idGiudice, Integer idHackaton) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT 1 " +
                    "FROM Voto_Team_Giudice vtg " +
                    "JOIN Team t ON vtg.id_team = t.id " +
                    "WHERE t.id_hackaton = ? AND vtg.id_giudice = ? " +
                    "LIMIT 1";

            ps = connection.prepareStatement(sql);
            ps.setInt(1, idHackaton);
            ps.setInt(2, idGiudice);

            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel controllo voti del giudice", e);
            return false;
        } finally {
            closeResources(ps, rs);
        }
    }

    @Override
    public boolean giudiceHaVotatoTeam(Integer idGiudice, Integer idTeam) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT 1 FROM voto_team_giudice WHERE id_giudice = ? AND id_team = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idGiudice);
            ps.setInt(2, idTeam);
            rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore durante la verifica del voto del giudice", e);
            return false;
        } finally {
            closeResources(ps, rs);
        }
    }

    @Override
    public void getVotiDelGiudice(Integer idGiudice, Integer idHackaton, List<String> nomiTeam, List<Integer> voti) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT t.nome, vt.voto " +
                    "FROM voto_team_giudice vt " +
                    "JOIN Team t ON vt.id_team = t.id " +
                    "WHERE vt.id_giudice = ? AND t.id_hackaton = ?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, idGiudice);
            ps.setInt(2, idHackaton);
            rs = ps.executeQuery();

            while (rs.next()) {
                nomiTeam.add(rs.getString("nome"));
                voti.add(rs.getInt("voto"));
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nel recupero voti del giudice", e);
        } finally {
            closeResources(ps, rs);
        }
    }

}

