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
		result.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); 
		result.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
		result.setEncryptFiles(true);
		result.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
		result.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);

		
		return result;
	}

}
