package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.scholnick.isbndb.domain.Book;

import com.mlesniak.amazon.backend.AmazonItem;
import com.omertron.omdbapi.model.OmdbVideoBasic;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;

public class MySQLDAO extends MySQLConnector {

	private PreparedStatement preparedStatement = null;

	public MySQLDAO() {
		super();
	}
	
	public boolean insertAsylumRecords(Map<String, String> entry) throws SQLException {
		if (this.checkConnection()) {
			preparedStatement = connection
					.prepareStatement("INSERT IGNORE into comicdb.asylum"
							+ "(productID,title,issue,publisher,volume,quantity,cost)"
							+ " values  (?,?,?,?,?,?,?)");

			System.out.println("id "+entry.get("id"));
			
			preparedStatement.setInt(1, Integer.parseInt(entry.get("id")));
			preparedStatement.setString(2, (String) entry.get("title"));
			preparedStatement.setString(3, (String) entry.get("issue"));
			preparedStatement.setString(4, (String) entry.get("publisher"));
			preparedStatement.setString(5, (String) entry.get("volume"));
			preparedStatement.setInt(6, Integer.parseInt(entry.get("quantity")));
			preparedStatement.setFloat(7, Float.parseFloat(entry.get("cost")));

			preparedStatement.executeUpdate();
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
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

	public boolean insertAmazonItems(List<AmazonItem> amazonItems) throws SQLException {
		System.out.println("Batch Insert : "+amazonItems.size());
		if(amazonItems.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.amazonitems"
							+ "(asin,title,price,url,imageurl,reviewurl)"
							+ " values  (?,?,?,?,?,?)");

			for(AmazonItem amazonItem : amazonItems){
				preparedStatement.setString(1, amazonItem.getAsin());
				preparedStatement.setString(2, amazonItem.getTitle());
				preparedStatement.setDouble(3,amazonItem.getPrice());
				preparedStatement.setString(4,amazonItem.getURL());
				preparedStatement.setString(5,amazonItem.getImageURL());
				preparedStatement.setString(6,amazonItem.getReviewURL());
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

	public boolean insertISBNDBBooks(List<Book> books) throws SQLException {
		System.out.println("Batch Insert : "+books.size());
		if(books.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.isbndb"
							+ "(title,isbn10,isbn13,deweynumber,publisher,summary)"
							+ " values  (?,?,?,?,?,?)");

			for(Book book : books){
				preparedStatement.setString(1, book.getTitle());
				preparedStatement.setString(2, book.getIsbn10());
				preparedStatement.setString(3,book.getIsbn13());
				preparedStatement.setString(4,book.getDeweyNumber());
				preparedStatement.setString(5,book.getPublisher());
				preparedStatement.setString(6,book.getSummary());
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

	public boolean insertOMDBItems(List<OmdbVideoBasic> omdbResults) throws SQLException {
		System.out.println("Batch Insert : "+omdbResults.size());
		if(omdbResults.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.omdb"
							+ "(imdbid,title,type,year)"
							+ " values  (?,?,?,?)");

			for(OmdbVideoBasic omdbVideo : omdbResults){
				preparedStatement.setString(1, omdbVideo.getImdbID());
				preparedStatement.setString(2, omdbVideo.getTitle());
				preparedStatement.setString(3,omdbVideo.getType());
				preparedStatement.setString(4,omdbVideo.getYear());
				preparedStatement.addBatch();
				System.out.println("Batch added "+omdbVideo.getTitle());
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
