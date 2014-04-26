package uk.co.kyleharrison.test.pim.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.pim.service.model.SteamService;

public class SteamServiceTest {

	private SteamService SteamService;
	
	@Before
	public void setUp() throws Exception {
		this.SteamService = new SteamService();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void ConstructorTest() {
		assert(this.SteamService!=null);
	}
	
	@Test
	public void QueryTest(){
		this.SteamService.setSteamID("apoclyps");
		String results = this.SteamService.executeQuery("Batman");
		assertNotNull("Steam JSON Response", results);
		assertEquals("Libary Size", this.SteamService.getSg().size(), 514);
	}
	
	

}
