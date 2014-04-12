package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.naming.NoInitialContextException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.mortbay.log.Log;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.pim.cassandra.ComicvineConnector;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;
import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	private MySQLFacade mySQLFacade;
	private GrapeVineFacade grapeVineFacade;
	private ArrayList<ComicVineVolume> cvv = null;
	private String resources = "name,id,first_issue,last_issue,count_of_issues,image,description,deck";
	private String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
			+ "&format=json&field_list="+resources+"&resources=volume&limit=10&query=";
	
	//Finding Issues in a volume
	//http://www.comicvine.com/api/issues/?api_key=2736f1620710c52159ba0d0aea337c59bd273816&volume=796&format=json
	
	//Volume Query
	//http://www.comicvine.com/api/volume/4050-796/?api_key=2736f1620710c52159ba0d0aea337c59bd273816&format=json
	
	public ComicVineService() {
		super();
		try{
		this.mySQLFacade = new MySQLFacade();
		}catch(Exception e){
			Log.info("Ensure Context.xml is accessible");
		}
		this.grapeVineFacade = new GrapeVineFacade();
	}

	public String encodeQuery(String query){
		System.out.println("Query String :"+query);
		System.out.println("Query After  :"+query.replaceAll(" ", "%20AND%20") );
		return query.replaceAll(" ", "%20AND%20");
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public String executeIssueQuery(String query) {
		long startTime = System.currentTimeMillis();
		
		query = encodeQuery(query);
		
		this.cvv = null;
		this.cvv = preformIssueQuery(query);
	
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;

		try {
			generatedJson = ow.writeValueAsString(this.cvv.get(0).getResults().getIssues());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			JSONArray cvvResults = new JSONArray(generatedJson);
			System.out.println("CVV Results"+cvvResults.length());
			jsonResponse.put("Results", this.cvv.size());
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
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		
		/*try {
			query = URLEncoder.encode(query,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		query = encodeQuery(query);
				
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
	
	public boolean cacheIssues() {
		System.out.println("Caching Issues");
		boolean cached = this.mySQLFacade.insertIssues(this.grapeVineFacade.getComicVineVolumes());
		return cached;
	}
	
	
	public void close(){
		this.mySQLFacade.closeConnection();
	}

	public ArrayList<ComicVineVolume> preformQuery(String query) {

		this.grapeVineFacade.PreformQuery(queryRequest+query);
		
		return this.grapeVineFacade.getComicVineVolumes();
	}
	
	public ArrayList<ComicVineVolume> preformIssueQuery(String queryID) {
		String issueQuery = "http://www.comicvine.com/api/volume/4050-"+queryID+"/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=issues,id,name&sort=id&limit=10";
		System.out.println(issueQuery);
		this.grapeVineFacade = new GrapeVineFacade();
		System.out.println("Issue Query :"+issueQuery);
		this.grapeVineFacade.PreformIssueQuery(issueQuery);
		System.out.println("Size = "+ this.grapeVineFacade.getComicVineVolumes().size());
		//System.out.println("Grab" +grabIssueImages(this.grapeVineFacade.getComicVineVolumes()));
		
		//return grabIssueImages(this.grapeVineFacade.getComicVineVolumes());
		cacheIssues();
		
		return this.grapeVineFacade.getComicVineVolumes();
	}
	
	public ArrayList<ComicVineVolume> grabIssueImages(ArrayList<ComicVineVolume> cvv){

		for(ComicVineVolume currentVolume : cvv){
			ArrayList<ComicVineIssue> issues = new ArrayList<ComicVineIssue>();
			for(ComicVineIssue currentIssue : currentVolume.getResults().getIssues()){
				ComicVineIssue currentIssueSearch = new ComicVineIssue();
				// Get currentIssueSearch
				currentIssueSearch = requestImageForIssue(currentIssue.getId());
				issues.add(currentIssueSearch);
				//Store and Cache
				
			}
			currentVolume.setIssues(issues);
			cvv.add(currentVolume);
		}
		return cvv;
	}
	
	public ComicVineIssue requestImageForIssue(int id){
		String issueQuery = "http://www.comicvine.com/api/issue/4000-"+id+"/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=id,description,cover_date,image,issue_number,name,volume";
		System.out.println(issueQuery);
		this.grapeVineFacade.PreformIssueImageQuery(issueQuery);
		return this.grapeVineFacade.getComicVineIssue();
	}
	
	public boolean executeSimpleQuery(String query) {
		try {
			query = URLEncoder.encode(query,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		query = encodeQuery(query);
		grapeVineFacade.PreformQuery(queryRequest + query);
		this.cvv = grapeVineFacade.getComicVineVolumes();
		System.out.println("\tResult Size : " +this.cvv.size());

		return true;
	}
	
	public boolean executeIDQuery(int id) {
		
		grapeVineFacade.PreformQuery(queryRequest + id);
		this.cvv = grapeVineFacade.getComicVineVolumes();
		System.out.println("\tResult Size : " +this.cvv.size());

		return true;
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
		try{
			ComicvineConnector comicvineConnector = new ComicvineConnector();
			comicvineConnector.insertVolumes(this.cvv);
			comicvineConnector.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
