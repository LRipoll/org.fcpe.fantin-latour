package org.fcpe.fantinlatour.model.controller;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.ConseilLocalEtablissementDAO;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.UserPreferencesDAO;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.junit.Before;
import org.junit.Test;

public class ConseilLocalEtablissementManagerTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private ConseilLocalEtablissementDAO conseilLocalEtablissementDAO;
	private UserPreferencesDAO userPreferencesDAO;
	private ConseilLocalEtablissementManagerListener conseilLocalEtablissementManagerListener;
	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@Before
	public void setup() {

		ctrl = support.createControl();
		conseilLocalEtablissementDAO = ctrl.createMock(ConseilLocalEtablissementDAO.class);
		userPreferencesDAO = ctrl.createMock(UserPreferencesDAO.class);
		conseilLocalEtablissementManagerListener  = ctrl.createMock(ConseilLocalEtablissementManagerListener.class);
		conseilLocalEtablissementManager = new ConseilLocalEtablissementManager(conseilLocalEtablissementDAO,
				userPreferencesDAO);
		
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
	public void testGetDefaultWhenNoDefaultDefinesShouldReturnNull() throws DataException {

		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn(null);

		conseilLocalEtablissementManagerListener.onSelected(null);
		EasyMock.expectLastCall().once();
		support.replayAll();
		conseilLocalEtablissementManager.init();
		assertNull(conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());

		support.verifyAll();
	}
	
	@Test
	public void testGetDefaultWhenDefaultShouldReturnIt() throws DataException {

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		
		EasyMock.expect(userPreferencesDAO.getDefaultConseilLocalName()).andReturn("Default");
		EasyMock.expect(conseilLocalEtablissementDAO.load("Default")).andReturn(conseilLocalEtablissement);
		conseilLocalEtablissementManagerListener.onSelected(conseilLocalEtablissement);
		EasyMock.expectLastCall().once();

		support.replayAll();

		conseilLocalEtablissementManager.init();
		assertSame(conseilLocalEtablissement, conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement());
		support.verifyAll();
	}
}
