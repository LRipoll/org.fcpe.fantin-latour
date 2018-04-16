package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class EntiteRacineTest {
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	private EntiteRacine entiteRacine;
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		
		
	}
	
	@Test
	public void testGroupe() {

		entiteRacine = new EntiteRacine("Test", 0);
		GroupeDeResponsablesLegaux groupe = ctrl.createMock(GroupeDeResponsablesLegaux.class);
		String nom = "Nom";
		
		EasyMock.expect(groupe.getNom()).andReturn(nom);

		support.replayAll();
		entiteRacine.addGroupe(groupe);

		assertSame(groupe, entiteRacine.getGroupes().iterator().next());
		assertSame(groupe, entiteRacine.getGroupe(nom));
		support.verifyAll();
	}
	
	@Test
	public void testGroupes() {

		entiteRacine = new EntiteRacine("Test", 0);
		GroupeDeResponsablesLegaux groupe = ctrl.createMock(GroupeDeResponsablesLegaux.class);
		String nom = "Nom";
		
		EasyMock.expect(groupe.getNom()).andReturn(nom);

		support.replayAll();
		entiteRacine.addGroupes(Arrays.asList(groupe));

		assertSame(groupe, entiteRacine.getGroupes().iterator().next());
		assertSame(groupe, entiteRacine.getGroupe(nom));
		support.verifyAll();
	}
}
