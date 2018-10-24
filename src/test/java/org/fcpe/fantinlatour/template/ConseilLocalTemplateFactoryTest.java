package org.fcpe.fantinlatour.template;

import static org.junit.Assert.assertSame;

import java.util.Map;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.ConseilLocal;
import org.junit.Test;

public class ConseilLocalTemplateFactoryTest {
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	@Test
	public void testCreate()  {
		ctrl = support.createControl();
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		ConseilLocalTemplateFactory factory = new ConseilLocalTemplateFactory(conseilLocal);
		Map<String, Object> template = factory.create();
		assertSame(conseilLocal,template.get("conseilLocal"));
	}	
	
}
