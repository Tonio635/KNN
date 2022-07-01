package knn.knn.example;

/**
 * Eccezione che controlla che due oggetti di tipo example abbiano
 * lo stesso numero di variabili indipendenti
 */
class ExampleSizeException extends RuntimeException {
	ExampleSizeException(String message) {
		super(message);
	}
}
