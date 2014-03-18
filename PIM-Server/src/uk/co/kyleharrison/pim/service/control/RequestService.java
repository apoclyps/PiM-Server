package uk.co.kyleharrison.pim.service.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

public class RequestService extends DatabaseConnector {

	@SuppressWarnings("deprecation")
	public void logAccess(String [] requestComponents ){
		System.out.print("GET : RequestController : "+new Date().toLocaleString() + "\t Path Length "+ requestComponents.length +" \t Components");
		for(String component : requestComponents){
			System.out.print("/"+component);
		}
		System.out.println();
	}
	
	public void testAmazonProductAPI(String query) {
        try{
            AmazonRequest request = AmazonRequestBuilder.init()
                    .addKeywords(query)
                    .addSearchIndex(SearchIndex.DVD)
                    .addMaximumPrice(10000)
                    .addMinimumPrice(1000)
                    .build();

            List<AmazonItem> amazonItems = AmazonItemConverter.convertFull(request);
            for (AmazonItem amazonItem : amazonItems) {
                System.out.println(amazonItem);
            }
        }catch(NoSuchFieldError nsfe){
        	System.out.println("No such field error ");
        }
	}
	
	public void testISBNDB(String query){
		
		BooksProxy.getInstance().setDeveloperKey("FB11MLN0"); // before any getBooks() calls
		// all books with virus in the title
		List<Book> books = BooksProxy.getInstance().getBooks(query);
		for(Book bk : books){
			System.out.println(bk.getTitle() +" : "+bk.getEdition() +" : " +bk.getPublisher());
		}
		
		//System.out.println(books.toString());

		/*
		// exact book by ISBN
		Book book = BooksProxy.getInstance().getBookByISBN("9780684853505");
		System.out.println(book.toString());

		// Title with spaces (BooksProxy automatically handles the spaces)
		List<Book> books2 = BooksProxy.getInstance().getBooks("All New X-men");
	//	System.out.println(books2.toString());
		
		for(Book bk : books2){
			System.out.println(bk.getTitle());
			System.out.println(bk.getPublisher());
			System.out.println(bk.getIsbn10());
			System.out.println(bk.getIsbn13()+"\n");
		}
		*/
	}
	
	public void testOMDB(String query){
		OmdbApi omdb = new OmdbApi();
		WrapperSearch result = null;
		try {
			result = omdb.search(query);
		} catch (OMDBException e) {
			e.printStackTrace();
		}
		System.out.println(result.toString());
		for(OmdbVideoBasic ls : result.getResults()){
			System.out.println(ls.getImdbID() + " : "+ ls.getTitle() + " : "+ ls.getYear());
		}
	}
	
	// To get a lits of all available games
	//http://api.steampowered.com/ISteamApps/GetAppList/v0001/
	public void testSteam(String query){
        try {
        	// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
			
			//System.out.println(sg.entrySet().toString());
			
			for(Entry<Integer, SteamGame> entry: sg.entrySet()) {
		     //   System.out.println(entry.getKey() + " : " + entry.getValue());
		        SteamGame sg1= entry.getValue();
		       // System.out.println(sg1.getName());
		        if(sg1.getName().contains(query)){
		        	System.out.println(sg1.getName());
		        }
		    }
			System.out.println("Total Games" +sg.size());
 
        }catch (SteamCondenserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public void testSpotify(String query){
		SpotifyMetadata metadata = new SpotifyMetadata();
		try {
			Response response = metadata.search(query, RequestType.album);
			
			for( Object artist : response.getAlbums().toArray()){
				//System.out.println(" " + artist.toString());
				Album artistAlbum = (Album) artist;
				System.out.println(artistAlbum.getName());
			}
			System.out.println("\n"+"Total Results : "+response.getAlbums().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void JSONPResponse(HttpServletResponse response,
			JSONObject jsonResponse, String callback) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(callback + "(" + jsonResponse + ");");
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void JSONResponse(HttpServletResponse response,
			String jsonResponse) {
		if (jsonResponse != null) {
			response.setContentType("text/x-json;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter out = null;
			response.setContentType("application/json");

			try {
				out = response.getWriter();
				out.print(jsonResponse);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
