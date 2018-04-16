package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.fcpe.fantinlatour.model.Titre;
import org.junit.Test;

public class TitreTest {

	@Test
	public void test() {
		assertSame(Titre.Pr�sident,Titre.parse("pr�sident"));
		assertSame(Titre.Membre,Titre.parse("membre"));
		assertSame(Titre.Secr�taire,Titre.parse("secr�taire"));
		assertSame(Titre.Tr�sorier,Titre.parse("tr�sorier"));
		assertSame(Titre.Vice_Pr�sident,Titre.parse("vice-pr�sident"));
		assertNull(Titre.parse(""));
	}
}
