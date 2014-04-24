package uk.co.kyleharrison.pim.service.model;

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
import uk.co.kyleharrison.pim.storage.mysql.connector.ProductConnectorMySQL;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.model.OmdbVideoFull;
import com.omertron.omdbapi.wrapper.WrapperSearch;

public class OMDBService implements ControllerServiceInterface {

	private MySQLFacade mySQLFacade;
	private List<OmdbVideoBasic> OMDBResults;
	private ArrayList<OmdbVideoFull> searchResults;

	public OMDBService() {
		super();
		this.mySQLFacade = new MySQLFacade();
		this.OMDBResults = null;
		this.setSearchResults(new ArrayList<OmdbVideoFull>());
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		// try {
		OmdbApi omdb = new OmdbApi();
		WrapperSearch result = null;

		System.out.println(query);
		try {
			result = omdb.search(query);
			//System.out.println(result.toString());
		} catch (OMDBException e) {
			e.printStackTrace();
		}
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		this.OMDBResults = null;
		this.OMDBResults = result.getResults();

		// Get detailed information for each search result
		ArrayList<OmdbVideoFull> searchFull = new ArrayList<OmdbVideoFull>();
		ArrayList<OmdbVideoFull> searchCache = new ArrayList<OmdbVideoFull>();
		// Get Full description for each search
		if(this.OMDBResults.size()>0 || this.OMDBResults!=null){
			for (OmdbVideoBasic searchBasic : this.OMDBResults) {
				//String newQuery = searchBasic.getTitle();
				OmdbVideoFull result2 = null;
	
				// Check if it exists in the database
				ProductConnectorMySQL pcmySQL = new ProductConnectorMySQL();
				
	           if(pcmySQL.findDvd(searchBasic.getImdbID())==true){
	        	   //System.out.println("Exists in database");
	        	   searchFull.add(pcmySQL.getTempVideo());
	           }else{  
	        	   //System.out.println("Creating record");
	        	   //System.out.println(searchBasic.getImdbID() + " " + searchBasic.getTitle());   
					try {
						//String title, String year, String imdbID, String type
						result2 = new OmdbVideoFull(searchBasic.getTitle(),searchBasic.getYear(),searchBasic.getImdbID(),searchBasic.getType());
						//System.out.println(result2.toString());
						result2 = omdb.movieInfo(searchBasic.getImdbID());
						System.out.println(result2.getPoster());
						searchCache.add(result2);
						
						searchFull.add(result2);
					} catch (OMDBException e) {
						// TODO Auto-generated catch block
						System.out.println("Socket Time out for OMDB API");
						//e.printStackTrace();
					}
	           }
			}
			this.setSearchResults(searchFull);
		}else{
			//return no results
		}
		
		if(searchCache.size()>0){
			System.out.println("Full Cache Update "+searchCache.size());
			cacheFullResults(searchCache);
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
	
	public boolean cacheFullResults(ArrayList<OmdbVideoFull> omdbResults) {
		System.out.println("Caching Results");
		boolean cached = this.mySQLFacade.insertOMDBFullItems(omdbResults);
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

	public ArrayList<OmdbVideoFull> getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(ArrayList<OmdbVideoFull> searchResults) {
		this.searchResults = searchResults;
	}

}
