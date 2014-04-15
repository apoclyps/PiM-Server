package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import java.util.List;

import net.scholnick.isbndb.BooksProxy;
import net.scholnick.isbndb.domain.Book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IsbnDBAPITest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void GetBooksByTitle(){
		BooksProxy.getInstance().setDeveloperKey("FB11MLN0"); // before any getBooks() calls
		// all books with virus in the title
		List<Book> books = BooksProxy.getInstance().getBooks("Batman");
	
		assertEquals("Results Size" , books.size(),10);
		
		assertEquals("Book Title", books.get(0).getTitle(),"Batman");
		assertEquals("Book Edition", books.get(0).getEdition(),"Paperback; 1990-05-14");
		assertEquals("Book Publisher", books.get(0).getPublisher(),"Titan Books Ltd");
	}
	
	@Test
	public void GetbookByISBN(){
		// exact book by ISBN
		Book book = BooksProxy.getInstance().getBookByISBN("9780684853505");
		//System.out.println(book.toString());
		assertEquals("Book Title", book.getTitle(),"Bag of bones");
		assertEquals("Book Edition", book.getEdition(),"");
		assertEquals("Book Publisher", book.getPublisher(),"Scribner");
	}
	
	@Test
	public void GetBooksWithSpaces(){
		// Title with spaces (BooksProxy automatically handles the spaces)
		List<Book> books = BooksProxy.getInstance().getBooks("All New X-men");

		assertEquals("Results Size" , books.size(),3);
		
		assertEquals("Book Title", books.get(2).getTitle(),"All-New X-Men - Volume 1: Here Comes Yesterday");
		assertEquals("Book Edition", books.get(2).getIsbn13(),"9780785168201");
		assertEquals("Book Publisher", books.get(2).getPublisher(),"Marvel");
		
	}

}
