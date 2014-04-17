package uk.co.kyleharrison.pim.cassandra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class ComicvineConnector extends CassandraConnector {

	public ComicvineConnector() {
		super();
		this.database = "comicvine";
		this.connectionString = "jdbc:cassandra://137.117.147.25:9160/" + database;
	}

	public boolean insertVolumes(ArrayList<ComicVineVolume> volumeList)
			throws SQLException {
		String data = "BEGIN BATCH \n";

		for (ComicVineVolume cvv : volumeList) {
			// System.out.println(""+cvv.getName());
			cvv.getImage().setComicVineImages();
			// System.out.println(cvv.getImage().getCassandraMap());
			try {
				String name = cvv.getName().replaceAll("[()?:!.,;{}']", " ");
				System.out.println(cvv.getName().replaceAll("[()?:!.,;{}']",
						" "));
				data += "insert into volumes (id,name,count_of_issues,start_year,image,first_issue) values ("
						+ cvv.getId()
						+ ",'"
						+ name
						+ "',"
						+ cvv.getCount_of_issues()
						+ ","
						+ cvv.getStart_year()
						+ ","
						+ cvv.getImage().getCassandraMap()
						+ ","
						+ cvv.getFirst_issue().getCassandraMap() + ") \n";
				// System.out.println("insert into comicvinevolumes (created, id,name,count_of_issues,start_year,image) values ("+java.util.UUID.fromString(new
				// com.eaio.uuid.UUID().toString())+","+cvv.getId()+",'"+cvv.getName().replaceAll("'",
				// " ")
				// +"',"+cvv.getCount_of_issues()+","+ cvv.getStart_year()
				// +","+cvv.getImage().getCassandraMap() +") \n");
			} catch (Exception e) {
				System.out.println("Default insert");
				data += "insert into volumes (id,name,count_of_issues,start_year) values ("
						+ cvv.getId()
						+ ",'"
						+ cvv.getName().replaceAll("'", " ")
						+ "',"
						+ cvv.getCount_of_issues()
						+ ","
						+ cvv.getStart_year()
						+ ") \n";
			}
		}
		data += "APPLY BATCH;";

		Statement st = this.connection.createStatement();
		st.executeUpdate(data);
		return true;
	}

	public boolean insertComicVineIssues(ArrayList<ComicVineIssue> issues,
			String volumeID) throws SQLException {
		// System.out.println("Batch Insert : "+issues.size()
		// +" Into volume : "+volumeID);
		
		if(this.checkConnection()){
			System.out.println("Connection should be open");
		}else{
			System.out.println("Connection should be closed");
		}
		
		if (issues.equals(null)) {
			System.out.println("Empty Issue List");
			return false;
		}

		String data = "BEGIN BATCH \n";
		for (ComicVineIssue cvi : issues) {
			if (cvi.getId() != 0) {
				System.out.println(cvi.getCassandraInsert());
				cvi.generateCassandraInsert();
				data += "INSERT into comicvineissues(id,volume,name,site_detail_url,api_detail_url,issue_number,image_url,cover_date,description)"
						+ " values (" + cvi.getCassandraInsert() + ")";
			}
		}
		data += "APPLY BATCH;";
		
		this.connection = this.getConnection();
		Statement st = this.connection.createStatement();
		st.executeUpdate(data);
		return true;
	}
	
	public ArrayList <ComicVineIssue> findAllIssues(String volumeID) {
		ArrayList <ComicVineIssue> comicvineIssues = new ArrayList<ComicVineIssue>();
		if (this.checkConnection()) {
			try {
				String data = "SELECT * FROM comicvineissues WHERE volume = "+volumeID+" limit 100 allow filtering";
				
				Statement st = this.connection.createStatement();

				ResultSet results = st.executeQuery(data);

				while (results.next()) {
					ComicVineIssue comicvineIssue = new ComicVineIssue();
					
					comicvineIssue.setId(results.getInt("id"));
					comicvineIssue.setSite_detail_url(results.getString("site_detail_url"));
					comicvineIssue.setName(results.getString("name"));
					comicvineIssue.setApi_detail_uri(results.getString("api_detail_url"));
					comicvineIssue.setImage_url(results.getString("image_url"));
					comicvineIssue.setIssue_number(results.getString("issue_number"));
					comicvineIssue.setImage_url(results.getString("image_url"));
					comicvineIssue.setCover_date(results.getString("cover_date"));
					comicvineIssue.setDescription(results.getString("description"));
					comicvineIssues.add(comicvineIssue);
					//System.out.println(comicvineIssue.toString());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		return comicvineIssues;
	}

}
