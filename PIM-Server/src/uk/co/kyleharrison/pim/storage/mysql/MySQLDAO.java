package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.scholnick.isbndb.domain.Book;

import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.mixtape.spotify.api.Album;
import com.mixtape.spotify.api.Artist;
import com.mixtape.spotify.api.Response;
import com.mlesniak.amazon.backend.AmazonItem;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
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
					.prepareStatement("INSERT IGNORE into pim.comicvinevolumes"
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
			
			preparedStatement.executeBatch();
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
			
			preparedStatement.executeBatch();
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
			
			preparedStatement.executeBatch();
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
			
			preparedStatement.executeBatch();
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
	}
	
	public boolean insertOMDBFullItems(List<OmdbVideoFull> omdbResults) throws SQLException {
		System.out.println("Batch Insert : "+omdbResults.size());
		if(omdbResults.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		
		if (this.checkConnection()) {
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.omdb"
							+ "(imdbid,title,type,year,director,poster,runtime)"
							+ " values  (?,?,?,?,?,?,?)");

			for(OmdbVideoFull omdbVideo : omdbResults){
				preparedStatement.setString(1, omdbVideo.getImdbID());
				preparedStatement.setString(2, omdbVideo.getTitle());
				preparedStatement.setString(3,omdbVideo.getType());
				preparedStatement.setString(4,omdbVideo.getYear());
				
				preparedStatement.setString(5,omdbVideo.getDirector());
				preparedStatement.setString(6,omdbVideo.getPoster());
				preparedStatement.setString(7,omdbVideo.getRuntime());		
				
				//preparedStatement.addBatch();
				System.out.println("Batch added "+omdbVideo.getTitle());
				//System.out.println(omdbVideo.toString());
				
				System.out.println(preparedStatement.toString());
				preparedStatement.execute();
			}
			
			//int[] results = preparedStatement.executeBatch();
			/*
			System.out.println(results.length);
			int responseCode = 0;
			for(int i : results){
				System.out.println(i);
				responseCode = responseCode + i;
			}
			if(responseCode<results.length){
				System.out.println("failed to insert");
				return false;
			}
			*/
			
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
	}

	public boolean insertSpotifyAlbums(Response response) throws SQLException {
		System.out.println("Batch Insert : "+response.getAlbums().size());
		if(response.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.spotifyalbums"
							+ "(artist,albumname,popularity,releaseyear,href)"
							+ " values  (?,?,?,?,?)");

			for(Album album : response.getAlbums()){
				
				List<Artist> artist = (List<Artist>) album.getArtists();
				System.out.println("Batch added "+artist.get(0).getName());
			
				int release = 0;
				try{
					if(album.getReleaseYear().equals(null)){
						release = 0;
					}else{
						release = album.getReleaseYear();
					}
				}catch(Exception e){
					release =0;
				}
				
				//System.out.println(" ! : "+artist.get(0).getName());
				//System.out.println(" ! : "+album.getName());
				//System.out.println(" ! : "+album.getPopularity());
				//System.out.println(" ! : "+release);
				//System.out.println(" ! : "+album.getHref());
				
				preparedStatement.setString(1,artist.get(0).getName());
				preparedStatement.setString(2, album.getName());
				preparedStatement.setDouble(3, album.getPopularity());
				preparedStatement.setInt(4,release);
				preparedStatement.setString(5,album.getHref());
				preparedStatement.addBatch();
				
			}
			
			preparedStatement.executeBatch();
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
	}

	public boolean insertSteamGames(HashMap<Integer, SteamGame> sg) throws SQLException {
		System.out.println("Batch Insert : "+sg.size());
		if(sg.equals(null)){
			System.out.println("Empty Volume List");
			return false;
		}
		if (this.checkConnection()) {
			System.out.println("Attempting Insert iccv");
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.steamgames"
							+ "(name,id,shortname,logothumbnail,logourl)"
							+ " values  (?,?,?,?,?)");

			for(Entry<Integer, SteamGame> entry : sg.entrySet()){
				
				//System.out.println(" ! : "+artist.get(0).getName());
				//System.out.println(" ! : "+album.getName());
				//System.out.println(" ! : "+album.getPopularity());
				//System.out.println(" ! : "+release);
				//System.out.println(" ! : "+album.getHref());
				
				SteamGame sg1 = entry.getValue();
				
				String id = null;
				try{
					if(sg1.getId().equals(null)){
						id = "null";
					}else{
						id = sg1.getId().toString();
					}
				}catch(Exception e){
					id = "null";
				}
				
				preparedStatement.setString(1,sg1.getName());
				preparedStatement.setString(2, id);
				preparedStatement.setString(3, sg1.getShortName());
				preparedStatement.setString(4,sg1.getLogoThumbnailUrl());
				preparedStatement.setString(5,sg1.getLogoUrl());
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
			return true;
		} else {
			System.out.println("MYSQLDOA : Insert Channel : Connection Failed");
		}
		if (connection != null) {
			connection.close();
		}
		return false;
	}

	public boolean insertComicVineIssues(ArrayList<ComicVineIssue> issues, int volumeID) throws SQLException {
		
		System.out.println("Batch Insert : "+issues.size() +" Into volume : "+volumeID);
		if(issues.equals(null)){
			System.out.println("Empty Issue List");
			return false;
		}
		if (this.checkConnection()) {
			preparedStatement = connection.prepareStatement("INSERT IGNORE into pim.comicvineissues"
							+ "(id,site_detail_url,name,api_detail_url,issue_number,volume)"
							+ " values  (?,?,?,?,?,?)");

			for(ComicVineIssue cvi : issues){
				preparedStatement.setInt(1, cvi.getId());
				preparedStatement.setString(2, cvi.getSite_detail_url());
				preparedStatement.setString(3,cvi.getName());
				preparedStatement.setString(4,cvi.getApi_detail_uri());
				preparedStatement.setString(5, cvi.getIssue_number());
				preparedStatement.setInt(6, volumeID);
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
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
