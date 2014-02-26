package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;
import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		GrapeVineFacade grapeVineFacade = new GrapeVineFacade();
		
		String resources = "name,id,first_issue,last_issue,count_of_issues";
		String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list="+resources+"&resources=volume&query=";
		
		grapeVineFacade.PreformQuery(queryRequest+query);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		ArrayList<ComicVineVolume> cvv = grapeVineFacade.getComicVineVolumes();
		try {
			generatedJson = ow.writeValueAsString(cvv);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			JSONArray cvvResults = new JSONArray(generatedJson);
			jsonResponse.put("Results", cvv.size());
			jsonResponse.put("Query", query);
			jsonResponse.put("COMICVINEVOLUME", cvvResults);
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println(duration+" ms");
			
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
	public boolean cacheResults(ArrayList<Object> results) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
