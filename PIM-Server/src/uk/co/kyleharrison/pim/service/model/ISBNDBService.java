package uk.co.kyleharrison.pim.service.model;

import java.io.IOException;
import java.util.List;

import net.scholnick.isbndb.BooksProxy;
import net.scholnick.isbndb.domain.Book;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;

import uk.co.kyleharrison.pim.interfaces.ControllerServiceInterface;
import uk.co.kyleharrison.pim.storage.mysql.MySQLFacade;

public class ISBNDBService implements ControllerServiceInterface {

	private MySQLFacade mySQLFacade;
	private List<Book> books;
	
	public ISBNDBService() {
		super();
		this.mySQLFacade = new MySQLFacade();
		this.books = null;
	}

	public String lookupISBN(String query) {
		try {
			BooksProxy.getInstance().setDeveloperKey("FB11MLN0");
			// exact book by ISBN Book book =
			Book bk = BooksProxy.getInstance().getBookByISBN("9780684853505");

			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(bk);
			} catch (JsonGenerationException | JsonMappingException ex) {
				ex.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String executeQuery(String query) {
		try {
			BooksProxy.getInstance().setDeveloperKey("FB11MLN0"); // before any getBooks() calls
			// all books with virus in the title
			this.books = BooksProxy.getInstance().getBooks(query);
			/*
			 * for(Book bk : books){ System.out.println(bk.getTitle()
			 * +" : "+bk.getEdition() +" : " +bk.getPublisher()); }
			 */
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = null;
			try {
				json = ow.writeValueAsString(books);
			} catch (JsonGenerationException | JsonMappingException ex) {
				ex.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			System.out.println("\n" + "Spotify Results : " + books.size());
			return json;
		} catch (Exception e) {
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
		System.out.println("Caching Results");
		boolean cached = this.mySQLFacade.insertISBNDBBooks(this.books);
		return cached;
	}

}
