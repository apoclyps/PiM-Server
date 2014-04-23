package uk.co.kyleharrison.test.pim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.co.kyleharrison.test.pim.service.AsylumServiceTest;
import uk.co.kyleharrison.test.pim.service.ComicVineIssueTest;
import uk.co.kyleharrison.test.pim.service.ComicVineServiceTest;
import uk.co.kyleharrison.test.pim.service.SpotifyServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ AsylumServiceTest.class, ComicVineIssueTest.class,
		ComicVineServiceTest.class,SpotifyServiceTest.class })
public class ServiceTestSuite {

}
