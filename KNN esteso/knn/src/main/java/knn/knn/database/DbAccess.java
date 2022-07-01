package knn.knn.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * 
 * @author Map Tutor
 */
public class DbAccess {

	/** Libreria utilizzata per connettersi al db. */
	private final String DBMS = "jdbc:mysql";

	/** Indirizzo IP del database. */
	private final String SERVER = "localhost";

	/** Porta del database. */
	private final int PORT = 3306;

	/** Nome del database. */
	private final String DATABASE = "map";

	/** Nome dell'utente per connettersi al database. */
	private final String USER_ID = "map";

	/** Password dell'utente per connettersi al database. */
	private final String PASSWORD = "map";

	/** Istanza di connessione al database. */
	private Connection conn;

	/**
	 * Inizializza una connessione al DB
	 * 
	 * @throws DatabaseConnectionException Eccezione controllata sollevata nel caso
	 *                                     in cui non dovesse riuscire la
	 *                                     connessione al database.
	 */
	public DbAccess() throws DatabaseConnectionException {
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
	}

	/**
	 * Restituisce la connessione del database
	 * 
	 * @return connessione del database
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Chiude la connessione del database
	 */
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}

}
