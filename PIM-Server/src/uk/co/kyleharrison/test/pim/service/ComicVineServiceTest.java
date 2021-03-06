package uk.co.kyleharrison.test.pim.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class ComicVineServiceTest {

	private ComicVineService comicVineService;
	
	@Before
	public void setUp() throws Exception {
		this.comicVineService = new ComicVineService();
		this.comicVineService.executeQuery("Flash");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ConstructorTest() {
		assert(this.comicVineService!=null);
	}
	
	@Test
	public void QueryTest(){
		String results = this.comicVineService.executeQuery("Superman");
		assert(!results.equals(null));
		System.out.println(results);
	}
	
	@Test 
	public void InsertionTest(){
		boolean result = this.comicVineService.cacheResults();
		assert(result==true);
	}
	
}
