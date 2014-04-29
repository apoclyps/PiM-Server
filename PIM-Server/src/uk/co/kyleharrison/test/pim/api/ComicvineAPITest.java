package uk.co.kyleharrison.test.pim.api;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.grapejuice.facade.GrapeVineFacade;

public class ComicvineAPITest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGrapeVine(){
		GrapeVineFacade grapeVineFacade = new GrapeVineFacade();
		
		grapeVineFacade.PreformQuery("http://www.comicvine.com/api/search/?api_key=2736f1620710c52159ba0d0aea337c59bd273816"
				+ "&format=json&field_list=name,id&query=batman");
	
		//System.out.println(grapeVineFacade.getComicVineVolumes().size());
		//System.out.println(grapeVineFacade.getComicVineVolumes().get(0).getName());
		
		assertEquals("Collection Size", grapeVineFacade.getComicVineVolumes().size(),353);
		assertEquals("First Issue",grapeVineFacade.getComicVineVolumes().get(0).getName(),"Batman");
	}
}
