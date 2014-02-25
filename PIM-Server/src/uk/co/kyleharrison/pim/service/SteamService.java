package uk.co.kyleharrison.pim.service;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

public class SteamService {

	// To get a lits of all available games
	// http://api.steampowered.com/ISteamApps/GetAppList/v0001/
	@SuppressWarnings("unchecked")
	public String testSteam(String query) {
		
		try {
			// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
			JSONArray itemArray = new JSONArray();
			for (Entry<Integer, SteamGame> entry : sg.entrySet()) {
				SteamGame sg1 = entry.getValue();
				if (sg1.getName().contains(query)) {
					System.out.println(sg1.getName());
				
				JSONObject item = new JSONObject();
				item.put("id", sg1.getId());
				item.put("name", sg1.getName());
				item.put("resource_type", "GAME");
				item.put("icon_URL", sg1.getIconUrl());
				item.put("steam_id", sg1.getAppId());
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

}
