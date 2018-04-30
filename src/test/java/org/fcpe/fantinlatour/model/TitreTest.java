package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class TitreTest {

	@Test
	public void test() {
		assertSame(Titre.Président,Titre.parse("président"));
		assertSame(Titre.Membre,Titre.parse("membre"));
		assertSame(Titre.Secrétaire,Titre.parse("secrétaire"));
		assertSame(Titre.Trésorier,Titre.parse("trésorier"));
		assertSame(Titre.Vice_Président,Titre.parse("vice-président"));
		assertNull(Titre.parse(""));
	}
}
