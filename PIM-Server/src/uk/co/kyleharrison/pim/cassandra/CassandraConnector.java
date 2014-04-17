package uk.co.kyleharrison.pim.cassandra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.mortbay.log.Log;

public class CassandraConnector {

	protected Connection connection = null;
	protected String database = "ComicVine";
	protected String connectionString = "jdbc:cassandra://137.117.147.25:9160/comicvine";

	public CassandraConnector() {
		try {
			System.out.println("connection string"+ connectionString);
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			this.connection = DriverManager.getConnection(connectionString);
			System.out.println("Connection Opened Cassadnra");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
			Log.info("Cassandra Server may not be started on Server");
		}
	}

	public boolean checkConnection() {
		try {
			//connection.close();
			this.connection = DriverManager.getConnection(connectionString);
			System.out.println("Connection Opened Cassadnra");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
	public boolean createColumnFamily() {
		String statement = "CREATE columnfamily comicvinevolumes (key int primary key, name text , issue_count, int year)";
		try {
			Statement st = this.connection.createStatement();
			return st.execute(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean dropColumnFamily(String name) {
		String data = "drop columnfamily " + name + ";";
		Statement st;
		try {
			st = this.connection.createStatement();
			return st.execute(data);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	public Connection getConnection() {
		return this.connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
}
