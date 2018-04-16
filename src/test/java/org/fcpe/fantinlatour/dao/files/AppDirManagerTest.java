package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class AppDirManagerTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;
	private FileFactory fileFactory;
	private AppDirManager appDirManager;
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		appDirManager  = new AppDirManager("userhome", "appRoot/Dir", fileFactory);

	}
	
	@Test
	public void testExistsWhenFileExistShouldReturnTrue() {
		
		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" ))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(true);
		support.replayAll();

		assertTrue(appDirManager.exists());

		support.verifyAll();
	}
	
	@Test
	public void testCreatesWhenFileExistShouldReturnTrue() {
		
		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" ))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(true);
		support.replayAll();

		assertTrue(appDirManager.create());

		support.verifyAll();
	}
	
	@Test
	public void testCreatesWhenFileDoesNotExistShouldReturnTrue() {
		
		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" ))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(false);
		EasyMock.expect(file.mkdirs()).andReturn(true);
		support.replayAll();

		assertTrue(appDirManager.create());

		support.verifyAll();
	}

}
