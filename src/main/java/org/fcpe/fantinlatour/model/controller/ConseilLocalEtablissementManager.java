package org.fcpe.fantinlatour.model.controller;

import java.util.ArrayList;
import java.util.List;

import org.fcpe.fantinlatour.dao.ConseilLocalEtablissementDAO;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.UserPreferencesDAO;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.TypeEtablissement;

public class ConseilLocalEtablissementManager implements UniqueNameManager {

	public static final String ID = "conseilLocalEtablissementManager";

	private ConseilLocalEtablissementDAO conseilLocalEtablissementDAO;
	private UserPreferencesDAO userPreferencesDAO;
	private ConseilLocalEtablissement currentConseilLocalEtablissement;
	private List<ConseilLocalEtablissementManagerListener> listeners;

	public ConseilLocalEtablissementManager(ConseilLocalEtablissementDAO conseilLocalEtablissementDAO,
			UserPreferencesDAO userPreferencesDAO) {
		this.conseilLocalEtablissementDAO = conseilLocalEtablissementDAO;
		this.userPreferencesDAO = userPreferencesDAO;
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

	public ConseilLocalEtablissement create(String name, TypeEtablissement typeEtablissement, boolean isDefault)
			throws DataException {

		ConseilLocalEtablissement result = conseilLocalEtablissementDAO.create(name, typeEtablissement);

		if (isDefault) {

			userPreferencesDAO.setDefaultConseilLocalName(name);
			userPreferencesDAO.store();
		}
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

}
