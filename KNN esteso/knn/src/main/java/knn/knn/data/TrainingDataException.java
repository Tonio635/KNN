package knn.knn.data;

/**
 * Eccezione controllata che controlla che il TrainingSet sia stato acquisito in maniera
 * corretta
 */
public class TrainingDataException extends Exception {

    /**
	 * Costruttore di classe.
	 * 
	 * Richiama il costruttore della super-classe che permette di sollevare
	 * un'eccezione con un messaggio passato come parametro.
	 * 
	 * @param message Messaggio passato al momento del sollevamento dell'eccezione.
	 */
    public TrainingDataException(String message) {
        super(message);
    }
}
