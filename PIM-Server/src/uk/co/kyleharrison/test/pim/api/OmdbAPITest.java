package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.wrapper.WrapperSearch;

public class OmdbAPITest {

	private OmdbApi omdb;
	private List<OmdbVideoBasic> OMDBResults;

	@Before
	public void setUp() throws Exception {
		omdb = new OmdbApi();
		this.OMDBResults = null;
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
			assertEquals("Wrong movie returned", "tt0083658",
					result.getImdbID());
		} catch (OMDBException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findOmdbVideoBasic() {
		String query = "Blade Runner";
		OmdbVideoBasic result;
		try {
			result = this.omdb.movieInfo(query);
			assertEquals("Wrong movie returned", "tt0083658",
					result.getImdbID());
		} catch (OMDBException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		OmdbApi omdb = new OmdbApi();
		WrapperSearch result = null;

		try {
			result = omdb.search("Batman");
		} catch (OMDBException e) {
			e.printStackTrace();
		}

		this.OMDBResults = null;
		this.OMDBResults = result.getResults();

		ArrayList<OmdbVideoFull> searchFull = new ArrayList<OmdbVideoFull>();
		ArrayList<OmdbVideoFull> searchCache = new ArrayList<OmdbVideoFull>();

		if (this.OMDBResults.size() > 0 || this.OMDBResults != null) {
			for (OmdbVideoBasic searchBasic : this.OMDBResults) {
				String newQuery = searchBasic.getTitle();
				OmdbVideoFull result2 = null;

				try {
					result2 = new OmdbVideoFull(searchBasic.getTitle(),
							searchBasic.getYear(), searchBasic.getImdbID(),
							searchBasic.getType());
					result2 = omdb.movieInfo(searchBasic.getImdbID());
					System.out.println(result2.getPoster());
					searchCache.add(result2);

					searchFull.add(result2);
				} catch (OMDBException e) {
					System.out.println("Socket Time out for OMDB API");
				}

			}
		}
		
	}

}
