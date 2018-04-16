package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Candidat;
import org.fcpe.fantinlatour.model.Engagement;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.Test;

public class CandidatTest {

	private EasyMockSupport support = new EasyMockSupport();

	private Candidat candidat;


	@Test
	public void testConstructor() {
		
		IMocksControl ctrl = support.createControl();
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		Engagement engagement = Engagement.OUI;
		candidat = new Candidat(responsableLegal, engagement);
		support.replayAll(); 
		
		assertSame(candidat.getEngagement(),engagement);
		assertSame(candidat.getResponsableLegal(),responsableLegal);

		support.verifyAll(); 
	}
	

}
