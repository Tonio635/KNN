package knn.knn.example;

/**
 * Eccezione che controlla che due oggetti di tipo example abbiano
 * lo stesso numero di variabili indipendenti
 */
public class ExampleSizeException extends RuntimeException {
	
	/**
	 * Costruttore di classe.
	 * 
	 * Richiama il costruttore della super-classe che permette di sollevare
	 * un'eccezione con un messaggio passato come parametro.
	 * 
	 * @param message Messaggio passato al momento del sollevamento dell'eccezione.
	 */
	ExampleSizeException(String message) {
		super(message);
	}
}
