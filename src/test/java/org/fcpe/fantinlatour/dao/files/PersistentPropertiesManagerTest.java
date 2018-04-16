package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DefaultPropertiesPersister;

public class PersistentPropertiesManagerTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private FileFactory fileFactory;
	private DefaultPropertiesPersister propertiesPersister;
	private PropertiesFactory propertiesFactory;
	private PersistentPropertiesManager persistentPropertiesManager;

	@Before
	public void setup() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		propertiesPersister = ctrl.createMock(DefaultPropertiesPersister.class);
		propertiesFactory = ctrl.createMock(PropertiesFactory.class);

		persistentPropertiesManager = new PersistentPropertiesManager(fileFactory, propertiesPersister,
				propertiesFactory);

	}

	@Test
	public void testLoadWhenCreateIntputStreamReturnNullShouldReturnNull() throws DataException {
		File file = ctrl.createMock(File.class);
		DataException dataException = ctrl.createMock(DataException.class);
		Throwable cause = ctrl.createMock(Throwable.class);
		fileFactory.createInputStream(file);

		EasyMock.expectLastCall().andThrow(dataException);

		EasyMock.expect(dataException.getMessage()).andReturn("Error");
		EasyMock.expect(dataException.getCause()).andReturn(cause);

		support.replayAll();

		try {
			persistentPropertiesManager.load(file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("Error", aExp.getMessage());
			assertSame(cause, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testStoreWhenCreateOutputStreamReturnNullShouldReturnFalse() throws DataException {
		File file = ctrl.createMock(File.class);
		Properties properties = ctrl.createMock(Properties.class);
		String header = "Test";
		DataException dataException = ctrl.createMock(DataException.class);
		Throwable cause = ctrl.createMock(Throwable.class);

		fileFactory.createOutputStream(file);

		EasyMock.expectLastCall().andThrow(dataException);
		EasyMock.expect(dataException.getMessage()).andReturn("Error");
		EasyMock.expect(dataException.getCause()).andReturn(cause);

		support.replayAll();

		try {
			persistentPropertiesManager.store(file, properties, header);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("Error", aExp.getMessage());
			assertSame(cause, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testLoadWhenCreateInputStreamReturnNotNullShouldReturnFalseIfPersistentPropertiesManagerRaiseException()
			throws IOException, DataException {
		File file = ctrl.createMock(File.class);
		Properties properties = ctrl.createMock(Properties.class);

		FileInputStream fileInputStream = ctrl.createMock(FileInputStream.class);

		EasyMock.expect(fileFactory.createInputStream(file)).andReturn(fileInputStream);

		EasyMock.expect(propertiesFactory.create()).andReturn(properties);

		IOException ioException = ctrl.createMock(IOException.class);

		propertiesPersister.load(properties, fileInputStream);
		EasyMock.expectLastCall().andThrow(ioException);
		fileFactory.close(fileInputStream);
		EasyMock.expectLastCall().once();

		support.replayAll();

		try {
			persistentPropertiesManager.load(file);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.persistentPropertiesManager.load.fileNotFoundException",
					aExp.getMessage());

			assertSame(ioException, aExp.getCause());
		}

		support.verifyAll();
	}

	@Test
	public void testStoreWhenCreateOutputStreamReturnNotNullShouldReturnFalseIfPersistentPropertiesManagerRaiseException()
			throws IOException, DataException {
		File file = ctrl.createMock(File.class);
		Properties properties = ctrl.createMock(Properties.class);
		String header = "Test";
		FileOutputStream fileOutputStream = ctrl.createMock(FileOutputStream.class);

		EasyMock.expect(fileFactory.createOutputStream(file)).andReturn(fileOutputStream);

		IOException ioException = ctrl.createMock(IOException.class);

		propertiesPersister.store(properties, fileOutputStream, header);
		EasyMock.expectLastCall().andThrow(ioException);
		fileFactory.close(fileOutputStream);
		EasyMock.expectLastCall().once();

		support.replayAll();
		try {
			persistentPropertiesManager.store(file, properties, header);
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.persistentPropertiesManager.store.ioException",
					aExp.getMessage());

			assertSame(ioException, aExp.getCause());
		}
		support.verifyAll();
	}

	@Test
	public void testStoreWhenCreateOutputStreamReturnNotNullShouldReturnTrueIfPersistentPropertiesManagerDoesNotRaiseException()
			throws IOException, DataException {
		File file = ctrl.createMock(File.class);
		Properties properties = ctrl.createMock(Properties.class);
		String header = "Test";
		FileOutputStream fileOutputStream = ctrl.createMock(FileOutputStream.class);

		EasyMock.expect(fileFactory.createOutputStream(file)).andReturn(fileOutputStream);

		propertiesPersister.store(properties, fileOutputStream, header);
		fileFactory.close(fileOutputStream);

		EasyMock.expectLastCall().once();

		support.replayAll();

		assertTrue(persistentPropertiesManager.store(file, properties, header));

		support.verifyAll();
	}

	@Test
	public void testCreate() {

		Properties properties = ctrl.createMock(Properties.class);

		EasyMock.expect(propertiesFactory.create()).andReturn(properties);

		support.replayAll();

		assertSame(properties, persistentPropertiesManager.create());

		support.verifyAll();
	}

	@Test
	public void testLoadWhenCreateInputStreamReturnNotNullShouldReturnPropertiesIfPersistentPropertiesManagerDoesNotRaiseException()
			throws IOException, DataException {
		File file = ctrl.createMock(File.class);
		Properties properties = ctrl.createMock(Properties.class);

		FileInputStream fileInputStream = ctrl.createMock(FileInputStream.class);

		EasyMock.expect(fileFactory.createInputStream(file)).andReturn(fileInputStream);

		EasyMock.expect(propertiesFactory.create()).andReturn(properties);

		propertiesPersister.load(properties, fileInputStream);

		fileFactory.close(fileInputStream);
		EasyMock.expectLastCall().once();

		support.replayAll();

		assertSame(properties, persistentPropertiesManager.load(file));

		support.verifyAll();
	}
}
