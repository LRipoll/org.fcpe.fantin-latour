package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
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
	private String dirname;

	@Before
	public void setUp() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		zipFileFactory = ctrl.createMock(ZipFileFactory.class);
		dirname = "dirname";
		zipFilesDAO = new ZipFilesDAO(fileFactory, zipFileFactory, dirname);
	}

	@Test
	public void testGetZipFileName() throws DataException {
		support.replayAll();

		assertEquals(dirname+File.separator+"test.zip", zipFilesDAO.getZipFileName("test"));

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
		
		zipFile.addFile(inputFile, zipParameters);
		EasyMock.expectLastCall().once();

		support.replayAll();

		zipFilesDAO.pack(inputFilename, archiveFilename);

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
		
		zipFile.addFile(inputFile, zipParameters);
		EasyMock.expectLastCall().once();

		support.replayAll();

		zipFilesDAO.pack(inputFilename, archiveFilename);
		
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

		ZipFile zipFile = ctrl.createMock(ZipFile.class);
		zipFileFactory.create(archiveFilename);
		ZipException zipException = ctrl.createMock(ZipException.class);
		EasyMock.expectLastCall().andThrow(zipException);

		support.replayAll();
		try {
			zipFilesDAO.pack(inputFilename, archiveFilename);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.ZipFilesDAO.pack.failed", aExp.getMessage());
			assertSame(zipException, aExp.getCause());
		}
		support.verifyAll();
	}

}
