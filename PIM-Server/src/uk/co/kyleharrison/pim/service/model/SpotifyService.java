package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

import com.mixtape.spotify.api.RequestType;
import com.mixtape.spotify.api.Response;
import com.mixtape.spotify.api.SpotifyMetadata;

public class SpotifyService implements ControllerServiceInterface {

	private Response response;
	private MySQLFacade mySQLFacade;
	
	public SpotifyService() {
		super();
		this.response = null;
		mySQLFacade = new MySQLFacade();
	}

	@Override
	public String executeQuery(String query) {
		SpotifyMetadata metadata = new SpotifyMetadata();
		try {
			this.response = metadata.search(query, RequestType.album);
			
			/*for( Object artist : response.getAlbums().toArray()){
				Album artistAlbum = (Album) artist;
				System.out.println(artistAlbum.getName());
			}*/
					
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(response.getAlbums().toArray());
			} catch (JsonGenerationException | JsonMappingException ex) {
				ex.printStackTrace();
			}catch( IOException ioe){
				ioe.printStackTrace();
			}
			System.out.println("\n"+"Spotify Results : "+response.getAlbums().size());
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject executeJSONQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean searchItemExists(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cacheItem(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cacheResults() {
		System.out.println("Caching Results");
		boolean cached = this.mySQLFacade.insertSpotifyAlbums(this.response);
		return cached;
	}

	@Override
	public boolean executeQueryAllResults(String query) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
