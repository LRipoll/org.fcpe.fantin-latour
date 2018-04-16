package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissementFactory;
import org.fcpe.fantinlatour.model.Etablissement;
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

		EasyMock.expect(appDirManager.create()).andReturn(true);

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

		assertSame(conseilLocalEtablissement, conseilLocalEtablissementDAOImpl.load(name));

		support.verifyAll();
	}

	@Test
	public void testGetExistingConseilEtablissementsWhenAppdirDoesNotExist() throws DataException {
		EasyMock.expect(appDirManager.exists()).andReturn(false);

		support.replayAll();

		assertEquals(0, conseilLocalEtablissementDAOImpl.getExistingConseilEtablissements().size());

		support.verifyAll();
	}

	@Test
	public void testGetExistingConseilEtablissementsWhenAppdirExists() throws DataException {
		EasyMock.expect(appDirManager.exists()).andReturn(true);
		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));

		File file = ctrl.createMock(File.class);

		EasyMock.expect(fileFactory.create("userhome" + File.separator + "appRoot/Dir")).andReturn(file);

		FilenameFilter fileFilter = ctrl.createMock(FilenameFilter.class);
		EasyMock.expect(fileFactory.createExtentionFilenameFilter("ext")).andReturn(fileFilter);

		File[] files = new File[2];
		files[0] = ctrl.createMock(File.class);
		EasyMock.expect(files[0].getAbsolutePath())
				.andReturn("userhome" + File.separator + "appRoot/Dir/ConseilLocal1.ext");
		files[1] = ctrl.createMock(File.class);
		EasyMock.expect(files[1].getAbsolutePath())
				.andReturn("userhome" + File.separator + "appRoot/Dir/ConseilLocal2.ext");

		EasyMock.expect(file.listFiles(fileFilter)).andReturn(files);

		support.replayAll();

		List<String> existingConseilEtablissements = conseilLocalEtablissementDAOImpl
				.getExistingConseilEtablissements();
		assertEquals(2, existingConseilEtablissements.size());
		assertEquals("ConseilLocal1", existingConseilEtablissements.get(0));
		assertEquals("ConseilLocal2", existingConseilEtablissements.get(1));

		support.verifyAll();
	}

	@Test
	public void testRenameWhenRenameIsOK() throws DataException {

		EasyMock.expect(appDirManager.exists()).andReturn(true).anyTimes();
		EasyMock.expect(appDirManager.getAbsolutePath()).andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"))
				.anyTimes();
		File oldFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "oldName.ext"))
				.andReturn(oldFile);
		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		Etablissement etablissement = ctrl.createMock(Etablissement.class);
		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement);
		
		EasyMock.expect(xmlFileManager.load(oldFile)).andReturn(conseilLocalEtablissement);
		etablissement.setNom("newName");
		EasyMock.expect(xmlFileManager.store(conseilLocalEtablissement, oldFile)).andReturn(true);
		File newFile = ctrl.createMock(File.class);

	
		
		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "newName.ext"))
				.andReturn(newFile);

		EasyMock.expect(oldFile.renameTo(newFile)).andReturn(true);

		support.replayAll();

		conseilLocalEtablissementDAOImpl.rename("oldName", "newName");

		support.verifyAll();
	}

	@Test
	public void testRenameWhenRenameOperationFailed() throws DataException {

		EasyMock.expect(appDirManager.exists()).andReturn(true).anyTimes();
		EasyMock.expect(appDirManager.getAbsolutePath()).andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"))
				.anyTimes();
		File oldFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "oldName.ext"))
				.andReturn(oldFile);

	
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		Etablissement etablissement = ctrl.createMock(Etablissement.class);
		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(xmlFileManager.load(oldFile)).andReturn(conseilLocalEtablissement);
		etablissement.setNom("newName");
		EasyMock.expectLastCall();
		EasyMock.expect(xmlFileManager.store(conseilLocalEtablissement, oldFile)).andReturn(true);
		
		etablissement.setNom("oldName");
		EasyMock.expectLastCall();
		
		File newFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "newName.ext"))
				.andReturn(newFile);

		EasyMock.expect(oldFile.renameTo(newFile)).andReturn(false);

		support.replayAll();

		try {
			conseilLocalEtablissementDAOImpl.rename("oldName", "newName");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.ConseilLocalEtablissementDAOImpl.rename.failed",
					aExp.getMessage());

		}

		support.verifyAll();
	}
	
	@Test
	public void testRenameWhenStoreOperationFailed() throws DataException {

		EasyMock.expect(appDirManager.exists()).andReturn(true).anyTimes();
		EasyMock.expect(appDirManager.getAbsolutePath()).andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"))
				.anyTimes();
		File oldFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "oldName.ext"))
				.andReturn(oldFile);

	
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		Etablissement etablissement = ctrl.createMock(Etablissement.class);
		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(xmlFileManager.load(oldFile)).andReturn(conseilLocalEtablissement);
		etablissement.setNom("newName");
		EasyMock.expectLastCall();
		EasyMock.expect(xmlFileManager.store(conseilLocalEtablissement, oldFile)).andReturn(false);
		
		etablissement.setNom("oldName");
		EasyMock.expectLastCall();
		

		support.replayAll();

		try {
			conseilLocalEtablissementDAOImpl.rename("oldName", "newName");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.ConseilLocalEtablissementDAOImpl.rename.failed",
					aExp.getMessage());

		}

		support.verifyAll();
	}

	@Test
	public void testDeleteWhenDeleteIsOK() throws DataException {

		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		;
		File oldFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "oldName.ext"))
				.andReturn(oldFile);

		EasyMock.expect(oldFile.delete()).andReturn(true);

		support.replayAll();

		conseilLocalEtablissementDAOImpl.delete("oldName");

		support.verifyAll();
	}

	@Test
	public void testDeleteWhenDeleteOperationFailed() {

		EasyMock.expect(appDirManager.getAbsolutePath())
				.andReturn(FileUtils.getAbsolutePath("userhome", "appRoot/Dir"));
		
		File oldFile = ctrl.createMock(File.class);

		EasyMock.expect(
				fileFactory.create("userhome" + File.separator + "appRoot/Dir" + File.separator + "oldName.ext"))
				.andReturn(oldFile);

		EasyMock.expect(oldFile.delete()).andReturn(false);

		support.replayAll();

		try {
			conseilLocalEtablissementDAOImpl.delete("oldName");
			fail("Should throw DataException");
		} catch (DataException aExp) {
			assertEquals("org.fcpe.fantinlatour.dao.files.ConseilLocalEtablissementDAOImpl.delete.failed",
					aExp.getMessage());

		}

		support.verifyAll();
	}

}
