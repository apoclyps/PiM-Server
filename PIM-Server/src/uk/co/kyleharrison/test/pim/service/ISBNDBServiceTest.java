package uk.co.kyleharrison.test.pim.service;

import static org.junit.Assert.*;

import java.util.List;

import net.scholnick.isbndb.domain.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.pim.service.model.ISBNDBService;

public class ISBNDBServiceTest {

	private ISBNDBService isbndbService;
	
	@Before
	public void setUp() throws Exception {
		this.isbndbService = new ISBNDBService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ConstructorTest() {
		assert(this.isbndbService!=null);
	}
	
	@Test
	public void lookupISBNQuery(){
		this.isbndbService.setBooks(null);
		String results = this.isbndbService.lookupISBN("9781781160855");
		assertNotNull("JSON Response Lookup", results);
		List<Book> books = this.isbndbService.getBooks();
		assertEquals("Book Results", books.size(), 1);
	}
	
	@Test
	public void lookupQuery(){
		this.isbndbService.setBooks(null);
		String results = this.isbndbService.executeQuery("batman");
		//System.out.println(results);
		assertNotNull("JSON Response Query",results);
		List<Book> books = this.isbndbService.getBooks();
		assertEquals("Book Results", books.size(), 10);
	}
}
