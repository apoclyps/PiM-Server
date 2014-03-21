package uk.co.kyleharrison.test.pim.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.pim.service.scrapper.AsylumService;

public class AsylumServiceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor() {
		AsylumService as = new AsylumService();
		assert(!as.equals(null));
	}
	
	@Test
	public void executeQuery(){
		AsylumService as = new AsylumService(5000,5100);
		as.setNextRequestDelay(1500);
		boolean success = as.executeQuery();
		assert(success==true);
	}

}
