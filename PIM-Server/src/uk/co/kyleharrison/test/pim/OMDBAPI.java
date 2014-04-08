package uk.co.kyleharrison.test.pim;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoFull;

public class OMDBAPI {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		OmdbApi omdb = new OmdbApi();
        String query = "Blade Runner";
        OmdbVideoFull result;
		try {
			result = omdb.movieInfo(query);
			System.out.println(result.getPoster());
			assertEquals("Wrong movie returned", "tt0083658", result.getImdbID());
		} catch (OMDBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}

}
