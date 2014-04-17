package uk.co.kyleharrison.test.pim.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uk.co.kyleharrison.pim.cassandra.CassandraConnector;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CassandraSchemaTest {

	private CassandraConnector cassandraConnector;
	private Connection activeConnection;
	
	@Before
	public void setUp() throws Exception {
		this.cassandraConnector = new CassandraConnector();
		this.activeConnection = this.cassandraConnector.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		this.cassandraConnector.close();
	}

	@Test
	public void test1OpenConnection() {
		assertTrue(this.cassandraConnector.checkConnection());
	}
	
    @Test
	public void test2CreateCOLUMNFAMILY() {
		String data = "CREATE COLUMNFAMILY test (key int primary key, category text , linkcounts int ,url text)";
		boolean response = executeQuery(data);
		assert(response==true);
	}
    
    @Test
	public void test3PouplateData() {
		String data = "BEGIN BATCH \n"
				+ "INSERT INTO test (key, category, linkcounts,url) VALUES (5,'class',71,'news.com') \n"
				+ "INSERT INTO test (key, category, linkcounts,url) VALUES (6,'education',15,'tech.com') \n"
				+ "INSERT INTO test (key, category, linkcounts,url) VALUES (7,'technology',415,'ba.com') \n"
				+ "INSERT INTO test (key, category, linkcounts,url) VALUES (8,'travelling',45,'google.com/teravel') \n"
				+ "APPLY BATCH;";
		
		int response = 0;
		try {
			Statement st = this.activeConnection.createStatement();
			response = st.executeUpdate(data);
		} catch (SQLException e) {
			e.printStackTrace();
			response =0;
		}
		assert(response==1);
	}
    
    @Test
    public void test4listData() {
		String t = "SELECT * FROM test";
		Statement st;
		ResultSet rs;
		try {
			st = this.activeConnection.createStatement();
			rs = st.executeQuery(t);
			while (rs.next()) {
				System.out.println(rs.getString("key"));
				for (int j = 1; j < rs.getMetaData().getColumnCount() + 1; j++) {
					System.out.println(rs.getMetaData().getColumnName(j) + " : "
							+ rs.getString(rs.getMetaData().getColumnName(j)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			rs= null;
		}
		assert(rs!=null);
	}
    
    @Test
	public void test5updateData() {
		String t = "UPDATE test SET category='sports', linkcounts=1 WHERE key=5";
		Statement statement;
		int response = 0;
		try {
			statement = this.activeConnection.createStatement();
			response = statement.executeUpdate(t);
		} catch (SQLException e) {
			e.printStackTrace();
			response=0;
		}
		assert(response==1);
	}
    
    @Test
	public void test6deleteData() {
		String data = "BEGIN BATCH \n"
				+ "DELETE FROM test WHERE key=5 \n"
				+ "DELETE category FROM test WHERE key=2 \n"
				+ "APPLY BATCH;";
		Statement st;
		boolean response = false;
		try {
			st = this.activeConnection.createStatement();
			st.executeUpdate(data);
		} catch (SQLException e) {
			e.printStackTrace();
			response = false;
		}
		assert(response==true);
	}
    
	@Test
	public void test9DROPCOLUMNFAMILY() {
		String data = "DROP COLUMNFAMILY test;";
		Statement st;
		boolean response = false;
		try {
			st = this.activeConnection.createStatement();
			response = st.execute(data);
		} catch (SQLException e) {
			e.printStackTrace();
			response=false;
		}
		assert(response==true);
	}
	
	public boolean executeQuery(String data){
		try {
			Statement st = this.activeConnection.createStatement();
			return st.execute(data);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
