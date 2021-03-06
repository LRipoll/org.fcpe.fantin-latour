package org.fcpe.fantinlatour.model.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.ConseilLocalEtablissementDAO;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.UserPreferencesDAO;
import org.fcpe.fantinlatour.dao.files.ZipFilesDAO;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.Etablissement;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.junit.Before;
import org.junit.Test;

public class ConseilLocalEtablissementManagerTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private ConseilLocalEtablissementDAO conseilLocalEtablissementDAO;
	private UserPreferencesDAO userPreferencesDAO;
	private ZipFilesDAO zipFilesDAO;
	private Desktop desktop;
	private ConseilLocalEtablissementManagerListener conseilLocalEtablissementManagerListener;
	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@Before
	public void setup() {

		ctrl = support.createControl();
		conseilLocalEtablissementDAO = ctrl.createMock(ConseilLocalEtablissementDAO.class);
		userPreferencesDAO = ctrl.createMock(UserPreferencesDAO.class);
		zipFilesDAO = ctrl.createMock(ZipFilesDAO.class);
		desktop = ctrl.createMock(Desktop.class);
		conseilLocalEtablissementManagerListener = ctrl.createMock(ConseilLocalEtablissementManagerListener.class);
		conseilLocalEtablissementManager = new ConseilLocalEtablissementManager(conseilLocalEtablissementDAO,
				userPreferencesDAO, zipFilesDAO, desktop);

		conseilLocalEtablissementManager.addListener(conseilLocalEtablissementManagerListener);

	}

	@Test
	public void testGetExistingConseilEtablissements() throws DataException {

		List<String> list = new ArrayList<String>();
		EasyMock.expect(conseilLocalEtablissementDAO.getExistingConseilEtablissements()).andReturn(list);

		support.replayAll();

		assertSame(list, conseilLocalEtablissementManager.getExistingConseilEtablissements());

		support.verifyAll();
	}

	@Test
	public void testExistsFromArchiveFilename() {

		String name = "existname";
		EasyMock.expect(conseilLocalEtablissementDAO.exists(name)).andReturn(true);
		EasyMock.expect(zipFilesDAO.getNameFromArchiveFilename("/a/existname.zip")).andReturn(name);

		support.replayAll();

		assertTrue(conseilLocalEtablissementManager.existsFromArchiveFilename("/a/existname.zip"));

		support.verifyAll();
	}
	
	@Test
	public void testGetArchiveDirname() {
		EasyMock.expect(zipFilesDAO.getZipDirname()).andReturn("/a/zipdir");
		
		support.replayAll();

		assertEquals("/a/zipdir",conseilLocalEtablissementManager.getArchiveDirname());

		support.verifyAll();

	}

	@Test
	public void testIsValidFromArchiveFilename() {

		String name = "existname";
		EasyMock.expect(conseilLocalEtablissementDAO.isValidName(name)).andReturn(true);
		EasyMock.expect(zipFilesDAO.getNameFromArchiveFilename("/a/existname.zip")).andReturn(name);

		support.replayAll();

		assertTrue(conseilLocalEtablissementManager.isValidFromArchiveFilename("/a/existname.zip"));

		support.verifyAll();
	}

	@Test
	public void testWhenNameExistShouldReturnTrue() {

		String name = "existname";
		EasyMock.expect(conseilLocalEtablissementDAO.exists(name)).andReturn(true);

		support.replayAll();

		assertTrue(conseilLocalEtablissementManager.exists(name));

		support.verifyAll();
	}

	@Test
	public void testWhenNameIsValidShouldReturnTrue() {

		String name = "existname";
		EasyMock.expect(conseilLocalEtablissementDAO.isValidName(name)).andReturn(true);

		support.replayAll();

		assertTrue(conseilLocalEtablissementManager.isValidName(name));

		support.verifyAll();
	}

	@Test
	public void testGetDefault() throws DataException {

		String defaultValue = "Default";
		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn(defaultValue);

		support.replayAll();

		assertSame(defaultValue, conseilLocalEtablissementManager.getDefault());

		support.verifyAll();
	}

	@Test
	public void testCreateADefaultConseilLocalTheUserPreferencesMustBeStore() throws DataException {

		String name = "existname";
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		EasyMock.expect(conseilLocalEtablissementDAO.create(name, TypeEtablissement.ELEMENTAIRE))
				.andReturn(conseilLocalEtablissement);

		userPreferencesDAO.setDefaultConseilLocalName(name);
		EasyMock.expectLastCall().once();

		EasyMock.expect(userPreferencesDAO.store()).andReturn(true);

		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		support.replayAll();

		assertSame(conseilLocalEtablissement,
				conseilLocalEtablissementManager.create(name, TypeEtablissement.ELEMENTAIRE, true));
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testCreateNotAsDefaultConseilLocalTheUserPreferencesMustBeStore() throws DataException {

		String name = "existname";
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		EasyMock.expect(conseilLocalEtablissementDAO.create(name, TypeEtablissement.ELEMENTAIRE))
				.andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		support.replayAll();

		assertSame(conseilLocalEtablissement,
				conseilLocalEtablissementManager.create(name, TypeEtablissement.ELEMENTAIRE, false));

		support.verifyAll();
	}

	@Test
	public void testInitWhenNoDefaultDefinesShouldReturnNull() throws DataException {

		conseilLocalEtablissementManagerListener.onSelected(null);
		EasyMock.expectLastCall().once();
		support.replayAll();
		conseilLocalEtablissementManager.init();
		assertNull(conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());

		support.verifyAll();
	}

	@Test
	public void testOpenShouldReturnIt() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testRenameWhenItisNotTheDefault() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("Default");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		EasyMock.expect(conseilLocalEtablissementDAO.rename("opened", "newName")).andReturn(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.rename("newName");
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testDeleteWhenItisNotTheDefault() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("Default");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		conseilLocalEtablissementDAO.delete("opened");
		EasyMock.expectLastCall().once();

		conseilLocalEtablissementManagerListener.onSelected(null);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.delete();
		assertNull(conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testDeleteWhenItisTheDefault() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("opened");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		conseilLocalEtablissementDAO.delete("opened");
		EasyMock.expectLastCall().once();

		userPreferencesDAO.setDefaultConseilLocalName(null);
		EasyMock.expectLastCall().once();
		EasyMock.expect(userPreferencesDAO.store()).andReturn(true);
		conseilLocalEtablissementManagerListener.onSelected(null);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.delete();
		assertNull(conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testSetDefault() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		userPreferencesDAO.setDefaultConseilLocalName("opened");
		EasyMock.expectLastCall().once();
		EasyMock.expect(userPreferencesDAO.store()).andReturn(true);

		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.setAsDefault();
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testStore() throws DataException {

		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		
		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		
		conseilLocalEtablissementDAO.store(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		
		support.replayAll();
		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.store();
		support.verifyAll();
	}	
	@Test
	public void testRenameWhenItisTheDefault() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("opened");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		EasyMock.expect(conseilLocalEtablissementDAO.rename("opened", "newName")).andReturn(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		userPreferencesDAO.setDefaultConseilLocalName("newName");
		EasyMock.expectLastCall().once();

		EasyMock.expect(userPreferencesDAO.store()).andReturn(true);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.open("opened");
		conseilLocalEtablissementManager.rename("newName");
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}

	@Test
	public void testisDefaultWhenNoSelection() throws DataException {

		support.replayAll();
		assertFalse(conseilLocalEtablissementManager.isDefault());
		support.verifyAll();
	}

	@Test
	public void testisDefaultWhenIsNotTheOpened() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("Default");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();
		conseilLocalEtablissementManager.open("opened");
		assertFalse(conseilLocalEtablissementManager.isDefault());
		support.verifyAll();
	}

	@Test
	public void testisDefaultWhenIsTheOpened() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("opened");

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("opened");

		EasyMock.expect(conseilLocalEtablissementDAO.load("opened")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();
		conseilLocalEtablissementManager.open("opened");
		assertTrue(conseilLocalEtablissementManager.isDefault());
		support.verifyAll();
	}

	@Test
	public void testExportAsZip() throws DataException, IOException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("exported").anyTimes();

		EasyMock.expect(conseilLocalEtablissementDAO.load("exported")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		EasyMock.expect(conseilLocalEtablissementDAO.getAbsoluteArchiveFilename("exported")).andReturn("zippedFilename");
		File zipFile = ctrl.createMock(File.class);

		String zipFilename = "dir/test.zip";
		EasyMock.expect(zipFilesDAO.getExportZipAbsoluteFilename("exported")).andReturn(zipFilename);

		EasyMock.expect(zipFilesDAO.pack("zippedFilename", zipFilename,"password")).andReturn(zipFile);

		desktop.open(zipFile);
		EasyMock.expectLastCall().once();
		support.replayAll();
		conseilLocalEtablissementManager.open("exported");
		conseilLocalEtablissementManager.exportArchive("password");
		support.verifyAll();
	}
	
	@Test
	public void testExportedArchiveAlreadyExistsShouldReturnFalseWhenNoOneisOpened() {
		support.replayAll();
		assertFalse(conseilLocalEtablissementManager.exportedArchiveAlreadyExists());
		support.verifyAll();

	}
	
	
	@Test
	public void testExportedArchiveAlreadyExistsShouldReturnTrueAsNecessary() throws DataException {
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("exported").anyTimes();

		EasyMock.expect(conseilLocalEtablissementDAO.load("exported")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		
		EasyMock.expect(zipFilesDAO.exportZipFilenameAlreadyExists("exported")).andReturn(true);
		
		support.replayAll();
		conseilLocalEtablissementManager.open("exported");
		assertTrue(conseilLocalEtablissementManager.exportedArchiveAlreadyExists());
		support.verifyAll();

	}

	@Test
	public void testGetExportedArchiveFilenameShouldReturnNullIfNoOneIsOpened() {
		support.replayAll();
		assertNull(conseilLocalEtablissementManager.getExportedArchiveFilename());
		support.verifyAll();

	}

	@Test
	public void testGetExportedArchiveFilenameShouldReturnZipDAO() throws DataException {
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement).anyTimes();
		EasyMock.expect(etablissement.getNom()).andReturn("exported").anyTimes();

		EasyMock.expect(conseilLocalEtablissementDAO.load("exported")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
		
		EasyMock.expect(zipFilesDAO.getExportZipAbsoluteFilename("exported")).andReturn("/a/exported.zip");
		
		support.replayAll();
		conseilLocalEtablissementManager.open("exported");
		assertEquals("/a/exported.zip",conseilLocalEtablissementManager.getExportedArchiveFilename());
		support.verifyAll();

	}
	
	@Test
	public void testIsValidArchiveFilename() {
		EasyMock.expect(zipFilesDAO.isValidArchiveFilename("/a/exported.zip")).andReturn(true);
		support.replayAll();
		assertTrue(conseilLocalEtablissementManager.isValidArchiveFilename("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testExistsArchiveFile() {
		EasyMock.expect(zipFilesDAO.existsArchiveFile("/a/exported.zip")).andReturn(true);
		support.replayAll();
		assertTrue(conseilLocalEtablissementManager.existsArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testIsValidArchiveFile() throws DataException {
		EasyMock.expect(zipFilesDAO.isValidArchiveFile("/a/exported.zip")).andReturn(true);
		support.replayAll();
		assertTrue(conseilLocalEtablissementManager.isValidArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testIsEncryptedArchiveFile() throws DataException {
		EasyMock.expect(zipFilesDAO.isEncryptedArchiveFile("/a/exported.zip")).andReturn(true);
		support.replayAll();
		assertTrue(conseilLocalEtablissementManager.isEncryptedArchiveFile("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testContainsExpectedArchives() {
		EasyMock.expect(zipFilesDAO.getNameFromArchiveFilename("/a/exported.zip")).andReturn("exported");
		EasyMock.expect(conseilLocalEtablissementDAO.getArchiveFilename("exported")).andReturn("file.ext");
		EasyMock.expect(zipFilesDAO.containsExpectedArchives("/a/exported.zip","file.ext")).andReturn(true);
		support.replayAll();
		assertTrue(conseilLocalEtablissementManager.containsExpectedArchives("/a/exported.zip"));
		support.verifyAll();

	}
	
	@Test
	public void testGetArchiveFilenameWildcardMatcher() {
		EasyMock.expect(zipFilesDAO.getZipFilenameWildcardMatcher()).andReturn("Wildcard");
		support.replayAll();
		assertEquals("Wildcard", conseilLocalEtablissementManager.getArchiveFilenameWildcardMatcher());
		support.verifyAll();
	
	}
	
	@Test
	public void testImportArchiveWhenIsNotDefault() throws DataException {
		EasyMock.expect(zipFilesDAO.getNameFromArchiveFilename("/a/exported.zip")).andReturn("exported");
		EasyMock.expect(conseilLocalEtablissementDAO.getArchiveFilename("exported")).andReturn("file.ext");
		EasyMock.expect(conseilLocalEtablissementDAO.getAbsoluteArchiveDirname()).andReturn("/app/dir");
		zipFilesDAO.unpack("/a/exported.zip", "password", "file.ext",
				"/app/dir");
		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		EasyMock.expect(conseilLocalEtablissementDAO.load("exported")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
	
		support.replayAll();
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.importArchive("/a/exported.zip", false, "password"));
		support.verifyAll();
		
	}
	
	@Test
	public void testImportArchiveWhenIsDefault() throws DataException {
		EasyMock.expect(zipFilesDAO.getNameFromArchiveFilename("/a/exported.zip")).andReturn("exported");
		EasyMock.expect(conseilLocalEtablissementDAO.getArchiveFilename("exported")).andReturn("file.ext");
		EasyMock.expect(conseilLocalEtablissementDAO.getAbsoluteArchiveDirname()).andReturn("/app/dir");
		zipFilesDAO.unpack("/a/exported.zip", "password", "file.ext",
				"/app/dir");
		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		EasyMock.expect(conseilLocalEtablissementDAO.load("exported")).andReturn(conseilLocalEtablissement);

		userPreferencesDAO.setDefaultConseilLocalName("exported");
		EasyMock.expectLastCall().once();
		EasyMock.expect(userPreferencesDAO.store()).andReturn(true);
		
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();
	
		support.replayAll();
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.importArchive("/a/exported.zip", true, "password"));
		support.verifyAll();
		
	}

}
