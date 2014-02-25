package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;

import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.wrapper.WrapperSearch;

import uk.co.kyleharrison.interfaces.ControllerServiceInterface;

public class OMDBService implements ControllerServiceInterface {

	@Override
	public String executeQuery(String query) {
		try {
			OmdbApi omdb = new OmdbApi();
			WrapperSearch result = null;
			try {
				result = omdb.search(query);
			} catch (OMDBException e) {
				e.printStackTrace();
			}
		    //System.out.println(result.toString());
			List<OmdbVideoBasic> OMDBResults = result.getResults();
			
			/*for (OmdbVideoBasic ls : result.getResults()) {
				System.out.println(ls.getImdbID() + " : " + ls.getTitle()
						+ " : " + ls.getYear());
			}*/
			
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(OMDBResults);
			} catch (JsonGenerationException | JsonMappingException ex) {
				ex.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			System.out.println("\n" + "OMDB Results : " + OMDBResults.size());
			return json;

		} catch (Exception e) {
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
	public boolean cacheResults(ArrayList<Object> results) {
		// TODO Auto-generated method stub
		return false;
	}

}
