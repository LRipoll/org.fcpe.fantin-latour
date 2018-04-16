package org.fcpe.fantinlatour.util;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.junit.Before;
import org.junit.Test;

public class ClasseComparatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private ClasseComparator classeComparator;


	private Classe firstCLasse;

	private Classe secondCLasse;

	@Before
	public void setup() {
		classeComparator = new ClasseComparator();
	}

	@Test
	public void testCompareWhenNiveauAreDifferentsThenCompareThemselves() {
		
		IMocksControl ctrl = support.createControl();
		firstCLasse = ctrl.createMock(Classe.class);
		secondCLasse = ctrl.createMock(Classe.class);

	
		EasyMock.expect(firstCLasse.getNiveau()).andReturn("1");
		EasyMock.expect(secondCLasse.getNiveau()).andReturn("2");
		support.replayAll(); 

		assertTrue(classeComparator.compare(firstCLasse, secondCLasse)=="1".compareTo("2"));

		support.verifyAll(); 
	}
	
	@Test
	public void testCompareWhenNiveauAreEqualsThenCompareSection() {
		
		IMocksControl ctrl = support.createControl();
		firstCLasse = ctrl.createMock(Classe.class);
		secondCLasse = ctrl.createMock(Classe.class);

	
		EasyMock.expect(firstCLasse.getNiveau()).andReturn("1");
		EasyMock.expect(secondCLasse.getNiveau()).andReturn("1");
		EasyMock.expect(firstCLasse.getSection()).andReturn("A");
		EasyMock.expect(secondCLasse.getSection()).andReturn("B");
		support.replayAll(); 

		assertTrue(classeComparator.compare(firstCLasse, secondCLasse)=="A".compareTo("B"));

		support.verifyAll(); 
	}

}
