package uk.co.kyleharrison.pim.background;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;

import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class ComicVineBackgroundService implements Runnable {

	private ComicVineService comicVineService;
	private Stack<String> searchStack = new Stack<String>();
	private HashMap<String,String> resultsMap = new HashMap<String,String>();
	private String filePath = "C:/Users/apoclyps/Desktop/Honors Project/unique-titles.csv";
	
    public ComicVineBackgroundService() {
		super();
		this.comicVineService = new ComicVineService();
		//Load File
	}
	
	public void importFile(){
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
			    splitStringToKeyVal(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void splitStringToKeyVal(String line){
	    //String [] result = line.split("{");
	    //resultsMap.put(result[1], result[2]);
		try{
			String [] split = line.split("\t");
			//System.out.println(split.length);
			if(split.length==2){
				//System.out.print(split[0] + " : "+ split[1]);
				storeToHashMap(split[0].substring(1, split[0].length()-1),split[1]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//System.out.println(line);
	}
	
	public void storeToHashMap(String key, String value){
		this.resultsMap.put(key, value);
	}
	
	public void storeToStack(String key){
		this.searchStack.add(key);
	}

	@Override
    public void run() {
        // Do your job here.
    	System.out.println("Background process "+new Date().toString());
    	importFile();
    	
    	for(Entry<String, String> entry : this.resultsMap.entrySet()){
    		System.out.println(entry.getKey());
    	}
    	//this.comicVineService.executeSimpleQuery("avengers");
    	//this.comicVineService.cacheAllResults();
    	//this.comicVineService.cacheAllResultsCassandra();
    	System.out.println("Background update complete");
    	
    }

	public Stack<String> getSearchStack() {
		return searchStack;
	}

	public void setSearchStack(Stack<String> searchStack) {
		this.searchStack = searchStack;
	}

	public HashMap<String,String> getResultsMap() {
		return resultsMap;
	}

	public void setResultsMap(HashMap<String,String> resultsMap) {
		this.resultsMap = resultsMap;
	}

}

