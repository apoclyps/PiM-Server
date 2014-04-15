package uk.co.kyleharrison.test.pim;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.co.kyleharrison.test.pim.utility.Glicko2RankingTest;
import uk.co.kyleharrison.test.pim.utility.Sha512EncryptionTest;
import uk.co.kyleharrison.test.pim.utility.UUIDTimestampTest;

@RunWith(Suite.class)
@SuiteClasses({ Glicko2RankingTest.class, Sha512EncryptionTest.class, UUIDTimestampTest.class })
public class UtilityTestSuite {

}
