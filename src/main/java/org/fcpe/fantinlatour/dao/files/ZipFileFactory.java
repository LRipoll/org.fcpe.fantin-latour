package org.fcpe.fantinlatour.dao.files;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipFileFactory {

	public ZipFile create(String zipFilename) throws ZipException {

		return new ZipFile(zipFilename);
	}

	public ZipParameters createZipParameters() {
		ZipParameters result = new ZipParameters();
		result.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression

		// DEFLATE_LEVEL_NORMAL - Optimal balance between compression level/speed
		result.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

		// Set the encryption flag to true
		result.setEncryptFiles(true);

		// Set the encryption method to AES Zip Encryption
		result.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);

		// AES_STRENGTH_256 - For both encryption and decryption

		result.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

		// TODO to be removed
		result.setPassword("test");
		
		return result;
	}

}
