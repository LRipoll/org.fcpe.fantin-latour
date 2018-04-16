package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.fcpe.fantinlatour.model.ResponsableLegalFactory;
import org.junit.Test;

public class ResponsableLegalFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private ResponsableLegalFactory responsableLegalFactory;

	@Test
	public void testCreate() {

		responsableLegalFactory = new ResponsableLegalFactory();

		String nom = "UneResponsableLegal";
		String prenom = "UnePrenomResponsableLegal";
		String mail = "mail";

		ResponsableLegal responsableLegal = responsableLegalFactory.createResponsableLegal(nom, prenom, mail);

		support.replayAll();

		assertSame(nom, responsableLegal.getNom());
		assertSame(prenom, responsableLegal.getPrenom());
		assertSame(mail, responsableLegal.getCourriel());

		support.verifyAll();
	}
	
	@Test
	public void testCreateWhenNomIsEmptyShouldReturnNull() {

		responsableLegalFactory = new ResponsableLegalFactory();

		String nom = "";
		String prenom = "UnePrenomResponsableLegal";
		String mail = "mail";

		ResponsableLegal responsableLegal = responsableLegalFactory.createResponsableLegal(nom, prenom, mail);

		support.replayAll();

		assertNull(responsableLegal);
		

		support.verifyAll();
	}
}
