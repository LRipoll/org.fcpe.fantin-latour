package org.fcpe.fantinlatour.app.context;

import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.junit.Before;
import org.junit.Test;

public class AppContextTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	
	private AnneeScolaire anneeScolaire;
	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
	}
	
	@Test
	public void testSetter() {
		AppContext appContext = new AppContext();
		
		appContext.setAnneeScolaire(anneeScolaire);
		
		assertSame(anneeScolaire, appContext.getAnneeScolaire());
	}

}
