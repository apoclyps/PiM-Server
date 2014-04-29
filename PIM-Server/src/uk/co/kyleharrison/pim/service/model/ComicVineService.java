package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

public class ComicVineService extends DatabaseConnector implements ControllerServiceInterface{

	//private MySQLFacade mySQLFacade;
	private GrapeVineFacade grapeVineFacade;
	private ArrayList<ComicVineVolume> cvv = null;
	private ComicvineConnector cc;
	private String resources = "name,id,first_issue,last_issue,count_of_issues,image,description,deck";
	private String queryRequest = "http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
			+ "&format=json&field_list="+resources+"&resources=volume&limit=25&query=";
	private boolean downloadImages = true;
	
	public ComicVineService() {
		super();
		try{
		this.cc = new ComicvineConnector();
		}catch(Exception e){
			Log.info("Ensure Context.xml is accessible");
		}
		this.grapeVineFacade = new GrapeVineFacade();
	}

	@Override
	public boolean cacheAllResults() {
		// TODO Auto-generated method stub
		try{
			//return this.mySQLFacade.insertVolumes(this.cvv);
			return this.cc.insertVolumes(this.cvv);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cacheAllResultsCassandra() {
		try{
			this.cc.insertVolumes(this.cvv);
			//comicvineConnector.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cacheIssues(String volumeID) {	
		System.out.println("CACHING ISSUES "+this.grapeVineFacade.getComicVineVolumes().get(0).getIssues().size() );
		/*for(ComicVineVolume vi :this.grapeVineFacade.getComicVineVolumes() ){
			System.out.println(vi.getIssues().get(0).getName());
		}*/
		//boolean cached = this.mySQLFacade.insertIssues(this.grapeVineFacade.getComicVineVolumes(),volumeID);
		boolean cached;
		try {
			cached = this.cc.insertComicVineIssues(this.grapeVineFacade.getComicVineVolumes().get(0).getIssues(),volumeID.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cached = false;
		}
		//this.mySQLFacade.closeConnection();
		return cached;
	}
	
	public boolean cacheIssue(String volumeID,ComicVineIssue comicvineIssue) {	
		System.out.println("CACHING ISSUE "+comicvineIssue.getName());
		//boolean cached = this.mySQLFacade.insertComicVineIssue(comicvineIssue,volumeID);
		boolean cached = false;
		return cached;
	}
	
	
	@Override
	public boolean cacheItem(String item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean cacheResults() {
		System.out.println("Caching Results");
		//boolean cached = this.mySQLFacade.insertVolumes(this.grapeVineFacade.getComicVineVolumes());
		boolean cached;
		try {
			cached = this.cc.insertVolumes(this.grapeVineFacade.getComicVineVolumes());
		} catch (SQLException e) {
			e.printStackTrace();
			cached = false;
		}
		return cached;
	}

	public void close(){
		//this.mySQLFacade.closeConnection();
		this.cc.close();
	}

	public String encodeQuery(String query){
		return query.replaceAll(" ", "%20AND%20");
	}
	
	public boolean executeIDQuery(int id) {
		grapeVineFacade.PreformQuery(queryRequest + id);
		this.cvv = grapeVineFacade.getComicVineVolumes();
		System.out.println("\tResult Size : " +this.cvv.size());

		return true;
	}
	
	public String executeIssueQuery(String volumeID) {
		long startTime = System.currentTimeMillis();
		this.cvv = null;
		volumeID = encodeQuery(volumeID);

		//ArrayList<ComicVineIssue> issues = this.comicvineConnector.findAllIssues(volumeID);
		ArrayList<ComicVineIssue> issues = this.cc.findAllIssues(volumeID);
		
		if(issues==null || issues.size()==0){
			// Preform Issue Query to get images
			System.out.println("\n\nVOLUME QUERY : "+volumeID);
			this.cvv = preformIssueQuery(volumeID);
			
			//Retrieve images from database
			//issues = this.cc.findAllIssues(volumeID);

			//Update current object with image strings
			issues = updateIssues(this.cvv.get(0).getIssues());		
			issues = issueSort(issues);	
		}else{
			System.out.println("CACHE ACCESSED");
			ComicVineVolume tempComicVineVolume = new ComicVineVolume();
			tempComicVineVolume.setId(Integer.parseInt(volumeID));
			issues = issueSort(issues);
			tempComicVineVolume.setIssues(issues);
			this.cvv = new ArrayList<ComicVineVolume>();
			this.cvv.add(tempComicVineVolume);
		}
		
		//Generate JSON Response
		return response(issues, volumeID,startTime);
	}
	
	public ArrayList<ComicVineIssue> issueSort(ArrayList<ComicVineIssue> issues){
		 Collections.sort(issues, new Comparator<ComicVineIssue>(){
			    @Override
			    public int compare(final ComicVineIssue o1, final ComicVineIssue o2){
			        // let your comparator look up your car's color in the custom order
			        return Integer.valueOf(	(int) (Math.floor(	(	Double.valueOf(o1.getIssue_number()))	))
			        		).compareTo(Integer.valueOf(	(int) (Math.floor(	(	Double.valueOf(o2.getIssue_number()))	))));
			    }
			});
		
		return new ArrayList<ComicVineIssue>( issues.subList(0, 150));
	}
	
	public ArrayList<ComicVineIssue> updateIssues(ArrayList<ComicVineIssue> issues){
		ArrayList<ComicVineIssue> tempIssues = new ArrayList<ComicVineIssue>();
		try{
			for(ComicVineIssue cvi : issues)
			{
				//CODE HERE TO HANDLE MISSING NAMES
				try{
					if(cvi.getName().length()<=0){
						cvi.setName(cvi.getVolume().getName());
					}
				}catch(NullPointerException e){
					System.out.println("Exception whilst changing name from null");
					cvi.setName(cvi.getVolume().getName());
				}
				
				if(cvi.getDescription()=="" || cvi.getDescription()=="null"|| cvi.getDescription()!=null){
					cvi.setDescription("No Description Available");
				}
				try{
					cvi.setImage_url(cvi.getImage().getThumb_url());
				}catch(IndexOutOfBoundsException e){
					System.out.println("Error setting IMAGE URL"+issues.size());
					//cvi.setImage_url(issues.get(i).getImage().getThumb_url());
				}
				tempIssues.add(cvi);
				//System.out.println("Issues Added"+cvi.getName());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return tempIssues;
	}
	
	
	public ArrayList<ComicVineIssue> sortIssues(ArrayList<ComicVineIssue> issues){
		Collections.sort(issues);
		return issues;
	}
	
	@SuppressWarnings("unchecked")
	public String response(ArrayList<ComicVineIssue> issues,String volumeID,long startTime){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		
		issues = issueSort(issues);
		
		try {
			generatedJson = ow.writeValueAsString(issues);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			JSONArray cvvResults = new JSONArray(generatedJson);
			jsonResponse.put("Results", issues.size());
			jsonResponse.put("Query", URLDecoder.decode(volumeID));
			jsonResponse.put("COMICVINE", cvvResults);
			jsonResponse.put("ResourceType", "Issues");
			
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
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public String executeQuery(String query) {
		long startTime = System.currentTimeMillis();
		
		query = encodeQuery(query);
		ArrayList<ComicVineVolume> comicvineVolumesResponse = preformQuery(query);
		comicvineVolumesResponse = volumeSort(comicvineVolumesResponse);
		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		JSONObject jsonResponse = new JSONObject();
		String generatedJson = null;
		

		try {
			generatedJson = ow.writeValueAsString(comicvineVolumesResponse);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			JSONArray cvvResults = new JSONArray(generatedJson);
			jsonResponse.put("Results", comicvineVolumesResponse.size());
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
	
	public ArrayList<ComicVineVolume> volumeSort(ArrayList<ComicVineVolume> volumes){
		 Collections.sort(volumes, new Comparator<ComicVineVolume>(){
			    @Override
			    public int compare(final ComicVineVolume o1, final ComicVineVolume o2){
			        // let your comparator look up your car's color in the custom order
			        return Integer.valueOf(	(int) (Math.floor(	(	Double.valueOf(o2.getCount_of_issues()))	))
			        		).compareTo(Integer.valueOf(	(int) (Math.floor(	(	Double.valueOf(o1.getCount_of_issues()))	))));
			    }
			});
		
		return new ArrayList<ComicVineVolume>( volumes.subList(0, 15));
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
	
	public ArrayList<ComicVineVolume> grabIssueImages(ArrayList<ComicVineVolume> comicvineVolumes){
		ComicVineVolume cvv = comicvineVolumes.get(0);
			ArrayList<ComicVineIssue> issues = new ArrayList<ComicVineIssue>();
			for(ComicVineIssue currentIssue : cvv.getResults().getIssues()){
				ComicVineIssue currentIssueSearch = new ComicVineIssue();
				// Get currentIssueSearch
				currentIssueSearch = requestImageForIssue(currentIssue.getId());
				//System.out.println(currentIssueSearch.getImage_url());
				issues.add(currentIssueSearch);
			}
			cvv.setIssues(issues);
			//Store
			comicvineVolumes.add(cvv);
		return comicvineVolumes;
	}
	
	public ArrayList<ComicVineVolume> preformIssueQuery(String volumeID) {
		String issueQuery = "http://www.comicvine.com/api/volume/4050-"+volumeID+"/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=issues,id,name&sort=id&limit=15";
		
		System.out.println("ISSUE QUERY \n"+issueQuery);
		this.grapeVineFacade = new GrapeVineFacade();
		this.grapeVineFacade.PreformIssueQuery(issueQuery);
		
		//System.out.println("ISSUE QUERY RETURN SIZE = "+ this.grapeVineFacade.getComicVineVolumes().size());
		//this.cvv = this.grapeVineFacade.getComicVineVolumes()
		//System.out.println("Grab" +grabIssueImages(this.grapeVineFacade.getComicVineVolumes()));
		if(downloadImages==true){
			ArrayList<ComicVineVolume> comicvinevolumes = grabIssueImages(this.grapeVineFacade.getComicVineVolumes());
			//System.out.println("preformIssueQuery  "+comicvinevolumes.get(0).getIssues().get(0).getImage().getThumb_url());
			this.grapeVineFacade.setComicVineVolumes(comicvinevolumes);
		}
		//return grabIssueImages(this.grapeVineFacade.getComicVineVolumes());
		cacheIssues(volumeID);
		return this.grapeVineFacade.getComicVineVolumes();
	}

	public ArrayList<ComicVineVolume> preformQuery(String query) {
		this.grapeVineFacade.PreformQuery(queryRequest+query);
		return this.grapeVineFacade.getComicVineVolumes();
	}

	public ComicVineIssue requestImageForIssue(int id){
		String issueQuery = "http://www.comicvine.com/api/issue/4000-"+id+"/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=id,description,cover_date,image,issue_number,name,volume";
		//System.out.println(issueQuery);
		this.grapeVineFacade.PreformIssueImageQuery(issueQuery);
		ComicVineIssue cvi = this.grapeVineFacade.getComicVineIssue();
		
		//System.out.println("Request Image For Issue " +cvi.getImage().getThumb_url());
		//cvi.setImage_url(cvi.getImage().getSmall_url());
		return cvi;
	}
	
	@Override
	public boolean searchItemExists(String item) {
		// TODO Auto-generated method stub
		return false;
	}

}
