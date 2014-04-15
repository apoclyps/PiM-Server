package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map.Entry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

public class SteamAPITest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void querySteamAPIForUser(){
        try {
        	// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
		
			assertNotNull(sg);
			
			int count = 0;
			for(Entry<Integer, SteamGame> entry: sg.entrySet()) {
		        SteamGame sg1= entry.getValue();
		        if(sg1.getName().contains("Batman")){
		        	//System.out.println(sg1.getName());
		        	count++;
		        }
		    }
			assertEquals("Total Batman Games", count, 3);
        }catch (SteamCondenserException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void querySteamAPIForUserTotalLibary(){
        try {
        	// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
		
			assertNotNull(sg);
			assertEquals("Total Games", sg.size(), 514);
 
        }catch (SteamCondenserException e) {
			e.printStackTrace();
		}
	}

}
