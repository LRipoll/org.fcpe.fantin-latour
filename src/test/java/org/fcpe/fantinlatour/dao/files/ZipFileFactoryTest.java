package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ZipFileFactoryTest {

	@Test
	public void testCreate() throws ZipException {

		ZipFileFactory fileFactory = new ZipFileFactory();
		assertEquals(new File("a.b/c.txt"), fileFactory.create("a.b/c.txt").getFile());
	}

	@Test
	public void testCreateParameters() throws ZipException {

		ZipFileFactory fileFactory = new ZipFileFactory();
		ZipParameters zipParameters = fileFactory.createZipParameters();
		
		assertEquals(Zip4jConstants.COMP_DEFLATE,zipParameters.getCompressionMethod());
		assertEquals(Zip4jConstants.DEFLATE_LEVEL_NORMAL, zipParameters.getCompressionLevel());
		assertTrue( zipParameters.isEncryptFiles());
		assertEquals(Zip4jConstants.ENC_METHOD_AES, zipParameters.getEncryptionMethod());
		assertEquals(Zip4jConstants.AES_STRENGTH_256, zipParameters.getAesKeyStrength());
	}
}
