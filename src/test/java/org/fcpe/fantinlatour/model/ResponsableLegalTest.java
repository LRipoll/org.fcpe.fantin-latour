package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMockSupport;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.Test;

public class ResponsableLegalTest {
	private EasyMockSupport support = new EasyMockSupport();

	private ResponsableLegal responsableLegal;

	@Test
	public void testConstructor() {

		String nom = "Nom";
		String prenom = "Prénom";
		String courriel ="un@courriel";
		responsableLegal = new ResponsableLegal(nom, prenom, courriel );
		support.replayAll();

		assertSame(nom, responsableLegal.getNom());
		assertSame(prenom, responsableLegal.getPrenom());
		assertSame(courriel, responsableLegal.getCourriel());
		assertEquals(0, responsableLegal.getMontantAdhesion(),0);
		assertEquals(0, responsableLegal.getNbEngagements());
		assertFalse(responsableLegal.isAdhesionTransmise());
		assertEquals("",responsableLegal.getMembreDe());
		assertEquals("ResponsableLegal [nom=Nom, prenom=Prénom courriel=un@courriel]",responsableLegal.toString());

		support.verifyAll();
	}
	
	@Test
	public void testAdhesionTransmise() {

		String nom = "Nom";
		String prenom = "Prénom";
		String courriel ="un@courriel";
		responsableLegal = new ResponsableLegal(nom, prenom, courriel );
		responsableLegal.setAdhesionTransmise(true);
		responsableLegal.setMontantAdhesion(10);
		support.replayAll();


		assertTrue(responsableLegal.isAdhesionTransmise());
		assertEquals(10, responsableLegal.getMontantAdhesion(),0);
		
		support.verifyAll();
	}
	
	@Test
	public void testMembreDeWhenOnlyOneMembreOfShouldReturnItself() {

		String nom = "Nom";
		String prenom = "Prénom";
		String courriel ="un@courriel";
		responsableLegal = new ResponsableLegal(nom, prenom, courriel );
		String membre = "Test";
		responsableLegal.estMembreDe(membre);
		support.replayAll();


		assertEquals(" (Test)**",responsableLegal.getMembreDe());
		assertEquals(1,responsableLegal.getNbEngagements());

		support.verifyAll();
	}
	
	@Test
	public void testMembreDeWhenTwoMembreOfShouldReturnThemself() {

		String nom = "Nom";
		String prenom = "Prénom";
		String courriel ="un@courriel";
		responsableLegal = new ResponsableLegal(nom, prenom, courriel );
		String membre = "Test";
		responsableLegal.estMembreDe(membre);
		responsableLegal.estMembreDe("Test2");
		support.replayAll();


		assertEquals(" (Test, Test2)**",responsableLegal.getMembreDe());
		assertEquals(2,responsableLegal.getNbEngagements());

		support.verifyAll();
	}
}
