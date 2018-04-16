package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.junit.Test;

public class EtablisssementFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private EtablissementFactory etablisssementFactory;

	@Test
	public void testCreate() {

		etablisssementFactory = new EtablissementFactory();

		String nom = "UnEtablissement";

		Etablissement etablissement = etablisssementFactory.create(nom, TypeEtablissement.ELEMENTAIRE);

		support.replayAll();

		assertSame(nom, etablissement.getNom());
		assertSame(TypeEtablissement.ELEMENTAIRE, etablissement.getTypeEtablissement());

		support.verifyAll();
	}
}
