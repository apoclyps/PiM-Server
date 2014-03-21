package uk.co.kyleharrison.test.pim.utility;

import static org.junit.Assert.*;

import org.apache.cassandra.utils.UUIDGen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimestampTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void createUUID(){
		UUIDGen uuidGen = null;
		assertNull(uuidGen);
	}
	
	@Test
	public void createTimestamp() {
		assertNotNull(UUIDGen.getTimeUUID());
	}
	
	@Test
	public void compareTimestamps() {
		assertNotEquals(UUIDGen.getTimeUUID(),UUIDGen.getTimeUUID());
	}

}
