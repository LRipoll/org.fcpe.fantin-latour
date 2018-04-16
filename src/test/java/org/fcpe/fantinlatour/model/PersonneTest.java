package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fcpe.fantinlatour.model.Personne;
import org.junit.Before;
import org.junit.Test;

public class PersonneTest {

	private Personne personne;

	@Before
	public void setup() {
		personne = new Personne("Nom", "Prénom");
	}

	@Test
	public void testConstructor() {

		assertEquals("Nom", personne.getNom());
		assertEquals("Prénom", personne.getPrenom());

	}
	
	@Test
	public void testIsThisPersonneReturnsFalseWhenNomAreDifferents() {

		assertFalse(personne.isThisPersonne(null, "Prénom"));
	}
	
	@Test
	public void testIsThisPersonneReturnsFalseWhenPrenomAreDifferents() {

		assertFalse(personne.isThisPersonne("Nom", null));
	}
	
	@Test
	public void testIsThisPersonneReturnsTrueWhenNomAndPrenomAreEqualsIgnoreCase() {

		assertTrue(personne.isThisPersonne("NOM", "prénom"));
	}
	
	@Test
	public void testToString() {
		
		assertEquals("nom=Nom, prenom=Prénom",personne.toString());
	}
	
	

}
