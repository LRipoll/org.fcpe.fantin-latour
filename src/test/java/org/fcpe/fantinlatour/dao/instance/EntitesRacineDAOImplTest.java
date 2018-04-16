package org.fcpe.fantinlatour.dao.instance;

import static org.junit.Assert.assertEquals;

import org.fcpe.fantinlatour.model.EntitesRacineFactoryProvider;
import org.fcpe.fantinlatour.model.TypeEnseignement;
import org.junit.Before;
import org.junit.Test;

public class EntitesRacineDAOImplTest {

	private EntitesRacineDAOImpl entitesRacineDAOImpl;

	private EntitesRacineFactoryProvider entitesRacineFactoryProviderImpl;

	@Before
	public void setup() {

		entitesRacineFactoryProviderImpl = new EntitesRacineFactoryProviderImpl(
				new GroupeDeResponsablesLegauxFactoryProvider());

	}

	@Test
	public void testGetEntitesRacinesElementaires() {

		entitesRacineDAOImpl = new EntitesRacineDAOImpl(entitesRacineFactoryProviderImpl);

		assertEquals(2, entitesRacineDAOImpl.getEntitesRacines(TypeEnseignement.ELEMENTAIRE).size());
		assertEquals(3, entitesRacineDAOImpl.getEntitesRacines(TypeEnseignement.SECONDAIRE).size());

	}
}
