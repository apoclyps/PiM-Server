package uk.co.kyleharrison.test.pim;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import uk.co.kyleharrison.grapejuice.comicvine.ComicVineIssue;
import uk.co.kyleharrison.pim.storage.mysql.connector.ComicVineConnectorMySQL;

import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ComicVineConnectorTest {

	ComicVineConnectorMySQL comicvineConnector;
	ArrayList<ComicVineIssue> comicvineIssues;
	
	@Before
	public void setUp() throws Exception {
		this.comicvineConnector = new ComicVineConnectorMySQL();
		this.comicvineIssues = new ArrayList<ComicVineIssue>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void findVolumeByID() {
		assert(this.comicvineConnector.findIssues("3092"));
	}
	
	@Test
	public void nonExistingVolume() {
		assert(this.comicvineConnector.findIssues("19993004012")==false);
	}
	
	@Test
	public void retrieveIssues() {
		this.comicvineIssues = this.comicvineConnector.findAllIssues("3092");
		assert(this.comicvineIssues.size()>0);
		this.comicvineIssues = null;
	}
	
	@Test
	public void checkRecordsExists() {
		this.comicvineIssues = this.comicvineConnector.findAllIssues("3092");
		assertFalse(this.comicvineIssues.size()==0);
		assertEquals(this.comicvineIssues.get(0).getId(),21057);
		assertEquals(this.comicvineIssues.get(0).getName(),"Mind Out of Time!");
		this.comicvineIssues = null;
	}
	
}
