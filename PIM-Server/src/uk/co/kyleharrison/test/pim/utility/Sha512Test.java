package uk.co.kyleharrison.test.pim.utility;

import static org.junit.Assert.*;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class Sha512Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("SHA512 Hash: " +calculateHash(data, salt));
		
		String username = "kyle90adam@hotmail.com";
		String secret_key = "Ivnwnhu$2015";
		this.salt = username+secret_key;
		this.data="orangesoda$1984";		
		//System.out.println("Actual "+calculateHash(data,salt));
		Assert.assertEquals("973f71325113694bdec8b0d394fe7f6a2d78f2f26fefbb877caf88de12b8114049fbf0c4189debcfe607c7055d8a4aeac5ca928e6ffc4373a1ddaa0acac590a1",
					calculateHash(data,salt));
	}
	
	private static String data = "";
	private static String salt = "ThisIsMySaltForAddedSecurity";
	 
	public static String calculateHash(String data, String salt) {
	    return DigestUtils.sha512Hex(data + salt);
	}
	 
}
