package uk.co.kyleharrison.test.pim.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omertron.omdbapi.model.OmdbVideoFull;

import uk.co.kyleharrison.pim.service.model.OMDBService;

public class OmdbServiceTest {

	private OMDBService omdbService;
	
	@Before
	public void setUp() throws Exception {
		this.omdbService = new OMDBService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ConstructorTest() {
		assert(this.omdbService!=null);
	}
	
	@Test
	public void QueryTest(){
		String results = this.omdbService.executeQuery("batman");
		System.out.println(results);
		assertNotNull("JSON Response",results);
		
		ArrayList<OmdbVideoFull> searchResults = this.omdbService.getSearchResults();
		assertEquals("Search Size ",searchResults.size(),10);
	}
	
	

}
