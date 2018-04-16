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
		ConseilLocalEtablissement result = null;
		String defaultName = userPreferencesDAO.getDefaultConseilLocalName();
		if (defaultName != null) {
			result = conseilLocalEtablissementDAO.load(defaultName);
		}
		notifyListeners(result);

	}

	public List<String> getExistingConseilEtablissements() throws DataException {
		return conseilLocalEtablissementDAO.getExistingConseilEtablissements();
	}

}
