package uk.co.kyleharrison.pim.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import net.scholnick.isbndb.BooksProxy;
import net.scholnick.isbndb.domain.Book;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;
import uk.co.kyleharrison.pim.connectors.DatabaseConnector;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamGame;
import com.github.koraktor.steamcondenser.steam.community.SteamId;
import com.mixtape.spotify.api.Album;
import com.mixtape.spotify.api.RequestType;
import com.mixtape.spotify.api.Response;
import com.mixtape.spotify.api.SpotifyMetadata;
import com.mlesniak.amazon.backend.AmazonItem;
import com.mlesniak.amazon.backend.AmazonItemConverter;
import com.mlesniak.amazon.backend.AmazonRequest;
import com.mlesniak.amazon.backend.AmazonRequestBuilder;
import com.mlesniak.amazon.backend.SearchIndex;
import com.omertron.omdbapi.OMDBException;
import com.omertron.omdbapi.OmdbApi;
import com.omertron.omdbapi.model.OmdbVideoBasic;
import com.omertron.omdbapi.wrapper.WrapperSearch;

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
