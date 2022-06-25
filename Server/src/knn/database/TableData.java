package knn.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import knn.example.Example;

/**
 * Contiene informazioni sulla tabella del database.
 */
public class TableData {

	/** Istanza di connessione al database. */
	private DbAccess db;

	/** Nome della tabella del database. */
	private String table;

	/** Contiene informazioni sullo schema della tabella. */
	private TableSchema tSchema;

	/** Lista di esempi della tabella. */
	private List<Example> transSet;

	/** Lista di variabili target. */
	private List<Object> target;

	/**
	 * Costruttore di classe di TableData.
	 * 
	 * @param db Oggetto di connessione al database.
	 * @param tSchema Contiene informazioni sullo schema della tabella.
	 * @throws SQLException						Eccezione per verificare che un problema nella
	 *                      					connessione al database non mandi il programma in crash.
	 * @throws InsufficientColumnNumberException Eccezione lanciata quando non ci sono numero di colonne 
	 * 											 sufficienti.
	 */
	public TableData(DbAccess db, TableSchema tSchema) 
		throws SQLException, InsufficientColumnNumberException {
		this.db = db;
		this.tSchema = tSchema;
		this.table = tSchema.getTableName();
		transSet = new ArrayList<Example>();
		target = new ArrayList<Object>();
		init();
	}

	/**
	 * Popola gli attributi di classe transSet e target
	 * 
	 * @throws SQLException Eccezione per verificare che un problema nella
	 *                      connessione al database non mandi il programma in crash.
	 */
	private void init() throws SQLException {
		String query = "SELECT ";

		// Variabili indipendenti
		for (Column c : tSchema) {
			query += c.getColumnName();
			query += ",";
		}

		// Variabili target
		query += tSchema.getTarget().getColumnName();
		query += " FROM " + table;

		Statement statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			Example currentTuple = new Example(tSchema.getNumberOfAttributes());
			int i = 0;
			for (Column c : tSchema) {
				if (c.isNumber())
					currentTuple.set(rs.getDouble(i + 1), i);
				else
					currentTuple.set(rs.getString(i + 1), i);
				i++;
			}
			transSet.add(currentTuple);

			if (tSchema.getTarget().isNumber())
				target.add(rs.getDouble(tSchema.getNumberOfAttributes() + 1));
			else
				target.add(rs.getString(tSchema.getNumberOfAttributes() + 1));
		}
		rs.close();
		statement.close();
	}

	/**
	 * Metodo che formula ed esegue una interrogazione SQL per estrarre il valore
	 * MIN o MAX della colonna passata come parametro.
	 * 
	 * @param column    Colonna di cui trovare il MIN o il MAX.
	 * @param aggregate Enumerazione contenente i valori MIN e MAX.
	 * @return Valore minimo o massimo della colonna.
	 * @throws SQLException Eccezione per verificare che un problema nella
	 *                      connessione al database non mandi il programma in crash.
	 */
	public Object getAggregateColumnValue(Column column, QUERY_TYPE aggregate) throws SQLException {
		String query = "SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table + ";";

		Statement statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		Object value = null;

		// Se è stata trovato una riga
		if (rs.next()) {
			if (column.isNumber())
				value = rs.getDouble(1);
			else
				value = rs.getString(1);
		}

		rs.close();
		statement.close();

		return value;
	}

	/**
	 * Restituisce gli esempi della tabella
	 * 
	 * @return transSet
	 */
	public List<Example> getExamples() {
		return transSet;
	}

	/**
	 * Restituisce le variabili target
	 * 
	 * @return target
	 */
	public List<Object> getTargetValues() {
		return target;
	}

}
