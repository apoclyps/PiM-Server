package uk.co.kyleharrison.pim.storage.mysql.connector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.omertron.omdbapi.model.OmdbVideoFull;

import uk.co.kyleharrison.pim.interfaces.ProductConnectorInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLConnector;

public class ProductConnectorMySQL extends MySQLConnector implements ProductConnectorInterface {

	private PreparedStatement preparedStatement = null; 
	private OmdbVideoFull tempVideo;
	
	public OmdbVideoFull getTempVideo() {
		return tempVideo;
	}

	public void setTempVideo(OmdbVideoFull tempVideo) {
		this.tempVideo = tempVideo;
	}

	public ProductConnectorMySQL() {
		super();
	}

	@Override
	public boolean addComic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addDvd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addCd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findComic() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean findDvd(String imdbid) {
		boolean flag = false;
		if (this.checkConnection()) {
			try {
				preparedStatement = connection
						.prepareStatement("SELECT * FROM pim.omdb WHERE imdbid = ? LIMIT 1;");
				
				preparedStatement.setString(1, imdbid);
				ResultSet results = preparedStatement.executeQuery();
				//System.out.println("Results : "+results.toString());
				if(results.first()){
					flag = true;
					
					tempVideo = new OmdbVideoFull();
					tempVideo.setTitle(results.getString("title"));
					tempVideo.setImdbID(results.getString("imdbid"));
					tempVideo.setPoster(results.getString("poster"));
					tempVideo.setDirector(results.getString("director"));
					tempVideo.setRuntime(results.getString("runtime"));
					
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

	@Override
	public boolean findCd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareComic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareDvd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareCd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean compareOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateComic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDvd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCD() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeComic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeDvd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCd() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeOther() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean findDvd() {
		// TODO Auto-generated method stub
		return false;
	}

}
