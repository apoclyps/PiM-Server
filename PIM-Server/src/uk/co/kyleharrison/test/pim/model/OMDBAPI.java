package uk.co.kyleharrison.test.pim.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;

public class OMDBAPI {

	private OmdbApi omdb ;
	
	@Before
	public void setUp() throws Exception {
		omdb = new OmdbApi();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void FindOmdbVideoFull() {
        String query = "Blade Runner";
        OmdbVideoFull result;
		try {
			result = this.omdb.movieInfo(query);
			assertEquals("Wrong movie returned", "tt0083658", result.getImdbID());
		} catch (OMDBException e) {
			e.printStackTrace();
		}
	}

}
