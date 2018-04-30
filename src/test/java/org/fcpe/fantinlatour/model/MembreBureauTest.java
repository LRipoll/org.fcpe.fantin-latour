package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class MembreBureauTest {

	private EasyMockSupport support = new EasyMockSupport();

	private MembreBureau membreBureau;


	@Test
	public void testConstructor() {
		
		IMocksControl ctrl = support.createControl();
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		Titre titre = Titre.Membre;
		membreBureau = new MembreBureau(responsableLegal, titre);
		support.replayAll(); 
		
		assertSame(membreBureau.getTitre(),titre);
		assertSame(membreBureau.getResponsableLegal(),responsableLegal);

		support.verifyAll(); 
	}
	

}
