package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class MySQLDAO extends MySQLConnector {

	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}

	public void insertVolume(int volumeID, String volumeName) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("INSERT IGNORE into comicdb.volumes"
							+ "(volumeID,volumeName)"
							+ " values  (?,?)");

			preparedStatement.setInt(1, volumeID);
			preparedStatement.setString(2, volumeName);
			/*
			preparedStatement.setString(3, rssChannel.getDescription());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(rssChannel
					.getLastBuildDate().getTime()));
			preparedStatement.setString(5, rssChannel.getLanguage());
			preparedStatement.setString(6, rssChannel.getUpdatePeriod());
			preparedStatement.setInt(7, rssChannel.getUpdateFrequency());
			preparedStatement
					.setString(8, rssChannel.getGenerator().toString());
					*/
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
