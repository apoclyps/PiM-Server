package uk.co.kyleharrison.pim.storage.mysql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.scholnick.isbndb.domain.Book;

import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.mixtape.spotify.api.Response;
import com.mlesniak.amazon.backend.AmazonItem;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;

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

	public boolean insertISBNDBBooks(List<Book> books) {
		try {
			return this.mySQLDAO.insertISBNDBBooks(books);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertOMDBItems(List<OmdbVideoBasic> omdbResults) {
		try {
			return this.mySQLDAO.insertOMDBItems(omdbResults);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertOMDBFullItems(List<OmdbVideoFull> omdbResults) {
		try {
			return this.mySQLDAO.insertOMDBFullItems(omdbResults);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertSpotifyAlbums(Response response) {
		try {
			return this.mySQLDAO.insertSpotifyAlbums(response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertSteamGames(HashMap<Integer, SteamGame> sg) {
		try {
			return this.mySQLDAO.insertSteamGames(sg);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insertIssues(ArrayList<ComicVineVolume> comicVineVolumes, String volumeID) {
		try {
			System.out.println("Inserting Attempt");
			int currentID = Integer.parseInt(volumeID);
			return this.mySQLDAO.insertComicVineIssues(comicVineVolumes.get(0).getResults().getIssues(),currentID);
		} catch (SQLException e) {
			System.out.println("Exception caught in MySQLFacade for Insert Volumes");
			e.printStackTrace();
		}
		System.out.println("Inserting Failed");
		return false;
	}

}
