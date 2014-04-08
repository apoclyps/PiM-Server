package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.grapejuice.utils.URLReader;
import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.model.SpotifyThumbnail;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

import com.mixtape.spotify.api.Album;
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
				List<Object> AlbumList = Arrays.asList(response.getAlbums().toArray());
				AlbumList.subList(0, 10);
				
				// Get Thumbnail_URLS
				ArrayList<Album> updatedAlbums = getThumbnails(AlbumList.subList(0, 10));
				
				json = ow.writeValueAsString(AlbumList.subList(0, 10));
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
	
	public ArrayList<Album> getThumbnails(List<Object> albums){
		
		ArrayList<Album> updatedAlbums = new ArrayList<Album>();
		
		for(Object album : albums){
			Album newAlbum = (Album) album;
			//System.out.println("HREF : "+newAlbum.getHref());
			String thumbnail = requestThumbnailURL(newAlbum.getHref());
			//System.out.println("THUMBNAIL "+thumbnail);
			newAlbum.setThumbnail_url(thumbnail);
			updatedAlbums.add(newAlbum);
		}
		return updatedAlbums;
	}
	
	public String requestThumbnailURL(String href){
		
		URLReader urlReader = new URLReader();
		
		String url = "https://embed.spotify.com/oembed/?url="+href;
		urlReader.setUrl(url);
		
		String jsonResponse = urlReader.readFromUrl();
		//System.out.println(jsonResponse);
		
		// Deconstruct to POJO
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(jsonResponse);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return string
		
		return mapToPojo(json);
	}
	
	public String mapToPojo(JSONObject json) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			SpotifyThumbnail spotifyThumbnail = mapper.readValue(json.toJSONString(), SpotifyThumbnail.class);
			return spotifyThumbnail.getThumbnail_url();	 
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
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

	@Override
	public boolean cacheAllResults() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
