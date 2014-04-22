package uk.co.kyleharrison.test.pim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ APITestSuite.class, ServiceTestSuite.class, UtilityTestSuite.class })
public class AllTests {

}
