package uk.co.kyleharrison.pim.storage.mysql.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
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
				//System.out.println("Results : "+results.toString());
				if(results.first()){
					flag = true;
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
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
	
}
