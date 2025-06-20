package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.*;

public class ConnessioneDatabase {

	// ATTRIBUTI
	private static ConnessioneDatabase instance;
	private Connection connection = null;

    // COSTRUTTORE
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


	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}

		return instance;
	}

	public Connection getConnection() {
		return connection;
	}
}