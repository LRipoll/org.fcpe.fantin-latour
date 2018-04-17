package org.fcpe.fantinlatour.dao.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EncryptHelperTest {

	@Test
	public void testEncryption() {
		EncryptHelper encryptHelper= new EncryptHelper();
		encryptHelper.setPassword("PassWOrd");
		String encryptedText = encryptHelper.encrypt("MyPlainTest to be encrypted");
		assertEquals("MyPlainTest to be encrypted", encryptHelper.decrypt(encryptedText));
	}
	
}
