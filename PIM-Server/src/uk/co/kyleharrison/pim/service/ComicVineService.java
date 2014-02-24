package uk.co.kyleharrison.pim.service;

import java.io.IOException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;

public class ComicVineService extends DatabaseConnector {

	public String testGrapeVine(String query){
		GrapeVineFacade grapeVineFacade = new GrapeVineFacade();
		
		String resources = "name,id,first_issue,last_issue,count_of_issues";
		String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list="+resources+"&resources=volume&query=";
		
		grapeVineFacade.PreformQuery(queryRequest+query);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(grapeVineFacade.getComicVineVolumes());
		} catch (JsonGenerationException | JsonMappingException ex) {
			ex.printStackTrace();
		}catch( IOException ioe){
			ioe.printStackTrace();
		}
		return json;
	}
	
}
