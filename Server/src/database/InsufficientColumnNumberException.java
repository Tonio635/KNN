package database;

/**
 * Classe per l'eccezione controllata sollevata nel caso in cui non si riesca
 * a caricare la colonna dei target dal database o nel caso in cui la tabella
 * del database sia senza colonne.
 */
public class InsufficientColumnNumberException extends Exception {

	/**
	 * Costruttore di classe.
	 * 
	 * Richiama il costruttore della super-classe che permette di sollevare
	 * un'eccezione con un messaggio passato come parametro.
	 * 
	 * @param msg Messaggio passato al momento del sollevamento dell'eccezione.
	 */
	public InsufficientColumnNumberException(String msg) {
		super(msg);
	}
}
