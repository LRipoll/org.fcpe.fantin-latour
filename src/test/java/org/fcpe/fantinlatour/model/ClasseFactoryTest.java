package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.ClasseFactory;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.fcpe.fantinlatour.model.EleveFactory;
import org.junit.Test;

public class ClasseFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private ClasseFactory classeFactory;

	@Test
	public void testCreate() {

		IMocksControl ctrl = support.createControl();
		AnneeScolaire anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		EleveFactory eleveFactory = ctrl.createMock(EleveFactory.class);
		ConseilLocalConfig config = ctrl.createMock(ConseilLocalConfig.class);

		classeFactory = new ClasseFactory(eleveFactory);

		String nom = "UneClasse";

		Classe classe = classeFactory.createClasse(anneeScolaire,nom, config);

		support.replayAll();

		assertSame(nom, classe.getNom());
		
		support.verifyAll();
	}
}
