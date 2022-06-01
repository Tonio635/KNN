package example;

/**
 * Eccezione che controlla che due oggetti di tipo example abbiano
 * lo stesso numero di variabili indipendenti
 */
public class ExampleSizeException extends RuntimeException {
  public ExampleSizeException(String message) {
    super(message);
  }
}
