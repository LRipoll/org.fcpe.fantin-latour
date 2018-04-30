package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class EleveTest {
	private EasyMockSupport support = new EasyMockSupport();

	private Eleve eleve;

	@Test
	public void testConstructor() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);
		support.replayAll();

		assertSame(nom, eleve.getNom());
		assertSame(prenom, eleve.getPrenom());
		assertSame(classe, eleve.getClasse());

		support.verifyAll();
	}

	@Test
	public void testResponsableLegal1() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal);
		support.replayAll();

		assertSame(responsableLegal, eleve.getResponsableLegal1());
		support.verifyAll();
	}

	@Test
	public void testResponsableLegal2() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal2(responsableLegal);
		support.replayAll();

		assertSame(responsableLegal, eleve.getResponsableLegal2());
		support.verifyAll();
	}

	@Test
	public void testResponsableLegalWhenIsTheResponsableLegal1ShoudReturnTheResponsableLegal1() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal);

		prenom = "ResponsableLegal1";
		EasyMock.expect(responsableLegal.isThisPersonne(nom, prenom)).andReturn(true);

		support.replayAll();

		assertSame(responsableLegal, eleve.getResponsableLegal(nom, prenom));
		support.verifyAll();
	}

	@Test
	public void testResponsableLegalWhenIsNotTheResponsableLegal1ButTheResponsableLegal2ShoudReturnTheResponsableLegal2() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal1);

		prenom = "ResponsableLegal2";
		EasyMock.expect(responsableLegal1.isThisPersonne(nom, prenom)).andReturn(false);

		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal2(responsableLegal2);

		EasyMock.expect(responsableLegal2.isThisPersonne(nom, prenom)).andReturn(true);

		support.replayAll();

		assertSame(responsableLegal2, eleve.getResponsableLegal(nom, prenom));
		support.verifyAll();
	}

	@Test
	public void testResponsableLegalWhenIsNotTheResponsableLegal1AndNotTheResponsableLegal2ShoudReturnNull() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal1);

		prenom = "ResponsableInconnu";
		EasyMock.expect(responsableLegal1.isThisPersonne(nom, prenom)).andReturn(false);

		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal2(responsableLegal2);

		EasyMock.expect(responsableLegal2.isThisPersonne(nom, prenom)).andReturn(false);

		support.replayAll();

		assertNull(eleve.getResponsableLegal(nom, prenom));
		support.verifyAll();
	}

	@Test
	public void testResponsableLegalWhenIsNotTheResponsableLegal1AndThereIsNoResponsableLegal2ShoudReturnNull() {

		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal1);

		prenom = "ResponsableInconnu";
		EasyMock.expect(responsableLegal1.isThisPersonne(nom, prenom)).andReturn(false);

		support.replayAll();

		assertNull(eleve.getResponsableLegal(nom, prenom));
		support.verifyAll();
	}

	@Test
	public void testToString() {
		IMocksControl ctrl = support.createControl();
		Classe classe = ctrl.createMock(Classe.class);

		String nom = "Nom";
		String prenom = "Prénom";
		eleve = new Eleve(classe, nom, prenom);
		String nomClasse = "UneClasse";
		EasyMock.expect(classe.getNom()).andReturn(nomClasse).anyTimes();

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		eleve.setResponsableLegal1(responsableLegal1);
		
		support.replayAll();
		assertEquals("Eleve [nom=Nom, prenom=Prénom classe=UneClasse, responsableLegal1=EasyMock for class org.fcpe.fantinlatour.model.ResponsableLegal, responsableLegal2=null]", eleve.toString());
		
		support.verifyAll();
	}
}
