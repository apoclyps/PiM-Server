package uk.co.kyleharrison.test.pim.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineVolume;
import uk.co.kyleharrison.pim.service.model.ComicVineService;

public class ComicVineIssueTest {

	ComicVineService comicVineService;
	ArrayList<ComicVineVolume> cvv;
	
	@Before
	public void setUp() throws Exception {
		this.comicVineService = new ComicVineService();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void queryVolumeForIssues() {
		this.cvv = this.comicVineService.preformIssueQuery("57181");
		System.out.println(cvv.get(0).getId());
		System.out.println(cvv.size());
		System.out.println(cvv.get(0).getResults().getIssues().size());
		assert(cvv!=null);
		assertEquals(cvv.get(0).getResults().getIssues().size(),20);
	}
	
	@Test
	public void checkResultSize(){
		assert(cvv.get(0).getResults().getIssues().size()>=20);
	}
	
	@Test
	public void checkID(){
		assert(cvv.get(0).getResults().getIssues().get(1).getId()==388566);
	}
	
	@Test
	public void checkResultName(){
		assert(cvv.get(0).getResults().getName()=="Batman: Legends of the Dark Knight");
	}

}
