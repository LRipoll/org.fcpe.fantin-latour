package org.fcpe.fantinlatour.dao.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

public class EncryptHelper {

	public static final String ID = "encryptHelper";

	private static final Logger logger = Logger.getLogger(EncryptHelper.class);

	private static final String ALGORITHM = "Blowfish";

	private SecretKeySpec secretKeySpec;

	public EncryptHelper() {
		super();

	}

	public void setPassword(String password) {

		secretKeySpec = new SecretKeySpec(password.getBytes(), ALGORITHM);
	}

	public String encrypt(String plainText) {
		String result = null;

		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			byte[] encrypted = cipher.doFinal(plainText.getBytes());
			result = new String(encrypted);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return result;

	}

	public String decrypt(String encryptedText) {
		String result = null;
		try {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			byte[] decrypted = cipher.doFinal(encryptedText.getBytes());
			result = new String(decrypted);

		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {

		}

		return result;
	}

}
