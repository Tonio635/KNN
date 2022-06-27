package knn.database;

/**
 * Classe che rappresenta una colonna di una tabella di un database.
 */
public class Column {
	private String name;
	private String type;

	/**
	 * Costruttore di classe.
	 * 
	 * Inizializza i valori degli attributi assegnando un nome ed un tipo di dato
	 * alla colonna.
	 * 
	 * @param name Nome della colonna.
	 * @param type Tipo di dato della colonna.
	 */
	Column(String name, String type) {
		this.name = name;
		this.type = type;
	}

	/**
	 * Metodo per l'accesso al nome della colonna.
	 * 
	 * @return Nome della colonna.
	 */
	public String getColumnName() {
		return name;
	}

	/**
	 * Metodo per la verifica che il tipo di dato della colonna sia numerico.
	 * 
	 * @return Esito della verifica.
	 */
	public boolean isNumber() {
		return type.equals("number");
	}

	/**
	 * Metodo per la trasformazione in stringa degli oggetti di tipo Column.
	 * 
	 * @return Stringa contentente le informazioni dell'oggetto Column.
	 */
	public String toString() {
		return name + ":" + type;
	}
}