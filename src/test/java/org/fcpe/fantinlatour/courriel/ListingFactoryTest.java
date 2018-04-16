package org.fcpe.fantinlatour.courriel;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocal;
import org.junit.Before;
import org.junit.Test;

public class ListingFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private ListingFactory listingFactory;

	private ConseilLocal conseilLocal;
	private AnneeScolaire anneeScolaire;

	private IMocksControl ctrl;

	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);

		listingFactory = new ListingFactory(anneeScolaire);

	}

	@Test
	public void testGetGoogleGroup() {

		assertEquals(listingFactory.getGoogleGroup("a.b/c.txt"), "c");
	}

	@Test
	public void testMinus() {
		HashSet<String> firstSet = new HashSet<String>(Arrays.asList("a", "b"));
		HashSet<String> secondSet = new HashSet<String>(Arrays.asList("a", "c"));

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("b"));
		assertEquals(expectedSet, listingFactory.minus(firstSet, secondSet));
	}

	@Test
	public void testUnion() {
		HashSet<String> firstSet = new HashSet<String>(Arrays.asList("a", "b"));
		HashSet<String> secondSet = new HashSet<String>(Arrays.asList("a", "c"));

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));
		assertEquals(expectedSet, listingFactory.union(firstSet, secondSet));
	}

	@Test
	public void testGetCourrielsReturnsNullWhenGoogleGroupIsUnknown() {
		assertEquals(null, listingFactory.getCourriels("UNKNOWN"));
	}

	@Test
	public void testGetCourrielsReturnsCourrielDesAdherentsDuConseilLocalWhenGoogleGroupIsFcpeFantin() {

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));

		conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal);
		EasyMock.expect(conseilLocal.getCourrielsDesAdherents()).andReturn(expectedSet);

		support.replayAll();
		Set<String> result = listingFactory.getCourriels("fcpe-fantin");

		support.verifyAll();
		assertEquals(expectedSet, result);

	}
	
	@Test
	public void testGetCourrielsReturnsCourrielNouveauxResponsablesLegauxDuAnneeScolaireWhenGoogleGroupIsFcpeFantinInfo() {

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));

		EasyMock.expect(anneeScolaire.getCourrielNouveauxResponsablesLegaux()).andReturn(expectedSet);

		support.replayAll();
		Set<String> result = listingFactory.getCourriels("fcpe-fantin-info");

		support.verifyAll();
		assertEquals(expectedSet, result);

	}
	
	@Test
	public void testGetCourrielsReturnsCourrielsDesMembresDuBureauUnionCourrielsElusDuCAWhenGoogleGroupIsCAFcpeFantinInfo() {

		HashSet<String> bureau = new HashSet<String>(Arrays.asList("a", "b"));
		HashSet<String> ca = new HashSet<String>(Arrays.asList("a", "c"));
		
		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));

		conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(conseilLocal.getCourrielsDesMembresDuBureau()).andReturn(bureau);
		EasyMock.expect(conseilLocal.getCourrielsDesMembresConseilAdministration()).andReturn(ca);
		
		support.replayAll();
		Set<String> result = listingFactory.getCourriels("ca-fcpe-fantinlatour");

		support.verifyAll();
		assertEquals(expectedSet, result);

	}
	
	@Test
	public void testGetCourrielsReturnsCourrielsDesMembresCommissionEducativeWhenGoogleGroupIsCommissionEducFantinlatour() {

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));

		conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal);
		EasyMock.expect(conseilLocal.getCourrielsDesMembresCommissionEducative()).andReturn(expectedSet);

		support.replayAll();
		Set<String> result = listingFactory.getCourriels("commission-educ--fantinlatour");

		support.verifyAll();
		assertEquals(expectedSet, result);

	}

}
