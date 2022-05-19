package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 * @author Map Tutor
 *
 */
public class DbAccess {

	//private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";		Non necessario
	private final String DBMS = "jdbc:mysql";
	private final String SERVER = "localhost";
	private final int PORT = 3306;
	private final String DATABASE = "map";
	private final String USER_ID = "map";
	private final String PASSWORD = "map";

	private Connection conn;

	/**
	 * Inizializza una connessione al DB
	 */
	public DbAccess() throws DatabaseConnectionException{
		String connectionString =  DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
		// non pi� richiesto con la versione 6 del driver jdbc
		
		/*try {
			
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} 
		catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new DatabaseConnectionException(e.toString());
			}
		catch (InstantiationException e) {
					e.printStackTrace();
					throw new DatabaseConnectionException(e.toString());
			} 
		catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}
		*/
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}
	
	public  Connection getConnection(){
		return conn;
	}

	public  void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}

}
