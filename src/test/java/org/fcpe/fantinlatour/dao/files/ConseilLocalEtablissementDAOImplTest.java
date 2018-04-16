package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissementFactory;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.junit.Before;
import org.junit.Test;

public class ConseilLocalEtablissementDAOImplTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;
	private FileFactory fileFactory;
	private AppDirManager appDirManager;
	private ConseilLocalEtablissementDAOImpl conseilLocalEtablissementDAOImpl;
	private ConseilLocalEtablissementFactory conseilLocalEtablissementFactory;
	private XMLFileManager xmlFileManager;

	@Before
	public void setup() {
		ctrl = support.createControl();
		fileFactory = ctrl.createMock(FileFactory.class);
		appDirManager = ctrl.createMock(AppDirManager.class);
		conseilLocalEtablissementFactory = ctrl.createMock(ConseilLocalEtablissementFactory.class);
		xmlFileManager = ctrl.createMock(XMLFileManager.class);
		conseilLocalEtablissementDAOImpl = new ConseilLocalEtablissementDAOImpl(conseilLocalEtablissementFactory,
				appDirManager, "ext", fileFactory, xmlFileManager);

	}

	@Test
	public void testExistsWhenFileDoesNotExistShouldReturnFalse() {
		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		File file = ctrl.createMock(File.class);
		String fileName = "fileName";
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + fileName + ".ext"))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(false);
		support.replayAll();

		assertFalse(conseilLocalEtablissementDAOImpl.exists(fileName));

		support.verifyAll();
	}

	@Test
	public void testIsValidNameWhenNameIsNullReturnFalse() {

		String fileName = null;

		support.replayAll();

		assertFalse(conseilLocalEtablissementDAOImpl.isValidName(fileName));

		support.verifyAll();
	}

	@Test
	public void testIsValidNameWhenNameIsEmptyReturnFalse() {

		String fileName = "   ";

		support.replayAll();

		assertFalse(conseilLocalEtablissementDAOImpl.isValidName(fileName));

		support.verifyAll();
	}

	@Test
	public void testIsValidNameWhenFileExistShouldReturnFalse() {
		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		File file = ctrl.createMock(File.class);
		String fileName = "fileName";
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + fileName + ".ext"))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(true);
		support.replayAll();

		assertFalse(conseilLocalEtablissementDAOImpl.isValidName(fileName));

		support.verifyAll();
	}

	@Test
	public void testIsValidNameWhenFileDoesNotExistAndItIsNotACanonicalPathShouldReturnFalse() throws IOException {
		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		File file = ctrl.createMock(File.class);
		String fileName = "fileName";
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + fileName + ".ext"))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(false);
		EasyMock.expect(file.getCanonicalPath()).andThrow(new IOException());
		support.replayAll();

		assertFalse(conseilLocalEtablissementDAOImpl.isValidName(fileName));

		support.verifyAll();
	}

	@Test
	public void testIsValidNameWhenFileDoesNotExistAndItIsACanonicalPathShouldReturnFalse() throws IOException {
		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		File file = ctrl.createMock(File.class);
		String fileName = "fileName";
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + fileName + ".ext"))
				.andReturn(file);
		EasyMock.expect(file.exists()).andReturn(false);
		EasyMock.expect(file.getCanonicalPath()).andReturn("");
		support.replayAll();

		assertTrue(conseilLocalEtablissementDAOImpl.isValidName(fileName));

		support.verifyAll();
	}

	@Test
	public void testCreateShouldReturnFactoryCreate() throws DataException, IOException {
		String name = "test";

		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		File file = ctrl.createMock(File.class);

		EasyMock.expect(appDirManager.create())
				.andReturn(true);
		
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + name + ".ext"))
				.andReturn(file);


		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		EasyMock.expect(conseilLocalEtablissementFactory.create(name, TypeEtablissement.ELEMENTAIRE))
				.andReturn(conseilLocalEtablissement);

		EasyMock.expect(xmlFileManager.store(conseilLocalEtablissement, file)).andReturn(true);

		support.replayAll();

		assertSame(conseilLocalEtablissement,
				conseilLocalEtablissementDAOImpl.create(name, TypeEtablissement.ELEMENTAIRE));

		support.verifyAll();
	}
	
	@Test
	public void testLoad() throws DataException {
		String name = "test";
		EasyMock.expect(appDirManager.getAbsolutePath())
		.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		
		File file = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + name + ".ext"))
				.andReturn(file);
		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		
		EasyMock.expect(xmlFileManager.load(file)).andReturn(conseilLocalEtablissement);
		
		support.replayAll();

		assertSame(conseilLocalEtablissement,
				conseilLocalEtablissementDAOImpl.load(name));

		support.verifyAll();
	}

}
