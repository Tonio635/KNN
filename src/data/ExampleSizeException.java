package data;

/**
 * Eccezione che controlla che due oggetti di tipo example abbiano 
 * lo stesso numero di variabili dipendenti
 */
class ExampleSizeException extends RuntimeException{
  public ExampleSizeException(String message) {
    super(message);
 }
}
