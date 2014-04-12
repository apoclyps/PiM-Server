package uk.co.kyleharrison.test.pim;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.co.kyleharrison.pim.storage.mysql.connector.ComicVineConnectorMySQL;

public class ComicVineConnectorTest {

	ComicVineConnectorMySQL cvcmysql;
	
	@Before
	public void setUp() throws Exception {
		cvcmysql = new ComicVineConnectorMySQL();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void findVolume() {
		assert(this.cvcmysql.findIssues("3092"));
	}
	
	@Test
	public void nonExistingVolume() {
		assert(this.cvcmysql.findIssues("19993004012")==false);
	}

}
