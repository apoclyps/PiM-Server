package uk.co.kyleharrison.test.pim;

import org.apache.cassandra.utils.UUIDGen;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MinorTests {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		UUIDGen a ;
		//a.unixTimestamp();
		System.out.println(UUIDGen.getTimeUUID());
		assert(UUIDGen.getTimeUUID().equals(null));
	}

}
