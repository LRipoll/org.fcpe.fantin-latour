package org.fcpe.fantinlatour.model.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.fcpe.fantinlatour.dao.ConseilLocalEtablissementDAO;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.UserPreferencesDAO;
import org.fcpe.fantinlatour.dao.files.ZipFilesDAO;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.fcpe.fantinlatour.service.SpringFactory;

public class ConseilLocalEtablissementManager implements UniqueNameManager {

	public static final String ID = "conseilLocalEtablissementManager";

	private ConseilLocalEtablissement currentConseilLocalEtablissement;
	private ConseilLocalEtablissementDAO conseilLocalEtablissementDAO;
	private UserPreferencesDAO userPreferencesDAO;
	private ZipFilesDAO zipFilesDAO;
	private Desktop desktop;
	private List<ConseilLocalEtablissementManagerListener> listeners;

	public ConseilLocalEtablissementManager(ConseilLocalEtablissementDAO conseilLocalEtablissementDAO,
			UserPreferencesDAO userPreferencesDAO, ZipFilesDAO zipFilesDAO) {
		this(conseilLocalEtablissementDAO, userPreferencesDAO, zipFilesDAO, Desktop.getDesktop());
	}

	public ConseilLocalEtablissementManager(ConseilLocalEtablissementDAO conseilLocalEtablissementDAO,
			UserPreferencesDAO userPreferencesDAO, ZipFilesDAO zipFilesDAO, Desktop desktop) {
		this.conseilLocalEtablissementDAO = conseilLocalEtablissementDAO;
		this.userPreferencesDAO = userPreferencesDAO;
		this.zipFilesDAO = zipFilesDAO;
		this.desktop = desktop;
		listeners = new ArrayList<ConseilLocalEtablissementManagerListener>();
	}

	public void addListener(ConseilLocalEtablissementManagerListener conseilLocalEtablissementManagerListener) {
		listeners.add(conseilLocalEtablissementManagerListener);
	}

