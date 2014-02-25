package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	@SuppressWarnings("unchecked")
	public String testGrapeVine(String query){
		GrapeVineFacade grapeVineFacade = new GrapeVineFacade();
		
		String resources = "name,id,first_issue,last_issue,count_of_issues";
		String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list="+resources+"&resources=volume&query=";
		
		grapeVineFacade.PreformQuery(queryRequest+query);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		JSONObject comicvineOBJ = null;
		try {
			json = ow.writeValueAsString(grapeVineFacade.getComicVineVolumes());
			//comicvineOBJ= new JSONObject();
			//comicvineOBJ.put("comicvine", ow.writeValueAsString(grapeVineFacade.getComicVineVolumes()) );
		} catch (JsonGenerationException | JsonMappingException ex) {
			ex.printStackTrace();
		}catch( IOException ioe){
			ioe.printStackTrace();
		}
		return json;
	}

	@Override
	public String executeQuery(String query) {
		// TODO Auto-generated method stub
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
