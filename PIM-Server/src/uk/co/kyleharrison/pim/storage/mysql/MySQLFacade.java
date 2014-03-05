package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mlesniak.amazon.backend.AmazonItem;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class MySQLFacade implements MySQLInterface {

	private MySQLDAO mySQLDAO;

	public MySQLFacade() {
		super();
		this.setNewConnection();
	}

	public MySQLDAO getConnection() {
		return this.mySQLDAO;
	}

	public void setNewConnection() {
		/*try {
			connection.close();
		} catch (NullPointerException e) {
			System.out.println("No connection to close | Connection closing problem");
		}*/
		this.mySQLDAO = new MySQLDAO();
	}

	public void closeConnection() {
		this.mySQLDAO.close();
	}

	public boolean insertVolume(int volumeID, String volumeName) {
		
		try {
			this.mySQLDAO.insertVolume(volumeID,volumeName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertVolumes(ArrayList<ComicVineVolume> comicVineVolumes) {
		try {
			System.out.println("Inserting Attempt");
			return this.mySQLDAO.insertComicVineVolumes(comicVineVolumes);
		} catch (SQLException e) {
			System.out.println("Exception caught in MySQLFacade for Insert Volumes");
			e.printStackTrace();
		}
		System.out.println("Inserting Failed");
		return false;
	}
	
	public boolean insertAsylumRecord(Map<String, String> entry){
		try {
			return this.mySQLDAO.insertAsylumRecords(entry);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertAmazonItems(List<AmazonItem> amazonItems) {
		try {
			return this.mySQLDAO.insertAmazonItems(amazonItems);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
