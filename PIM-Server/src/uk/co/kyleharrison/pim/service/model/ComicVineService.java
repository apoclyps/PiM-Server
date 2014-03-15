package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.pim.cassandra.CassandraConnector;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;
import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	private MySQLFacade mySQLFacade;
	private GrapeVineFacade grapeVineFacade;
	private ArrayList<ComicVineVolume> cvv = null;
	String resources = "name,id,first_issue,last_issue,count_of_issues,image";
	String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
			+ "&format=json&field_list="+resources+"&resources=volume&query=";
	
	public ComicVineService() {
		super();
		this.mySQLFacade = new MySQLFacade();
		this.grapeVineFacade = new GrapeVineFacade();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		
		ArrayList<ComicVineVolume> cvv = preformQuery(query);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;

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

	public ArrayList<ComicVineVolume> preformQuery(String query) {

		
		this.grapeVineFacade.PreformQuery(queryRequest+query);
		
		return this.grapeVineFacade.getComicVineVolumes();
	}

	@Override
	public boolean executeQueryAllResults(String query) {
		
		grapeVineFacade.PreformQuery(queryRequest + query);
		
		this.cvv = grapeVineFacade.getComicVineVolumes();
		System.out.println("\tSize : " +this.cvv.size());

		// OUTPUT
		int remainder = (int) (Math.ceil(grapeVineFacade.getNumber_of_total_results() / 100)) ;
		System.out.println("\tRemaining Pages : " +remainder);
		int page = 1;
		boolean exit = false;
		
		do{
			System.out.println("\tPreforming query");
			grapeVineFacade.PreformQuery(queryRequest + query + "&page=" + (page+1));

			if (grapeVineFacade.getNumber_of_page_result() != 0) {
				this.cvv.addAll(grapeVineFacade.getComicVineVolumes());
			}else{
				exit = true;
			}
			if(page==remainder ){
				exit = true;
			}
			page++;
		}while (exit==false);
		
		System.out.println("Expected Size = "
				+ grapeVineFacade.getNumber_of_total_results());
		System.out.println("Actual Size = " + this.cvv.size());
		System.out.println("Expected Pages = " + (remainder));
		
		return true;
	}

	@Override
	public boolean cacheAllResults() {
		// TODO Auto-generated method stub
		try{
			return this.mySQLFacade.insertVolumes(this.cvv);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cacheAllResultsCassandra() {
		// TODO Auto-generated method stub
		try{
			CassandraConnector cc = new CassandraConnector();
			cc.insertComicVineVolumes(this.cvv);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	

}
