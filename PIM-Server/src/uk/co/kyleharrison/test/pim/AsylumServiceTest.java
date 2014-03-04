package uk.co.kyleharrison.test.pim;

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
		AsylumService as = new AsylumService(1000,1100);
		boolean success = as.executeQuery();
		assert(success==true);
	}

}
