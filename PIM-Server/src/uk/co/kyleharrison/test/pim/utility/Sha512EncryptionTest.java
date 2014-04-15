package uk.co.kyleharrison.test.pim.utility;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sha512EncryptionTest {

	private static String data = "";
	private static String salt = "";
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void encryptData() {
		//System.out.println("SHA512 Hash: " +calculateHash(data, salt));
		String username = "kyle90adam@hotmail.com";
		String secret_key = "secrets5ace$2965";
		this.salt = username+secret_key;
		this.data="thisisthedatatoencrypt";		
		//System.out.println("Actual "+calculateHash(data,salt));
		
		Assert.assertEquals("802ad5f01975397d9761e872f723b05a972b2eabab99301bcec94cb896de343fba2a2584b1385e10df49be7715b9ad3baab478e0126c66eb067fad3f20e54420",
					calculateHash(data,salt));
	}
	
	public static String calculateHash(String data, String salt) {
	    return DigestUtils.sha512Hex(data + salt);
	}
	 
}
