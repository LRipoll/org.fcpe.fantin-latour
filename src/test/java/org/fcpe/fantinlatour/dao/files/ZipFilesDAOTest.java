package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;

public class ZipFilesDAOTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private ZipFilesDAO zipFilesDAO;
	private FileFactory fileFactory;
	private ZipFileFactory zipFileFactory;
	private String exportDirname;
	private String importDirname;
	private String zipPrefix;
	private String zipSuffix;

	@Before
	public void setUp() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		zipFileFactory = ctrl.createMock(ZipFileFactory.class);
		exportDirname = "exportDirname";
		importDirname = "importDirname";
		zipPrefix = "export-";
		zipSuffix = "arc";
		zipFilesDAO = new ZipFilesDAO(fileFactory, zipFileFactory, exportDirname, importDirname, zipPrefix, zipSuffix);
	}

	@Test
	public void testGetZipFileName() throws DataException {
		support.replayAll();

		assertEquals(exportDirname + File.separator + zipPrefix + "test." + zipSuffix,
				zipFilesDAO.getExportZipAbsoluteFilename("test"));

		support.verifyAll();
	}

	@Test
	public void testExportZipFilenameAlreadyExists() throws DataException {

		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(exportDirname + File.separator + zipPrefix + "test" + "." + zipSuffix))
				.andReturn(inputFile);

		EasyMock.expect(inputFile.exists()).andReturn(true);
		support.replayAll();

		assertTrue(zipFilesDAO.exportZipFilenameAlreadyExists("test"));

		support.verifyAll();
	}

	@Test
	public void testPackWhenFileAlreadyExists() throws DataException, ZipException {

		String inputFilename = "zipFilename.ext";
		String archiveFilename = "zipFilename.zip";
		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(inputFilename)).andReturn(inputFile);

		File archiveFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(archiveFilename)).andReturn(archiveFile);

		EasyMock.expect(archiveFile.exists()).andReturn(true);
		archiveFile.deleteOnExit();
		EasyMock.expectLastCall().once();

		ZipFile zipFile = ctrl.createMock(ZipFile.class);
		EasyMock.expect(zipFileFactory.create(archiveFilename)).andReturn(zipFile);

		ZipParameters zipParameters = ctrl.createMock(ZipParameters.class);
		EasyMock.expect(zipFileFactory.createZipParameters()).andReturn(zipParameters);
		zipParameters.setPassword("password");

		EasyMock.expectLastCall().once();
		zipFile.addFile(inputFile, zipParameters);
		EasyMock.expectLastCall().once();

		support.replayAll();

		zipFilesDAO.pack(inputFilename, archiveFilename, "password");

		support.verifyAll();
	}

	@Test
	public void testPackWhenFileDoesNotExist() throws DataException, ZipException {

		String inputFilename = "zipFilename.ext";
		String archiveFilename = "zipFilename.zip";
		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(inputFilename)).andReturn(inputFile);

		File archiveFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(archiveFilename)).andReturn(archiveFile);

		EasyMock.expect(archiveFile.exists()).andReturn(false);
		File parentFile = ctrl.createMock(File.class);
		EasyMock.expect(archiveFile.getParentFile()).andReturn(parentFile);
		EasyMock.expect(parentFile.mkdirs()).andReturn(true);

		ZipFile zipFile = ctrl.createMock(ZipFile.class);
		EasyMock.expect(zipFileFactory.create(archiveFilename)).andReturn(zipFile);

		ZipParameters zipParameters = ctrl.createMock(ZipParameters.class);
		EasyMock.expect(zipFileFactory.createZipParameters()).andReturn(zipParameters);
		zipParameters.setPassword("password");
		EasyMock.expectLastCall().once();
		zipFile.addFile(inputFile, zipParameters);
		EasyMock.expectLastCall().once();

		support.replayAll();

		zipFilesDAO.pack(inputFilename, archiveFilename, "password");

		support.verifyAll();
	}

	@Test
	public void testPackWhenZipExpectionIsRaise() throws DataException, ZipException {

		String inputFilename = "zipFilename.ext";
		String archiveFilename = "zipFilename.zip";
		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(inputFilename)).andReturn(inputFile);

		File archiveFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(archiveFilename)).andReturn(archiveFile);

		EasyMock.expect(archiveFile.exists()).andReturn(false);
		File parentFile = ctrl.createMock(File.class);
		EasyMock.expect(archiveFile.getParentFile()).andReturn(parentFile);
		EasyMock.expect(parentFile.mkdirs()).andReturn(true);

		zipFileFactory.create(archiveFilename);
		ZipException zipException = ctrl.createMock(ZipException.class);
		EasyMock.expectLastCall().andThrow(zipException);

		support.replayAll();
		try {
			zipFilesDAO.pack(inputFilename, archiveFilename, "password");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.pack.failed", aExp.getMessage());
			assertSame(zipException, aExp.getCause());
		}
		support.verifyAll();
	}

	@Test
	public void testGetNameFromArchive() {
		assertEquals("test", zipFilesDAO.getNameFromArchiveFilename("/a/b/export-test.arc"));
	}
	
	@Test
	public void testIsValidArchiveFilename() {
		assertFalse(zipFilesDAO.isValidArchiveFilename("/a/b/test.zip"));
		assertFalse(zipFilesDAO.isValidArchiveFilename("/a/b/test.arc"));
		assertFalse(zipFilesDAO.isValidArchiveFilename("/a/b/export-test.zip"));
		assertTrue(zipFilesDAO.isValidArchiveFilename("/a/b/export-test.arc"));
	}
	
	@Test
	public void testGetExportFilenameWildcardMatcher() {
		assertEquals("export-*.arc",zipFilesDAO.getExportFilenameWildcardMatcher());
	}
	
	@Test
	public void testExistsArchiveFile() {
		
		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create("/a/exported.zip"))
				.andReturn(inputFile);
		EasyMock.expect(inputFile.exists()).andReturn(true);
		support.replayAll();

		assertTrue(zipFilesDAO.existsArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testIsValidArchiveFile() throws DataException, ZipException {
		ZipFile inputFile = ctrl.createMock(ZipFile.class);

		EasyMock.expect(zipFileFactory.create("/a/exported.zip"))
				.andReturn(inputFile);
		EasyMock.expect(inputFile.isValidZipFile()).andReturn(true);
		support.replayAll();
		assertTrue(zipFilesDAO.isValidArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testIsValidArchiveFileWhenExceptionShouldReturnFalse() throws DataException, ZipException {
		
		zipFileFactory.create("/a/exported.zip");
		EasyMock.expectLastCall().andThrow(new ZipException());
		support.replayAll();
		assertFalse(zipFilesDAO.isValidArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testIsEncryptedArchiveFile() throws ZipException, DataException {
		ZipFile inputFile = ctrl.createMock(ZipFile.class);

		EasyMock.expect(zipFileFactory.create("/a/exported.zip"))
				.andReturn(inputFile);
		EasyMock.expect(inputFile.isEncrypted()).andReturn(true);
		support.replayAll();
		assertTrue(zipFilesDAO.isEncryptedArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}

	@Test
	public void testIsEncryptedArchiveFileWhenExceptionShouldReturnFalse() throws ZipException, DataException {
		zipFileFactory.create("/a/exported.zip");
		EasyMock.expectLastCall().andThrow(new ZipException());
		support.replayAll();
		assertFalse(zipFilesDAO.isEncryptedArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}

}
