package org.fcpe.fantinlatour.dao.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class EncryptHelper {

	private static final String ENCODING = "UTF-8";

	public static final String ID = "encryptHelper";

	private static final Logger logger = Logger.getLogger(EncryptHelper.class);

	private static final String ALGORITHM = "Blowfish";

	private SecretKeySpec secretKeySpec;

	private Cipher cipher;

	public EncryptHelper() {
		super();
		setPassword("test");
		try {
			cipher = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}

	}

	public void setPassword(String password) {

		secretKeySpec = new SecretKeySpec(password.getBytes(), ALGORITHM);

	}

	public String encrypt(String plainText) {
		String result = null;

		try {

			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encrypted = cipher.doFinal(plainText.getBytes(ENCODING));
			
			result = new String(Base64.getEncoder().encode(encrypted));
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;

	}

	public String decrypt(String encryptedText) {
		String result = null;
		try {
			
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedText.getBytes(ENCODING)));
			result = new String(decrypted);

		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | UnsupportedEncodingException  e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		return result;
	}

}
