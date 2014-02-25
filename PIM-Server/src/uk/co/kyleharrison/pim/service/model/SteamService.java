package uk.co.kyleharrison.pim.service.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.interfaces.ControllerServiceInterface;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

public class SteamService implements ControllerServiceInterface {

	// To get a lits of all available games
	// http://api.steampowered.com/ISteamApps/GetAppList/v0001/
	@SuppressWarnings("unchecked")
	public String testSteam(String query,String steamID) {
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
			results.put("Amazon", itemArray);
			return results.toString();

		} catch (SteamCondenserException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String executeQuery(String query) {
		try {
			// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
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
			results.put("Amazon", itemArray);
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
	public boolean cacheResults(ArrayList<Object> results) {
		// TODO Auto-generated method stub
		return false;
	}

}