	public ConseilLocalEtablissement getCurrentConseilLocalEtablissement() {
		return currentConseilLocalEtablissement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fcpe.fantinlatour.model.controller.UniqueNameManager#exists(java.lang.
	 * String)
	 */
	@Override
	public boolean exists(String name) {
		return conseilLocalEtablissementDAO.exists(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fcpe.fantinlatour.model.controller.UniqueNameManager#isValidName(java.
	 * lang.String)
	 */
	@Override
	public boolean isValidName(String name) {
		return conseilLocalEtablissementDAO.isValidName(name);
	}

	public boolean existsFromArchiveFilename(String archiveFilename) {
		String name = zipFilesDAO.getNameFromArchiveFilename(archiveFilename);
		return conseilLocalEtablissementDAO.exists(name);
	}

	public boolean isValidFromArchiveFilename(String archiveFilename) {
		String name = zipFilesDAO.getNameFromArchiveFilename(archiveFilename);
		return conseilLocalEtablissementDAO.isValidName(name);
	}

	public ConseilLocalEtablissement create(String name, TypeEtablissement typeEtablissement, boolean isDefault)
			throws DataException {

		ConseilLocalEtablissement result = conseilLocalEtablissementDAO.create(name, typeEtablissement);

		setDefaultConseilLocalName(isDefault, name);
		notifyListeners(result);

		return result;
	}

	protected void notifyListeners(ConseilLocalEtablissement result) {
		currentConseilLocalEtablissement = result;
		for (ConseilLocalEtablissementManagerListener listener : listeners) {
			listener.onSelected(currentConseilLocalEtablissement);
		}
	}

	public void init() throws DataException {

		open(null);

	}

	public List<String> getExistingConseilEtablissements() throws DataException {
		return conseilLocalEtablissementDAO.getExistingConseilEtablissements();
	}

	public void open(String conseiLocalName) throws DataException {
		ConseilLocalEtablissement result = null;
		if (conseiLocalName != null) {
			result = conseilLocalEtablissementDAO.load(conseiLocalName);
		}
		notifyListeners(result);

	}

	public void rename(String newName) throws DataException {
		String oldName = currentConseilLocalEtablissement.getEtablissement().getNom();
		currentConseilLocalEtablissement = conseilLocalEtablissementDAO.rename(oldName, newName);

		if (oldName.equals(userPreferencesDAO.getDefaultConseilLocalName())) {
			userPreferencesDAO.setDefaultConseilLocalName(newName);
			userPreferencesDAO.store();
		}
		notifyListeners(currentConseilLocalEtablissement);

	}

	public void delete() throws DataException {
		String oldName = currentConseilLocalEtablissement.getEtablissement().getNom();
		conseilLocalEtablissementDAO.delete(oldName);
		if (oldName.equals(userPreferencesDAO.getDefaultConseilLocalName())) {
			userPreferencesDAO.setDefaultConseilLocalName(null);
			userPreferencesDAO.store();
		}
		notifyListeners(null);

	}

	public boolean isDefault() throws DataException {

		return currentConseilLocalEtablissement != null && currentConseilLocalEtablissement.getEtablissement().getNom()
				.equals(userPreferencesDAO.getDefaultConseilLocalName());
	}

	public void setAsDefault() throws DataException {
		userPreferencesDAO.setDefaultConseilLocalName(currentConseilLocalEtablissement.getEtablissement().getNom());
		userPreferencesDAO.store();
		notifyListeners(currentConseilLocalEtablissement);

	}

	public String getDefault() throws DataException {

		return userPreferencesDAO.getDefaultConseilLocalName();
	}

	public boolean exportedArchiveAlreadyExists() {
		boolean result = (currentConseilLocalEtablissement != null);
		if (result) {
			String etablissement = currentConseilLocalEtablissement.getEtablissement().getNom();
			result = zipFilesDAO.exportZipFilenameAlreadyExists(etablissement);
		}

		return result;
	}

	public String getArchiveDirname() {
		return zipFilesDAO.getZipDirname();
	}

	public String getExportedArchiveFilename() {
		String result = null;
		if (currentConseilLocalEtablissement != null) {
			String etablissement = currentConseilLocalEtablissement.getEtablissement().getNom();
			result = zipFilesDAO.getExportZipAbsoluteFilename(etablissement);
		}

		return result;
	}

	public void exportArchive(String password) throws DataException {

		String etablissement = currentConseilLocalEtablissement.getEtablissement().getNom();

		String zipFilename = zipFilesDAO.getExportZipAbsoluteFilename(etablissement);

		String attachedFilename = conseilLocalEtablissementDAO.getAbsoluteArchiveFilename(etablissement);

		File zipFile = zipFilesDAO.pack(attachedFilename, zipFilename, password);
		try {
			desktop.open(zipFile);
		} catch (IOException e) {
			throw new DataException(
					SpringFactory.getMessage("org.fcpe.fantinlatour.model.controller.exportAsZip.iOException"), e);
		}

	}

	public ConseilLocalEtablissement importArchive(String archiveFilename, boolean isDefault, String password)
			throws DataException {

		ConseilLocalEtablissement result = null;

		String name = zipFilesDAO.getNameFromArchiveFilename(archiveFilename);
		String expectedFilename = conseilLocalEtablissementDAO.getArchiveFilename(name);

		zipFilesDAO.unpack(archiveFilename, password, expectedFilename,
				conseilLocalEtablissementDAO.getAbsoluteArchiveDirname());

		result = conseilLocalEtablissementDAO.load(name);

		setDefaultConseilLocalName(isDefault, name);
		notifyListeners(result);

		return result;

	}

	public boolean containsExpectedArchives(String archiveFilename) {
		String expectedFilename = getExpectedFilename(archiveFilename);
		return zipFilesDAO.containsExpectedArchives(archiveFilename, expectedFilename);
	}

	private String getExpectedFilename(String archiveFilename) {
		String name = zipFilesDAO.getNameFromArchiveFilename(archiveFilename);
		String expectedFilename = conseilLocalEtablissementDAO.getArchiveFilename(name);
		return expectedFilename;
	}

	public boolean isValidArchiveFilename(String filename) {

		return zipFilesDAO.isValidArchiveFilename(filename);
	}

	public String getArchiveFilenameWildcardMatcher() {
		return zipFilesDAO.getZipFilenameWildcardMatcher();
	}

	public boolean existsArchiveFile(String filename) {
		return zipFilesDAO.existsArchiveFile(filename);
	}

	public boolean isValidArchiveFile(String filename) {
		return zipFilesDAO.isValidArchiveFile(filename);

	}

	public boolean isEncryptedArchiveFile(String filename) {
		return zipFilesDAO.isEncryptedArchiveFile(filename);
	}

	private void setDefaultConseilLocalName(boolean isDefault, String name) throws DataException {
		if (isDefault) {

			userPreferencesDAO.setDefaultConseilLocalName(name);
			userPreferencesDAO.store();
		}
	}

	public void store() throws DataException {
		conseilLocalEtablissementDAO.store(currentConseilLocalEtablissement);
		
	}

}
