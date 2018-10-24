package org.fcpe.fantinlatour.template;

import static org.junit.Assert.assertSame;

import java.util.Map;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.junit.Test;

public class AnneeScolaireTemplateFactoryTest {
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	@Test
	public void testCreate()  {
		ctrl = support.createControl();
		
		AnneeScolaire anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		AnneeScolaireTemplateFactory factory = new AnneeScolaireTemplateFactory(anneeScolaire);
		Map<String, Object> template = factory.create();
		assertSame(anneeScolaire,template.get("anneeScolaire"));
	}	
	
}
