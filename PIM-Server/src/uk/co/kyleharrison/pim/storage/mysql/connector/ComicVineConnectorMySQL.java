package uk.co.kyleharrison.pim.storage.mysql.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
import uk.co.kyleharrison.pim.storage.mysql.MySQLConnector;

public class ComicVineConnectorMySQL extends MySQLConnector {

	private PreparedStatement preparedStatement = null; 

	public ComicVineConnectorMySQL() {
		super(true);
	}

	public boolean findIssues(String volumeID) {
		boolean flag = false;
		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("SELECT * FROM pim.comicvineissues WHERE volume = ? LIMIT 1;");
				
				preparedStatement.setString(1, volumeID);
				ResultSet results = preparedStatement.executeQuery();
				if(results.first()){
					flag = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				flag = false;
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
		return flag;
	}
	
	public ArrayList <ComicVineIssue> findAllIssues(String volumeID) {
		ArrayList <ComicVineIssue> comicvineIssues = new ArrayList<ComicVineIssue>();
		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("SELECT * FROM pim.comicvineissues WHERE volume = ?;");
				
				preparedStatement.setString(1, volumeID);
				ResultSet results = preparedStatement.executeQuery();


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
