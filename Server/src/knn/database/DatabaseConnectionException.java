package knn.database;
/**
 * Eccezione controllata che verifica se è possibile connettersi al Database oppure no. 
 * Viene sollevata nei casi in cui:
 * 1) è errato il nome del database 
 * 2) è errato il nome utente
 * 3) è errata la password dell'utente
 * 4) è errata la porta
 * 5) è errato il server
 * 6) è errato il JDBC
 */
public class DatabaseConnectionException extends Exception {

	/**
	 * Costruttore di classe.
	 * 
	 * Richiama il costruttore della super-classe che permette di sollevare
	 * un'eccezione con un messaggio passato come parametro.
	 * 
	 * @param msg Messaggio passato al momento del sollevamento dell'eccezione.
	 */
	DatabaseConnectionException(String msg) {
		super(msg);
	}
}
