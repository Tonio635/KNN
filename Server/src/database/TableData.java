package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import example.Example;

public class TableData {

	private DbAccess db;
	private String table;
	private TableSchema tSchema;
	private List<Example> transSet;
	private List<Object> target;

	public TableData(DbAccess db, TableSchema tSchema) throws SQLException, InsufficientColumnNumberException {
		this.db = db;
		this.tSchema = tSchema;
		this.table = tSchema.getTableName();
		transSet = new ArrayList<Example>();
		target = new ArrayList<Object>();
		init();
	}

	/**
	 * Metodo che formula ed esegue una interrogazione SQL per estrarre il valore
	 * MIN o MAX della colonna
	 * passata come parametro.
	 * 
	 * @param column    Colonna di cui trovare il MIN o il MAX.
	 * @param aggregate Enumerazione contenente i valori MIN e MAX.
	 * @return Valore minimo o massimo della colonna.
	 * @throws SQLException Eccezione per verificare che un problema nella
	 *                      connessione al database non mandi il programma in crash.
	 */
	public Object getAggregateColumnValue(Column column, QUERY_TYPE aggregate) throws SQLException {
		String query = "SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table + ";";

		Statement statement;
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		Object value = null;

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

	private void init() throws SQLException {
		String query = "select ";

		for (Column c : tSchema) {
			query += c.getColumnName();
			query += ",";
		}
		query += tSchema.target().getColumnName();
		query += (" FROM " + table);

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

			if (tSchema.target().isNumber())
				target.add(rs.getDouble(tSchema.getNumberOfAttributes() + 1));
			else
				target.add(rs.getString(tSchema.getNumberOfAttributes() + 1));
		}
		rs.close();
		statement.close();
	}

	public List<Example> getExamples() {
		return transSet;
	}

	public List<Object> getTargetValues() {
		return target;
	}

}
