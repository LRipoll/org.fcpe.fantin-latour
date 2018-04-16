package org.fcpe.fantinlatour.util;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Personne;
import org.junit.Before;
import org.junit.Test;

public class PersonneComparatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private PersonneComparator PersonneComparator;


	private Personne firstPersonne;

	private Personne secondPersonne;

	@Before
	public void setup() {
		PersonneComparator = new PersonneComparator();
	}

	@Test
	public void testCompareWhenNomsAreDifferentsThenCompareThemselves() {
		
		IMocksControl ctrl = support.createControl();
		firstPersonne = ctrl.createMock(Personne.class);
		secondPersonne = ctrl.createMock(Personne.class);

	
		EasyMock.expect(firstPersonne.getNom()).andReturn("1");
		EasyMock.expect(secondPersonne.getNom()).andReturn("2");
		support.replayAll(); 

		assertTrue(PersonneComparator.compare(firstPersonne, secondPersonne)=="1".compareTo("2"));

		support.verifyAll(); 
	}
	
	@Test
	public void testCompareWhenNomsAreEqualsThenComparePrenom() {
		
		IMocksControl ctrl = support.createControl();
		firstPersonne = ctrl.createMock(Personne.class);
		secondPersonne = ctrl.createMock(Personne.class);

	
		EasyMock.expect(firstPersonne.getNom()).andReturn("1");
		EasyMock.expect(secondPersonne.getNom()).andReturn("1");
		EasyMock.expect(firstPersonne.getPrenom()).andReturn("A");
		EasyMock.expect(secondPersonne.getPrenom()).andReturn("B");
		support.replayAll(); 

		assertTrue(PersonneComparator.compare(firstPersonne, secondPersonne)=="A".compareTo("B"));

		support.verifyAll(); 
	}

}
