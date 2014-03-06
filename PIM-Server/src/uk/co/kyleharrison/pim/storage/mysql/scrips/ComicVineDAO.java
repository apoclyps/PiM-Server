package uk.co.kyleharrison.pim.storage.mysql.scrips;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.pim.storage.mysql.MySQLConnector;

public class ComicVineDAO extends MySQLConnector {

	private PreparedStatement preparedStatement = null;

	public ComicVineDAO() {
		super();
	}
	
	public void insertComicVineVolume(ComicVineVolume comicVineVolume) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("INSERT IGNORE into pim.comicvinevolumes"
							+ "(volumeID,volumeName,issue_count)"
							+ " values  (?,?,?)");

			preparedStatement.setInt(1, comicVineVolume.getId());
			preparedStatement.setString(2, comicVineVolume.getName());
			preparedStatement.setInt(3, comicVineVolume.getCount_of_issues());

			preparedStatement.executeUpdate();
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
	}
	
	public boolean insertComicVineVolumes(ArrayList<ComicVineVolume> volumeList) throws SQLException {
		System.out.println("Batch Insert : "+volumeList.size());
		if(volumeList.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into comicdb.volumes"
							+ "(volumeID,volumeName,issue_count)"
							+ " values  (?,?,?)");

			for(ComicVineVolume cvv : volumeList){
				preparedStatement.setInt(1, cvv.getId());
				preparedStatement.setString(2, cvv.getName());
				preparedStatement.setInt(3,cvv.getCount_of_issues());
				preparedStatement.addBatch();
			}
			
			int [] results = preparedStatement.executeBatch();
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
	}

}
