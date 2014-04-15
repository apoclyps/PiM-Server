package uk.co.kyleharrison.test.pim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.co.kyleharrison.test.pim.api.AmazonAPITest;
import uk.co.kyleharrison.test.pim.api.ComicvineAPITest;
import uk.co.kyleharrison.test.pim.api.IsbnDBAPITest;
import uk.co.kyleharrison.test.pim.api.OmdbAPITest;
import uk.co.kyleharrison.test.pim.api.SpotifyAPITest;
import uk.co.kyleharrison.test.pim.api.SteamAPITest;

@RunWith(Suite.class)
@SuiteClasses({ ComicvineAPITest.class, SpotifyAPITest.class,AmazonAPITest.class,OmdbAPITest.class, IsbnDBAPITest.class, SteamAPITest.class })
public class APITestSuite {

}
