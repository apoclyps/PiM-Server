package uk.co.kyleharrison.pim.interfaces;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public interface ControllerServiceInterface {

	public String executeQuery(String query);
	public JSONObject executeJSONQuery(String query);
	public boolean searchItemExists(String item);
	public boolean cacheItem(String item);
	public boolean cacheResults();
	
}
