package uk.co.kyleharrison.test.pim.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mixtape.spotify.api.Album;

import uk.co.kyleharrison.pim.service.model.SpotifyService;

public class SpotifyServiceTest {

	private SpotifyService spotifyService;
	
	@Before
	public void setUp() throws Exception {
		this.spotifyService = new SpotifyService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ConstructorTest() {
		assert(this.spotifyService!=null);
	}
	
	@Test
	public void QueryTest(){
		String results = this.spotifyService.executeQuery("Superman");
		assert(!results.equals(null));
		assertEquals("Response Size" , this.spotifyService.getSpotifyResponse().getAlbums().size(), 100);
	}
	
	@Test
	public void QueryThumbnails(){
		String results = this.spotifyService.executeQuery("Superman");
		assert(!results.equals(null));
		assertEquals("Response Size" , this.spotifyService.getSpotifyResponse().getAlbums().size(), 100);
		
		
		List<Object> AlbumList = Arrays.asList(this.spotifyService.getSpotifyResponse().getAlbums().toArray());
		ArrayList<Album> updatedAlbums = this.spotifyService.getThumbnails(AlbumList.subList(0, 10));
		assertEquals("Thumbnail Response Size ", updatedAlbums.size(),10);
		assertEquals("First Thumbnail URL", this.spotifyService.getThumbnails(AlbumList.subList(0, 10)).get(0).getThumbnail_url(),"https://d3rt1990lpmkn.cloudfront.net/cover/0a4fba04550165d20f3fba96ec65274ef7351962");

	}
 
	public void InsertionTest(){
		boolean result = this.spotifyService.cacheResults();
		assert(result==true);
	}
	
	public void checkVolumesInserted(){

	}
	
}
