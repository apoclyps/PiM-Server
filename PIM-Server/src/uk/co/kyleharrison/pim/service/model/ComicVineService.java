package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	private MySQLFacade mySQLFacade;
	private GrapeVineFacade grapeVineFacade;
	
	public ComicVineService() {
		super();
		this.mySQLFacade = new MySQLFacade();
		this.grapeVineFacade = new GrapeVineFacade();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		
		String resources = "name,id,first_issue,last_issue,count_of_issues,image";
		String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list="+resources+"&resources=volume&query=";
		
		this.grapeVineFacade.PreformQuery(queryRequest+query);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		ArrayList<ComicVineVolume> cvv = this.grapeVineFacade.getComicVineVolumes();
		try {
			generatedJson = ow.writeValueAsString(cvv);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			JSONArray cvvResults = new JSONArray(generatedJson);
			jsonResponse.put("Results", cvv.size());
			jsonResponse.put("Query", URLDecoder.decode(query));
			jsonResponse.put("COMICVINE", cvvResults);
			jsonResponse.put("ResourceType", "Volume");
			
			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			System.out.println(duration+" ms");
			
			return jsonResponse.toJSONString();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "{ \"ComicVine\": \"No Results\" }";
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
		boolean cached = this.mySQLFacade.insertVolumes(this.grapeVineFacade.getComicVineVolumes());
		return cached;
	}
	
	public void close(){
		this.mySQLFacade.closeConnection();
	}

}
