package database;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import example.Example;

public class TableData {

	private DbAccess db;
	private String table;
	private TableSchema tSchema;
	private List<Example> transSet;
	private List<Object> target;
	
	public TableData(DbAccess db, TableSchema tSchema) throws SQLException, InsufficientColumnNumberException{
		this.db = db;
		this.tSchema = tSchema;
		this.table = tSchema.getTableName();
		transSet = new ArrayList<Example>();
		target = new ArrayList<Object>();	
		init();
	}
	
	Object getAggregateColumnValue(Column column, QUERY_TYPE aggregate) throws SQLException{
		String query = "SELECT "+ aggregate + "(" + column + ") FROM " + table;

		Statement statement;
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);

		Double value = rs.getDouble(1);

		return value;
	}

	private void init() throws SQLException{		
		String query = "select ";
		
		Iterator<Column> it = tSchema.iterator();
		for(Column c : tSchema){			
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
			for(Column c : tSchema) {
				if(c.isNumber())
					currentTuple.set(rs.getDouble(i + 1), i);
				else
					currentTuple.set(rs.getString(i + 1), i);
				i++;
			}
			transSet.add(currentTuple);
			
			if(tSchema.target().isNumber())
				target.add(rs.getDouble(tSchema.getNumberOfAttributes()));
			else
				target.add(rs.getString(tSchema.getNumberOfAttributes()));
		}
		rs.close();
		statement.close();	
	}
	
	

	
	public List<Example> getExamples(){
		return transSet; 
	}
	

	public List<Object> getTargetValues(){
		return target;
	}
	
}
