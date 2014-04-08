package uk.co.kyleharrison.pim.service.model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.pim.enums.RequestTypes;
import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.wrapper.WrapperSearch;

public class OMDBService implements ControllerServiceInterface {

	private MySQLFacade mySQLFacade;
	private List<OmdbVideoBasic> OMDBResults;

	public OMDBService() {
		super();
		this.mySQLFacade = new MySQLFacade();
		this.OMDBResults = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		// try {
		OmdbApi omdb = new OmdbApi();
		WrapperSearch result = null;
		try {
			result = omdb.search(query);
		} catch (OMDBException e) {
			e.printStackTrace();
		}
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		this.OMDBResults = result.getResults();

		/*
		 * ObjectWriter ow = new
		 * ObjectMapper().writer().withDefaultPrettyPrinter(); String json =
		 * null; try { json = ow.writeValueAsString(OMDBResults); } catch
		 * (JsonGenerationException | JsonMappingException ex) {
		 * ex.printStackTrace(); } catch (IOException ioe) {
		 * ioe.printStackTrace(); } System.out.println("\n" + "OMDB Results : "
		 * + OMDBResults.size()); return json;
		 * 
		 * } catch (Exception e) { e.printStackTrace(); } return null;
		 */

		// Get detailed information for each search result
		ArrayList<OmdbVideoFull> searchFull = new ArrayList<OmdbVideoFull>();
		// Get Full description for each search
		for (OmdbVideoBasic searchBasic : this.OMDBResults) {
			String newQuery = searchBasic.getTitle();
			OmdbVideoFull result2;

			try {
				result2 = omdb.movieInfo(newQuery);
				System.out.println(result2.getPoster());
				searchFull.add(result2);
			} catch (OMDBException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Return JSON
		try {
			generatedJson = ow.writeValueAsString(searchFull);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			JSONArray omdbJsonArray = new JSONArray(generatedJson);
			jsonResponse.put("Service", RequestTypes.IMDB.toString());
			jsonResponse.put("Results", this.OMDBResults.size());
			jsonResponse.put("Query", query);
			jsonResponse.put("IMDB", omdbJsonArray);

			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println(duration + " ms");

			return jsonResponse.toJSONString();
		} catch (JSONException e) {
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
		boolean cached = this.mySQLFacade.insertOMDBItems(this.OMDBResults);
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
