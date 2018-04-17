package org.fcpe.fantinlatour.dao.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptHelperTest {

	@Test
	public void testEncryption() {
		EncryptHelper encryptHelper= new EncryptHelper();
		encryptHelper.setPassword("PssWOrd");
		String encryptedText = encryptHelper.encrypt("MyPlainTest");
		assertEquals("MyPlainTest", encryptHelper.decrypt(encryptedText));
	}
	
}
