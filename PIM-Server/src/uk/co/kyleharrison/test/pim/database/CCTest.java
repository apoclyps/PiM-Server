package uk.co.kyleharrison.test.pim.database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.lang.StringEscapeUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.pim.cassandra.ComicvineConnector;
import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class CCTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	public void test() {
		ComicvineConnector cc = new ComicvineConnector();
		ArrayList<ComicVineIssue> issues = cc.findAllIssues("2133");
		System.out.println("Size" + issues.size());
		assert(issues.size()>0);
		System.out.println(issues.get(0).getName());
		System.out.println(issues.get(0).getImage_url());
		System.out.println(issues.get(0).getName());
	}
	

	public void batchRemove() {
		ComicvineConnector cc = new ComicvineConnector();
		ArrayList<ComicVineIssue> issues = cc.findAllIssues("2133");
		System.out.println("Size" + issues.size());
		assertNotEquals("Issue Size ", issues.size(),0);

		try {
			boolean success = cc.removeComicVineIssues(issues,"2133");
			assertTrue("Batch Delete" , success);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void batchInsert(){
		
		ComicVineService cvs = new ComicVineService();
		ArrayList<ComicVineVolume> cvv = cvs.preformIssueQuery("3092");
		
		int volumeID = 3092;
		ComicVineIssue cvi = cvv.get(0).getIssues().get(0);
		
	    try {
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");

		    Connection con = DriverManager.getConnection("jdbc:cassandra://137.117.147.25:9160/comicvine");
	
		    String query = "INSERT into comicvineissues(id,volume,name,site_detail_url,api_detail_url,issue_number,image_url,cover_date,description) "
		    		+ "VALUES (?,?,?,?,?,?,?,?,?)";
		    
		    PreparedStatement preparedStatement = con.prepareStatement(query);
		   
			preparedStatement.setInt(1, cvi.getId());
			preparedStatement.setInt(2, volumeID);
			preparedStatement.setString(3, StringEscapeUtils.escapeSql(cvi.getName()));
			preparedStatement.setString(4, cvi.getSite_detail_url());
			preparedStatement.setString(5, cvi.getApi_detail_uri());
			preparedStatement.setString(6, cvi.getIssue_number());
			preparedStatement.setString(7, cvi.getImage()
					.getThumb_url().toString());
			preparedStatement.setString(8, cvi.getCover_date());
			preparedStatement.setString(9, StringEscapeUtils.escapeSql(cvi.getDescription()));
			
			int success = preparedStatement.executeUpdate();
	
			preparedStatement.close();
			assert(success==1);
	    
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


}
