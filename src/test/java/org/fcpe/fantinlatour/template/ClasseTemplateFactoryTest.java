package org.fcpe.fantinlatour.template;

import static org.junit.Assert.assertSame;

import java.util.Map;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.junit.Test;

public class ClasseTemplateFactoryTest {
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	@Test
	public void testCreate()  {
		ctrl = support.createControl();
		
		Classe classe = ctrl.createMock(Classe.class);
		ClasseTemplateFactory factory = new ClasseTemplateFactory(classe);
		Map<String, Object> template = factory.create();
		assertSame(classe,template.get("classe"));
	}	
	
}
