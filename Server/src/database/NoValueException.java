package database;
/**
 * Eccezione controlata che viene sollevata nel caso in cui i
 * valori inseriti nel database non sono corretti.
 */
class NoValueException extends Exception {
	
	/**
	 * Costruttore di classe.
	 * 
	 * Richiama il costruttore della super-classe che permette di sollevare
	 * un'eccezione con un messaggio passato come parametro.
	 * 
	 * @param msg Messaggio passato al momento del sollevamento dell'eccezione.
	 */
	NoValueException(String msg) {
		super(msg);
	}

}
