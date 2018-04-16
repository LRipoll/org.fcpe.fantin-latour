package org.fcpe.fantinlatour.util;

import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Delegue;
import org.fcpe.fantinlatour.model.Engagement;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.Before;
import org.junit.Test;

public class DelegueComparatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private DelegueComparator DelegueComparator;


	private Delegue firstDelegue;
	private Delegue secondDelegue;
	
	private ResponsableLegal firstResponsableLegal;
	private ResponsableLegal secondResponsableLegal;


	@Before
	public void setup() {
		DelegueComparator = new DelegueComparator();
	}

	@Test
	public void testCompareWhenEngagementsAreDifferentsThenCompareThemselves() {
		
		IMocksControl ctrl = support.createControl();
		firstDelegue = ctrl.createMock(Delegue.class);
		secondDelegue = ctrl.createMock(Delegue.class);

	
		EasyMock.expect(firstDelegue.getEngagement()).andReturn(Engagement.NON);
		EasyMock.expect(secondDelegue.getEngagement()).andReturn(Engagement.OUI);
		support.replayAll(); 

		assertTrue(DelegueComparator.compare(firstDelegue, secondDelegue)==Engagement.NON.compareTo(Engagement.OUI));

		support.verifyAll(); 
	}
	
	@Test
	public void testCompareWhenEngagementsAreEqualsThenComparePoidsEngagement() {
		
		IMocksControl ctrl = support.createControl();
		firstDelegue = ctrl.createMock(Delegue.class);
		secondDelegue = ctrl.createMock(Delegue.class);
		
		

		EasyMock.expect(firstDelegue.getEngagement()).andReturn(Engagement.OUI);
		EasyMock.expect(secondDelegue.getEngagement()).andReturn(Engagement.OUI);
		
		firstResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		secondResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		EasyMock.expect(firstDelegue.getResponsableLegal()).andReturn(firstResponsableLegal).anyTimes();
		EasyMock.expect(secondDelegue.getResponsableLegal()).andReturn(secondResponsableLegal).anyTimes();
		
		EasyMock.expect(firstResponsableLegal.getNbEngagements()).andReturn(1);
		EasyMock.expect(secondResponsableLegal.getNbEngagements()).andReturn(0);
		support.replayAll(); 

		assertTrue(DelegueComparator.compare(firstDelegue, secondDelegue)==new Integer(0).compareTo(1));

		support.verifyAll(); 
	}
	
	@Test
	public void testCompareWhenEngagementsAreEqualsAndPoidsAreSimilarThenCompareNom() {
		
		IMocksControl ctrl = support.createControl();
		firstDelegue = ctrl.createMock(Delegue.class);
		secondDelegue = ctrl.createMock(Delegue.class);
		
		

		EasyMock.expect(firstDelegue.getEngagement()).andReturn(Engagement.OUI);
		EasyMock.expect(secondDelegue.getEngagement()).andReturn(Engagement.OUI);
		
		firstResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		secondResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		EasyMock.expect(firstDelegue.getResponsableLegal()).andReturn(firstResponsableLegal).anyTimes();
		EasyMock.expect(secondDelegue.getResponsableLegal()).andReturn(secondResponsableLegal).anyTimes();
		
		EasyMock.expect(firstResponsableLegal.getNbEngagements()).andReturn(1);
		EasyMock.expect(secondResponsableLegal.getNbEngagements()).andReturn(2);
		
		EasyMock.expect(firstResponsableLegal.getNom()).andReturn("A");
		EasyMock.expect(secondResponsableLegal.getNom()).andReturn("B");
		support.replayAll(); 

		assertTrue(DelegueComparator.compare(firstDelegue, secondDelegue)=="A".compareTo("B"));

		support.verifyAll(); 
	}
	
	@Test
	public void testCompareWhenEngagementsAreEqualsAndPoidsAreSimilarAndNameAreEqualsThenComparePrenom() {
		
		IMocksControl ctrl = support.createControl();
		firstDelegue = ctrl.createMock(Delegue.class);
		secondDelegue = ctrl.createMock(Delegue.class);
		
		

		EasyMock.expect(firstDelegue.getEngagement()).andReturn(Engagement.OUI);
		EasyMock.expect(secondDelegue.getEngagement()).andReturn(Engagement.OUI);
		
		firstResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		secondResponsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		EasyMock.expect(firstDelegue.getResponsableLegal()).andReturn(firstResponsableLegal).anyTimes();
		EasyMock.expect(secondDelegue.getResponsableLegal()).andReturn(secondResponsableLegal).anyTimes();
		
		EasyMock.expect(firstResponsableLegal.getNbEngagements()).andReturn(1);
		EasyMock.expect(secondResponsableLegal.getNbEngagements()).andReturn(2);
		
		EasyMock.expect(firstResponsableLegal.getNom()).andReturn("A");
		EasyMock.expect(secondResponsableLegal.getNom()).andReturn("A");
		
		EasyMock.expect(firstResponsableLegal.getPrenom()).andReturn("0");
		EasyMock.expect(secondResponsableLegal.getPrenom()).andReturn("1");
		
		support.replayAll(); 

		assertTrue(DelegueComparator.compare(firstDelegue, secondDelegue)=="0".compareTo("1"));

		support.verifyAll(); 
	}

}
