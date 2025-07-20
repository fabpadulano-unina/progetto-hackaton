package database;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.*;

/**
 * Gestisce la connessione al database PostgreSQL dell'applicazione hackathon.
 */
public class ConnessioneDatabase {

	// ATTRIBUTI
	private static ConnessioneDatabase instance;
	private Connection connection = null;

	/**
	 * Crea una nuova connessione al database PostgreSQL.
	 * Stabilisce la connessione e crea lo schema se non esiste.
	 *
	 * @throws SQLException se la connessione al database fallisce
	 */

	private ConnessioneDatabase() throws SQLException {
		try {
            String driver = "org.postgresql.Driver";
            Class.forName(driver);
			String nome = "myuser";
			String password = "mypassword";
			String url = "jdbc:postgresql://localhost:5432/hackaton?currentSchema=hackaton";
            connection = DriverManager.getConnection(url, nome, password);

			setSchema();

		} catch (ClassNotFoundException ex) {
			out.println("Database Connection Creation Failed : " + ex.getMessage());
		}

	}

	/**
	 * Crea lo schema hackathon nel database se non esiste già.
	 * Utilizza uno statement SQL per creare lo schema in modo sicuro.
	 */
	private void setSchema() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.execute("CREATE SCHEMA IF NOT EXISTS hackaton;");
		} catch (SQLException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella creazione dello schema", e);
		} finally {
			try {
				if (stmt != null) stmt.close();
			} catch (SQLException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Errore nella chiusura dello statement", e);
			}
		}
	}

	/**
	 * Restituisce l'istanza della connessione al database.
	 * Crea una nuova istanza se non esiste o se la connessione è chiusa.
	 *
	 * @return l'istanza unica di ConnessioneDatabase
	 * @throws SQLException se la creazione della connessione fallisce
	 */
	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}

		return instance;
	}

	/**
	 * Restituisce la connessione al database attiva.
	 *
	 * @return la connessione al database PostgreSQL
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Chiude in modo sicuro un PreparedStatement.
	 * Gestisce eventuali eccezioni durante la chiusura.
	 *
	 * @param ps il PreparedStatement da chiudere
	 */
	public static void closePs(PreparedStatement ps) {
		try {
			if (ps != null) ps.close();
		} catch (SQLException e) {
			Logger.getLogger(ConnessioneDatabase.class.getName()).log(Level.SEVERE, "Errore nella chiusura del PreparedStatement ", e);
		}
	}

	/**
	 * Chiude in modo sicuro PreparedStatement e ResultSet.
	 * Gestisce eventuali eccezioni durante la chiusura delle risorse.
	 *
	 * @param ps il PreparedStatement da chiudere
	 * @param rs il ResultSet da chiudere
	 */
	public static void closeResources(PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null) ps.close();
		} catch (SQLException e) {
			Logger.getLogger(ConnessioneDatabase.class.getName()).log(Level.SEVERE, "Errore chiusura ps", e);
		}

		try {
			if (rs != null) rs.close();
		} catch (SQLException e) {
			Logger.getLogger(ConnessioneDatabase.class.getName()).log(Level.SEVERE, "Errore chiusura result set", e);
		}
	}

}