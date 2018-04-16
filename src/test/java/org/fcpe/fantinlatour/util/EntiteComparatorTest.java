package org.fcpe.fantinlatour.util;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Entite;
import org.junit.Before;
import org.junit.Test;

public class EntiteComparatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private EntiteComparator entiteComparator;
	
	@Before
	public void setup() {
		entiteComparator = new EntiteComparator();
	}
	
	@Test
	public void testCompareWhenPrioriteAreDifferent() {
		
		IMocksControl ctrl = support.createControl();
		Entite entite1 = ctrl.createMock(Entite.class);
		Entite entite2 = ctrl.createMock(Entite.class);

	
		EasyMock.expect(entite1.getPriorite()).andReturn(1);
		EasyMock.expect(entite2.getPriorite()).andReturn(2);
		support.replayAll(); 

		assertTrue(entiteComparator.compare(entite1, entite2)=="1".compareTo("2"));

		support.verifyAll(); 
	}

}
