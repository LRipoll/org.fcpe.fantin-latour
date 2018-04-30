package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.zeroturnaround.zip.ZipUtil;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ZipUtil.class })
public class ZipFilesDAOTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private ZipFilesDAO zipFilesDAO;
	private FileFactory fileFactory;
	private String dirname;

	@Before
	public void setUp() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		PowerMock.mockStatic(ZipUtil.class);
		dirname = "dirname";
		zipFilesDAO = new ZipFilesDAO(fileFactory, dirname);
	}

	@Test
	public void testGetZipFileName() throws DataException {
		support.replayAll();

		assertEquals(dirname+File.separator+"test.zip", zipFilesDAO.getZipFileName("test"));

		support.verifyAll();
	}
	@Test
	public void testPackWhenFileAlreadyExists() throws DataException {

		String zipFilename = "zipFilename.ext";
		String zippedFilename = "zipFilename.zip";
		File filetoBeZipped = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(zipFilename)).andReturn(filetoBeZipped);

		File zippedFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(zippedFilename)).andReturn(zippedFile);

		EasyMock.expect(zippedFile.exists()).andReturn(true);
		zippedFile.deleteOnExit();
		EasyMock.expectLastCall().once();

		File[] filesToBepacked = new File[] { filetoBeZipped };
		ZipUtil.packEntries(filesToBepacked, zippedFile);
		PowerMock.expectLastCall();

		support.replayAll();

		zipFilesDAO.pack(zipFilename, zippedFilename);

		support.verifyAll();
	}

	@Test
	public void testPackWhenFileDoesNotExist() throws DataException {

		String zipFilename = "zipFilename.ext";
		String zippedFilename = "zipFilename.zip";
		File filetoBeZipped = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(zipFilename)).andReturn(filetoBeZipped);

		File zippedFile = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create(zippedFilename)).andReturn(zippedFile);

		EasyMock.expect(zippedFile.exists()).andReturn(false);
		File parentFile = ctrl.createMock(File.class);
		EasyMock.expect(zippedFile.getParentFile()).andReturn(parentFile);
		EasyMock.expect(parentFile.mkdirs()).andReturn(true);

		File[] filesToBepacked = new File[] { filetoBeZipped };
		ZipUtil.packEntries(filesToBepacked, zippedFile);
		PowerMock.expectLastCall();

		support.replayAll();

		zipFilesDAO.pack(zipFilename, zippedFilename);

		support.verifyAll();
	}

}
