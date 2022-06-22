package database;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Classe per definire lo schema della tabella del database da usare per
 * l'estrazione dei dati.
 */
public class TableSchema implements Iterable<Column> {

	private List<Column> tableSchema = new ArrayList<Column>();
	private Column target;
	private String tableName;

	/**
	 * Costruttore di classe.
	 * 
	 * Serve per inizializzare il nome della tabella,
	 * la colonna che definisce la variabile dipendente e una lista contentente
	 * tutte le variabili indipendenti.
	 * 
	 * @param tableName Nome della tabella.
	 * @param db        Oggetto contenente i dati della connessione al database.
	 * @throws SQLException                      Eccezione che fornisce informazioni
	 *                                           su errori di accesso al database o
	 *                                           di altro genere.
	 * @throws InsufficientColumnNumberException Eccezione sollevata nel caso in
	 *                                           cui la tabella non dovesse avere
	 *                                           variabili indipendenti o non
	 *                                           dovesse avere la variabile
	 *                                           dipendente.
	 */
	public TableSchema(String tableName, DbAccess db) throws SQLException, InsufficientColumnNumberException {
		this.tableName = tableName;

		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		DatabaseMetaData meta = db.getConnection().getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {
			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				if (res.isLast())
					target = new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME")));
				else
					tableSchema.add(
							new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}

		res.close();
		if (target == null || tableSchema.size() == 0)
			throw new InsufficientColumnNumberException("La tabella selezionata contiene meno di due colonne");
	}

	/**
	 * Metodo per l'accesso alla colonna della variabile dipendente.
	 * 
	 * @return Colonna della variabile dipendente.
	 */
	public Column getTarget() {
		return target;
	}

	/**
	 * Metodo per l'accesso al numero di variabili indipendenti presenti nella tabella.
	 * 
	 * @return Numero di variabili indipendenti.
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * Metodo per l'accesso al nome della tabella del database.
	 * 
	 * @return Nome della tabella.
	 */
	String getTableName() {
		return tableName;
	}

	/**
	 * Metodo per l'accesso all'iteratore della lista contenente le colonne
	 * delle variabili indipendenti.
	 * 
	 * @return Iteratore della lista tableSchema.
	 */
	@Override
	public Iterator<Column> iterator() {
		return tableSchema.iterator();
	}

	/**
	 * Metodo per la trasformazione in stringa delle informazioni di un oggetto
	 * di tipo TableSchema.
	 * 
	 * @return Stringa contentente le informazioni dell'oggetto TableSchema.
	 */
	public String toString() {
		String result = "";

		result = "Nome tabella: " + tableName + "\n\n";
		for(Column column : tableSchema) {
			result += column + "\n";
		}

		return result;
	}
}