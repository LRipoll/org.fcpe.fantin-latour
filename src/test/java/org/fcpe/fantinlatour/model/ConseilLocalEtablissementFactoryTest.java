package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class ConseilLocalEtablissementFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private EtablissementFactory etablisssementFactory;

	private ConseilLocalEtablissementFactory conseilLocalEtablissementFactory;

	@Test
	public void testCreate() {

		IMocksControl ctrl = support.createControl();

		etablisssementFactory = ctrl.createMock(EtablissementFactory.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);
		EasyMock.expect(etablisssementFactory.create("Test", TypeEtablissement.COLLEGE)).andReturn(etablissement);

		conseilLocalEtablissementFactory = new ConseilLocalEtablissementFactory(etablisssementFactory);

		support.replayAll();
		ConseilLocalEtablissement conseilLocalEtablissement = conseilLocalEtablissementFactory.create("Test",
				TypeEtablissement.COLLEGE);
		assertSame(etablissement, conseilLocalEtablissement.getEtablissement());

		support.verifyAll();
	}
}
