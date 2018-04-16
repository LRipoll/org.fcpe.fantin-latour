package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.junit.Test;

public class TypeEnseignementFactoryTest {

	@Test
	public void testCreate() {
		TypeEnseignementFactory factory = new TypeEnseignementFactory();
		assertSame(TypeEnseignement.ELEMENTAIRE, factory.create(TypeEtablissement.ELEMENTAIRE));
		assertSame(TypeEnseignement.ELEMENTAIRE, factory.create(TypeEtablissement.MATERNELLE));
		assertSame(TypeEnseignement.ELEMENTAIRE, factory.create(TypeEtablissement.PRIMAIRE));
		assertSame(TypeEnseignement.SECONDAIRE, factory.create(TypeEtablissement.COLLEGE));
		assertSame(TypeEnseignement.SECONDAIRE, factory.create(TypeEtablissement.LYCEE));

	}

}
