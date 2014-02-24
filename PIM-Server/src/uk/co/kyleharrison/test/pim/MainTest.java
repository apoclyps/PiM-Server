package uk.co.kyleharrison.test.pim;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;

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

import java.util.List;

import net.scholnick.isbndb.BooksProxy;
import net.scholnick.isbndb.domain.Book;

public class MainTest {

	public static void main(String[] args) {

		String query ="Batman";
		
		System.out.println("\n========================================================================");
		System.out.println("SPOTIFY API : Available movies that match '"+query+"'");	
		System.out.println("========================================================================");
		testSpotify(query);
		
		// A new thread for each will be needed to increase wait times
		System.out.println("\n========================================================================");
		System.out.println("OMDB DB : Available movies that match '"+query+"'");	
		System.out.println("========================================================================");
		testOMDB(query);
		
		System.out.println("\n========================================================================");
		System.out.println("ISBN DB : Available books that match '"+query+"'");
		System.out.println("========================================================================");
		testISBNDB(query);
		
		System.out.println("\n========================================================================");
		System.out.println("STEAM GAMES : Owned Searches that match '"+query+"'");
		System.out.println("========================================================================");
		testSteam(query);
		
		System.out.println("\n========================================================================");
		System.out.println("COMICVINE : Searches that match '"+query+"'");
		System.out.println("========================================================================");
		testGrapeVine(query);
		
		System.out.println("\n========================================================================");
		System.out.println("Amazon Product API : Searches that match '"+query+"'");
		System.out.println("========================================================================");
		testAmazonProductAPI(query);

	}
	
	public static void testAmazonProductAPI(String query) {
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
	
	public static void testISBNDB(String query){
		
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
	
	public static void testOMDB(String query){
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
	public static void testSteam(String query){
        try {
        	// Queries are case sensitive
			SteamId id = SteamId.create("apoclyps");
			HashMap<Integer, SteamGame> sg = id.getGames();
			
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
			e.printStackTrace();
		}
	}
	
	public static void testGrapeVine(String query){
		GrapeVineFacade grapeVineFacade = new GrapeVineFacade();
		
		grapeVineFacade.PreformQuery("http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=name,id&query="+query);
	}
	
	public static void testSpotify(String query){
		SpotifyMetadata metadata = new SpotifyMetadata();
		try {
			Response response = metadata.search(query, RequestType.album);
			
			for( Object artist : response.getAlbums().toArray()){
				Album artistAlbum = (Album) artist;
				System.out.println(artistAlbum.getName());
			}
			System.out.println("\n"+"Total Results : "+response.getAlbums().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
