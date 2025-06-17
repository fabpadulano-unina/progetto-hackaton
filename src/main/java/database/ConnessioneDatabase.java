package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
			String url = "jdbc:postgresql://localhost:5432/mydb";
            connection = DriverManager.getConnection(url, nome, password);

		} catch (ClassNotFoundException ex) {
			out.println("Database Connection Creation Failed : " + ex.getMessage());
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