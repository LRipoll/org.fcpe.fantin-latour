package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class DelegueTest {

	private EasyMockSupport support = new EasyMockSupport();

	private Delegue delegue;


	@Test
	public void testConstructor() {
		
		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		Engagement engagement = Engagement.OUI;
		delegue = new Delegue(classe, responsableLegal, engagement);
		support.replayAll(); 
		
		assertSame(delegue.getEngagement(),engagement);
		assertSame(delegue.getResponsableLegal(),responsableLegal);
		assertSame(delegue.getClasse(),classe);

		support.verifyAll(); 
	}

}
