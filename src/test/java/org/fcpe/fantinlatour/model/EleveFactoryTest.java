package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class EleveFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private EleveFactory eleveFactory;

	@Test
	public void testCreate() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		eleveFactory = new EleveFactory();

		String nom = "UneEleve";
		String prenom = "UnePrenomEleve";

		Eleve eleve = eleveFactory.createEleve(classe,nom,prenom);

		support.replayAll();

		assertSame(nom, eleve.getNom());
		assertSame(prenom, eleve.getPrenom());
		assertSame(classe, eleve.getClasse());
		
		support.verifyAll();
	}
}
