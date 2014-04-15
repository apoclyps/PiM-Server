package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mlesniak.amazon.backend.AmazonItem;

public class AmazonAPITest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createAmazonItemNull() {
		AmazonItem amazonItem = null;
		assertNull(amazonItem);
	}

	@Test
	public void createAmazonItem() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertNotEquals(amazonItem, null);
	}

	@Test
	public void retrieveASIN() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getAsin(), "123456789");
	}

	@Test
	public void retrieveTitle() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getTitle(), "Default Item");
	}

	@Test
	public void retrievePrice() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getPrice(), 399);
	}

	@Test
	public void retrieveURL() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getURL(), "www.item.com");
	}

	@Test
	public void retireveImageURL() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getImageURL(), "www.item.com/image");
	}

	@Test
	public void retireveReviewURL() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		assertEquals(amazonItem.getReviewURL(), "www.item.com/review");
	}

	@Test
	public void retrieveToString() {
		AmazonItem amazonItem = new AmazonItem("123456789", "Default Item",
				399, "www.item.com", "www.item.com/image",
				"www.item.com/review");
		
		String result = "AmazonItem{\n" 
				+ "  asin='123456789'\n"
				+ ", title='Default Item'\n" 
				+ ", price=399\n"
				+ ", URL=www.item.com\n" 
				+ ", imageURL=www.item.com/image\n"
				+ ", reviewURL=www.item.com/review\n" 
				+ "}";

		assertEquals(amazonItem.toString(), result);
	}

}
