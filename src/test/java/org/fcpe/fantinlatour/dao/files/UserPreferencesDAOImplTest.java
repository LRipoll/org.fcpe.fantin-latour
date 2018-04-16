package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Properties;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;

public class UserPreferencesDAOImplTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private FileFactory fileFactory;
	private AppDirManager appDirManager;
	private PersistentPropertiesManager persistentPropertiesManager;

	private UserPreferencesDAOImpl userPreferencesDAOImpl;

	@Before
	public void setup() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		appDirManager = ctrl.createMock(AppDirManager.class);
		persistentPropertiesManager = ctrl.createMock(PersistentPropertiesManager.class);
		EasyMock.expect(appDirManager.getAbsolutePath()).andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"))
				.anyTimes();

		userPreferencesDAOImpl = new UserPreferencesDAOImpl(appDirManager, "default.properties", fileFactory,
				persistentPropertiesManager);

	}

	@Test
	public void testExistsWhenFileExistShouldReturnTrue() {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(true);

		support.replayAll();

		assertTrue(userPreferencesDAOImpl.exists());

		support.verifyAll();
	}

	@Test
	public void testSetPropertyWhenFileDoestExistShouldReturnDoIt() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(false).anyTimes();
		Properties properties = new Properties();
		EasyMock.expect(persistentPropertiesManager.create()).andReturn(properties);
		support.replayAll();

		assertFalse(userPreferencesDAOImpl.exists());
		userPreferencesDAOImpl.setProperty("key", "value");
		assertEquals("value", userPreferencesDAOImpl.getProperty("key"));

		support.verifyAll();
	}

	@Test
	public void testSetPropertyWhenFileExistShouldReturnDoIt() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();

		Properties properties = new Properties();

		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);

		support.replayAll();

		assertTrue(userPreferencesDAOImpl.exists());
		userPreferencesDAOImpl.setProperty("key", "value");
		assertEquals("value", userPreferencesDAOImpl.getProperty("key"));

		support.verifyAll();
	}

	@Test
	public void testSetPropertyAfterLoadingExistingFileShouldReturnDoIt() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		Properties properties = new Properties();
		properties.setProperty("not", "empty");
		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);
		support.replayAll();

		assertTrue(userPreferencesDAOImpl.exists());
		assertTrue(userPreferencesDAOImpl.load());
		userPreferencesDAOImpl.setProperty("key", "value");
		assertEquals("value", userPreferencesDAOImpl.getProperty("key"));

		support.verifyAll();
	}

	@Test
	public void testGetPropertyWhenFileExistShouldReturnDoIt() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		Properties properties = ctrl.createMock(Properties.class);
		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);
		EasyMock.expect(properties.isEmpty()).andReturn(false);
		EasyMock.expect(properties.getProperty("key")).andReturn("value");
		support.replayAll();

		assertEquals("value", userPreferencesDAOImpl.getProperty("key"));

		support.verifyAll();
	}

	@Test
	public void testGetPropertyWhenFileExistButNotThePropertyShouldReturnNull() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		Properties properties = ctrl.createMock(Properties.class);
		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);
		EasyMock.expect(properties.isEmpty()).andReturn(true);

		support.replayAll();

		assertNull(userPreferencesDAOImpl.getProperty("key"));

		support.verifyAll();
	}

	@Test
	public void testLoadWhenFileDoesNotExistShouldReturnFalse() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(false);

		support.replayAll();

		assertFalse(userPreferencesDAOImpl.load());

		support.verifyAll();
	}

	@Test
	public void testIsLoadWhenReturnFalse() {

		support.replayAll();
		assertFalse(userPreferencesDAOImpl.isLoad());

		support.verifyAll();
	}

	@Test
	public void testIsLoadWhenFileDoesNotExistsReturnTrue() {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(false);

		support.replayAll();
		assertFalse(userPreferencesDAOImpl.exists());
		assertTrue(userPreferencesDAOImpl.isLoad());

		support.verifyAll();
	}

	@Test
	public void testIsLoadWhenFileExistsReturnTrueOnlyAfterLoad() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		Properties properties = ctrl.createMock(Properties.class);
		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);
		EasyMock.expect(properties.isEmpty()).andReturn(true);

		support.replayAll();
		assertTrue(userPreferencesDAOImpl.exists());
		assertFalse(userPreferencesDAOImpl.isLoad());

		userPreferencesDAOImpl.load();

		assertTrue(userPreferencesDAOImpl.isLoad());

		support.verifyAll();
	}

	@Test
	public void testStoreWhenDirCouldNotBeCreatedShouldReturnFalse() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(false);
		EasyMock.expect(appDirManager.create()).andReturn(false);

		support.replayAll();

		assertFalse(userPreferencesDAOImpl.store());

		support.verifyAll();
	}

	@Test
	public void testStoreWhenDirCouldBeCreatedShouldReturnPersistentPropertiesManagerResult() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(true).anyTimes();
		Properties properties = ctrl.createMock(Properties.class);
		EasyMock.expect(persistentPropertiesManager.load(file)).andReturn(properties);
		EasyMock.expect(properties.isEmpty()).andReturn(true);

		EasyMock.expect(persistentPropertiesManager.store(file, properties, "default.properties")).andReturn(true);
		support.replayAll();

		userPreferencesDAOImpl.load();
		assertTrue(userPreferencesDAOImpl.store());

		support.verifyAll();
	}

	@Test
	public void testDefaultConseilLocalNameAccessors() throws DataException {

		File file = ctrl.createMock(File.class);
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "default.properties"))
				.andReturn(file).anyTimes();
		EasyMock.expect(file.exists()).andReturn(false).anyTimes();
		Properties properties = new Properties();
		EasyMock.expect(persistentPropertiesManager.create()).andReturn(properties);
		support.replayAll();

		userPreferencesDAOImpl.setDefaultConseilLocalName("default");

		assertEquals("default", userPreferencesDAOImpl.getDefaultConseilLocalName());

		support.verifyAll();
	}

}
