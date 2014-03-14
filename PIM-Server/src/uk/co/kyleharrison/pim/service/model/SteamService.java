package uk.co.kyleharrison.pim.service.model;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

// To get a lits of all available games
// http://api.steampowered.com/ISteamApps/GetAppList/v0001/

public class SteamService implements ControllerServiceInterface {

	private HashMap<Integer, SteamGame> sg;
	private String steamID;
	private MySQLFacade mySQLFacade;
	
	public SteamService() {
		super();
		this.sg = null;
		this.steamID = null;
		this.mySQLFacade = new MySQLFacade();
	}
	
	public String getSteamID() {
		return steamID;
	}

	public void setSteamID(String steamID) {
		this.steamID = steamID;
	}
	
	public HashMap<Integer, SteamGame> getSg() {
		return sg;
	}

	public void setSg(HashMap<Integer, SteamGame> sg) {
		this.sg = sg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery(String query) {
		try {
			// Queries are case sensitive
			SteamId id = SteamId.create(steamID);
			this.sg = null;
			this.sg = id.getGames();
			JSONArray itemArray = new JSONArray();
			for (Entry<Integer, SteamGame> entry : sg.entrySet()) {
				SteamGame sg1 = entry.getValue();

				JSONObject item = new JSONObject();
				item.put("id", sg1.getId());
				item.put("steam_id", sg1.getAppId());
				item.put("name", sg1.getName());
				item.put("resource_type", "GAME");
				item.put("icon_URL", sg1.getIconUrl());
				item.put("thumbnail_URL", sg1.getLogoThumbnailUrl());
				itemArray.add(item);

			}
			System.out.println("Total Games" + sg.size());
			JSONObject results = new JSONObject();
			results.put("Steam", itemArray);
			return results.toString();

		} catch (SteamCondenserException e) {
			e.printStackTrace();
			//results = "{ \"Steam\": \"offline\" ";
		}
		return "{ \"Steam\": \"offline\" }";
	}
	
	@SuppressWarnings("unchecked")
	public String executeQueryComparison(String query) {
		try {
			// Queries are case sensitive
			SteamId id = SteamId.create(steamID);
			HashMap<Integer, SteamGame> sg = id.getGames();
			JSONArray itemArray = new JSONArray();
			for (Entry<Integer, SteamGame> entry : sg.entrySet()) {
				SteamGame sg1 = entry.getValue();
				if (sg1.getName().contains(query)) {
					System.out.println(sg1.getName());
				
				JSONObject item = new JSONObject();
				item.put("id", sg1.getId());
				item.put("steam_id", sg1.getAppId());
				item.put("name", sg1.getName());
				item.put("resource_type", "GAME");
				item.put("icon_URL", sg1.getIconUrl());
				item.put("thumbnail_URL", sg1.getLogoThumbnailUrl());
				itemArray.add(item);
				}
			}
			System.out.println("Total Games" + sg.size());
			JSONObject results = new JSONObject();
			results.put("Steam", itemArray);
			return results.toString();

		} catch (SteamCondenserException e) {
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
	public boolean cacheResults() {
		if(this.sg.equals(null)){
			System.out.println("Caching Results");
			return this.mySQLFacade.insertSteamGames(this.sg);
		}
		return false;
	}

	@Override
	public boolean executeQueryAllResults(String query) {
		// TODO Auto-generated method stub
		return false;
	}

}
