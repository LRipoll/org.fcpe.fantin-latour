package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
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

	@Before
	public void setUp() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		zipFileFactory = ctrl.createMock(ZipFileFactory.class);
		exportDirname = "exportDirname";
		importDirname = "importDirname";
		zipFilesDAO = new ZipFilesDAO(fileFactory, zipFileFactory,exportDirname, importDirname);
	}

	@Test
	public void testGetZipFileName() throws DataException {
		support.replayAll();

		assertEquals(exportDirname + File.separator + "test.zip", zipFilesDAO.getExportZipFilename("test"));

		support.verifyAll();
	}

	@Test
	public void testExportZipFilenameAlreadyExists() throws DataException {

		File inputFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(exportDirname + File.separator + "test.zip")).andReturn(inputFile);

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
		assertEquals("test",zipFilesDAO.getNameFromArchiveFilename("/a/b/test.zip"));
	}

}
