package org.fcpe.fantinlatour.model;

import static org.junit.Assert.*;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.Delegue;
import org.fcpe.fantinlatour.model.Engagement;
import org.fcpe.fantinlatour.model.ResponsableLegal;
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
