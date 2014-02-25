package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;







import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.mixtape.spotify.api.Album;
import com.mixtape.spotify.api.RequestType;
import com.mixtape.spotify.api.Response;
import com.mixtape.spotify.api.SpotifyMetadata;

public class SpotifyService {

	public String testSpotify(String query){
		SpotifyMetadata metadata = new SpotifyMetadata();
		try {
			Response response = metadata.search(query, RequestType.album);
			
			for( Object artist : response.getAlbums().toArray()){
				Album artistAlbum = (Album) artist;
				System.out.println(artistAlbum.getName());
			}
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(response.getAlbums().toArray());
			} catch (JsonGenerationException | JsonMappingException ex) {
				ex.printStackTrace();
			}catch( IOException ioe){
				ioe.printStackTrace();
			}
			System.out.println("\n"+"Total Results : "+response.getAlbums().size());
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
