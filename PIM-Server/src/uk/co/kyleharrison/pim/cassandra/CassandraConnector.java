package uk.co.kyleharrison.pim.cassandra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.eaio.uuid.UUIDGen;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class CassandraConnector {

	protected Connection connection = null;

	private String database = "ComicVine";
	private String connectionString = "jdbc:cassandra://localhost:9160/Comicvine";

	public CassandraConnector() {
		try {
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			connection = DriverManager.getConnection(connectionString);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkConnection() {
		try {
			connection.close();
			connection = DriverManager.getConnection(connectionString);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void close() {
		try {
			if (connection != null) {
				connection.close();
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
	
	public int insertComicVineVolumes(ArrayList<ComicVineVolume> volumeList) throws SQLException {
		String data = "BEGIN BATCH \n";
		
		for(ComicVineVolume cvv : volumeList){
			//System.out.println(""+cvv.getName());	
			cvv.getImage().setComicVineImages();
			//System.out.println(cvv.getImage().getCassandraMap());
			try{
				data +=  "insert into volumes (id,name,count_of_issues,start_year,image) values ("+cvv.getId()+",'"+cvv.getName().replaceAll("'", " ")
						+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +","+cvv.getImage().getCassandraMap() +") \n";
				//System.out.println("insert into comicvinevolumes (created, id,name,count_of_issues,start_year,image) values ("+java.util.UUID.fromString(new com.eaio.uuid.UUID().toString())+","+cvv.getId()+",'"+cvv.getName().replaceAll("'", " ")
				//+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +","+cvv.getImage().getCassandraMap() +") \n");
			}catch(Exception e){
				System.out.println("Failed");
				data +=  "insert into volumes (id,name,count_of_issues,start_year) values ("+cvv.getId()+",'"+cvv.getName().replaceAll("'", " ")
						+"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year() +") \n";
			}
		}
		data += "APPLY BATCH;";
		
		Statement st = this.connection.createStatement();
		return st.executeUpdate(data);
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
}
