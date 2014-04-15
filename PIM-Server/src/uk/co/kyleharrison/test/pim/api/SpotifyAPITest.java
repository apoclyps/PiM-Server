package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.mixtape.spotify.api.Album;
import com.mixtape.spotify.api.RequestType;
import com.mixtape.spotify.api.Response;
import com.mixtape.spotify.api.SpotifyMetadata;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SpotifyAPITest {

	private SpotifyMetadata metadata;
	private Response response ;
	
	@Before
	public void setUp() throws Exception {
		this.metadata = new SpotifyMetadata();
		this.response = null;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void SpotifyArtistTest(){
		this.metadata = new SpotifyMetadata();
		try {
			this.response = metadata.search("Foo Fighters", RequestType.album);
			assertNotNull("Response not null", this.response);
			for( Object artist : this.response.getAlbums().toArray()){
				Album artistAlbum = (Album) artist;
			}
			assertEquals("Album Size = 100 Results ", this.response.getAlbums().size(),100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2SpotifyAlbumTest(){
		this.metadata = new SpotifyMetadata();
		try {
			this.response = metadata.search("Foo Fighters", RequestType.album);
			assertNotNull("Response not null", this.response);
			Object [] allAlbums  = this.response.getAlbums().toArray();
			
			Album artistsAlbum = (Album) allAlbums[0];
			assertEquals("Album Expected : Greatest Hits", artistsAlbum.getName(), "Greatest Hits");	
			assertEquals("Href Expected : spotify:album:1zCNrbPpz5OLSr6mSpPdKm", artistsAlbum.getHref(),"spotify:album:1zCNrbPpz5OLSr6mSpPdKm");
			
			assertEquals("Album Size = 100 Results ", this.response.getAlbums().size(),100);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
