package org.fcpe.fantinlatour.dao.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EncryptHelperTest {

	@Test
	public void testEncryption() {
		EncryptHelper encryptHelper= new EncryptHelper();
		encryptHelper.setPassword("PassWOrd");
		String encryptedText = encryptHelper.encrypt("MyPlainTest to be encrypted");
		assertEquals("MyPlainTest to be encrypted", encryptHelper.decrypt(encryptedText));
	}
	
	@Test
	public void testIsValid() {
		EncryptHelper encryptHelper= new EncryptHelper();
		assertFalse(encryptHelper.isValid(null));
		assertFalse(encryptHelper.isValid(""));
		assertTrue(encryptHelper.isValid("1"));
	}
	
}
