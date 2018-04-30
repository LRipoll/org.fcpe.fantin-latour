package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class ClasseTest {

	private static final String NOM_CLASSE = "54";

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	
	private EleveFactory eleveFactory;
	private AnneeScolaire anneeScolaire;
	private ConseilLocalConfig config;

	private Classe classe;

	@Before
	public void setup() {
		ctrl = support.createControl();
		eleveFactory = ctrl.createMock(EleveFactory.class);
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		config = ctrl.createMock(ConseilLocalConfig.class);
		classe = new Classe(anneeScolaire, NOM_CLASSE, eleveFactory, config);
	}

	@Test
	public void testConstructor() {

		support.replayAll();

		assertSame(NOM_CLASSE, classe.getNom());
		assertNotNull(classe.getEleves());
		assertEquals(0, classe.getEleves().size(), 0);
		assertEquals("5", classe.getNiveau());
		assertEquals("4", classe.getSection());
		assertEquals("5ème4", classe.getNomComplet());
		support.verifyAll();
	}

	@Test
	public void testAddEleve() {

		Eleve eleve = ctrl.createMock(Eleve.class);
		String nom = "Nom";
		String prenom = "Prénom";
		EasyMock.expect(eleveFactory.createEleve(classe, nom, prenom)).andReturn(eleve);

		support.replayAll();
		classe.addEleve(nom, prenom);

		assertSame(eleve, classe.getEleves().get(0));

		support.verifyAll();
	}
	
	@Test
	public void testToString() {

		Eleve eleve = ctrl.createMock(Eleve.class);
		String nom = "Nom";
		String prenom = "Prénom";
		EasyMock.expect(eleveFactory.createEleve(classe, nom, prenom)).andReturn(eleve);

		support.replayAll();
		classe.addEleve(nom, prenom);

		assertEquals("Classe [nom=54\n" + 
				"	EasyMock for class org.fcpe.fantinlatour.model.Eleve]", classe.toString());

		support.verifyAll();
	}

	@Test
	public void testGetResponsableLegalWhenThereIsNoResponsableLegalMustReturnNull() {

		support.replayAll();

		assertNull(classe.getResponsableLegal("", ""));

		support.verifyAll();
	}

	@Test
	public void testGetResponsableLegalWhenThereIsAResponsableLegalMustReturnItSelf() {

		Eleve eleve = ctrl.createMock(Eleve.class);
		String nom = "Nom";
		String prenom = "Prénom";
		EasyMock.expect(eleveFactory.createEleve(classe, nom, prenom)).andReturn(eleve);

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		String nom2 = "Nom2";
		String prenom2 = "Prénom2";
		EasyMock.expect(eleve.getResponsableLegal(nom2, prenom2)).andReturn(responsableLegal);
		support.replayAll();

		classe.addEleve(nom, prenom);
		assertSame(responsableLegal, classe.getResponsableLegal(nom2, prenom2));

		support.verifyAll();
	}

	@Test
	public void testEquals() {
		assertTrue(classe.equals(classe));
		assertFalse(classe.equals(null));
		assertFalse(classe.equals(""));
		assertFalse(classe.equals(new Classe(anneeScolaire, null, eleveFactory, config)));
		assertFalse(new Classe(anneeScolaire, null, eleveFactory, config).equals(classe));
		assertTrue(classe.equals(new Classe(anneeScolaire, NOM_CLASSE, eleveFactory, config)));
	}

	@Test
	public void testHashCode() {
		assertEquals(new Classe(anneeScolaire, null, eleveFactory, config).hashCode(), 31);
		assertEquals(classe.hashCode(), 31 + NOM_CLASSE.hashCode());
	}

	@Test
	public void testGetResponsableLegaux() {

		Eleve eleve1 = ctrl.createMock(Eleve.class);
		String nom = "Nom";
		String prenom = "Prénom";
		EasyMock.expect(eleveFactory.createEleve(classe, nom, prenom)).andReturn(eleve1);

		Eleve eleve2 = ctrl.createMock(Eleve.class);
		String nom2 = "Nom2";
		String prenom2 = "Prénom2";
		EasyMock.expect(eleveFactory.createEleve(classe, nom2, prenom2)).andReturn(eleve2);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);

		EasyMock.expect(eleve1.getResponsableLegal1()).andReturn(responsableLegal1);
		EasyMock.expect(eleve1.getResponsableLegal2()).andReturn(responsableLegal2);

		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(eleve2.getResponsableLegal1()).andReturn(responsableLegal3);
		EasyMock.expect(eleve2.getResponsableLegal2()).andReturn(null);
		support.replayAll();

		classe.addEleve(nom, prenom);
		classe.addEleve(nom2, prenom2);

		List<ResponsableLegal> expectedList = new ArrayList<ResponsableLegal>(
				Arrays.asList(responsableLegal1, responsableLegal2, responsableLegal3));

		assertEquals(expectedList, classe.getResponsablesLegaux());
		support.verifyAll();
	}

	@Test
	public void testGetCourrielResponsableLegaux() {

		Eleve eleve1 = ctrl.createMock(Eleve.class);
		String nom = "Nom";
		String prenom = "Prénom";
		EasyMock.expect(eleveFactory.createEleve(classe, nom, prenom)).andReturn(eleve1);

		Eleve eleve2 = ctrl.createMock(Eleve.class);
		String nom2 = "Nom2";
		String prenom2 = "Prénom2";
		EasyMock.expect(eleveFactory.createEleve(classe, nom2, prenom2)).andReturn(eleve2);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("responsableLegal@1").anyTimes();
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn(null);

		EasyMock.expect(eleve1.getResponsableLegal1()).andReturn(responsableLegal1);
		EasyMock.expect(eleve1.getResponsableLegal2()).andReturn(responsableLegal2);

		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(eleve2.getResponsableLegal1()).andReturn(responsableLegal3);
		EasyMock.expect(responsableLegal3.getCourriel()).andReturn("responsableLegal@3").anyTimes();
		EasyMock.expect(eleve2.getResponsableLegal2()).andReturn(null);
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal);
		
		Delegue delegue1 = ctrl.createMock(Delegue.class);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI);
		EasyMock.expect(delegue1.getResponsableLegal()).andReturn(responsableLegal3);
		
		support.replayAll();

		classe.addEleve(nom, prenom);
		classe.addEleve(nom2, prenom2);

		List<String> expectedList = Arrays.asList("responsablelegal@1");

		
		assertEquals(expectedList, classe.getCourrielsDesResponsablesLegaux());
		support.verifyAll();
	}

	@Test
	public void testGetCandidatsDelegues() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal);

		Delegue delegue1 = ctrl.createMock(Delegue.class);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues);

		support.replayAll();
		assertEquals(expectedDelegues, classe.getCandidatsDelegues());

		support.verifyAll();
	}

	@Test
	public void testGetNbMinimumCandidatsDeleguesWhenOnly1OuiShouldReturn1() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(1, classe.getNbMinimumCandidatsDelegues());

		support.verifyAll();
	}

	@Test
	public void testGetNbMinimumCandidatsDeleguesWhenOnly1SiBesoinShouldReturn1() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.SI_BESOIN);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(1, classe.getNbMinimumCandidatsDelegues());

		support.verifyAll();
	}
	
	@Test
	public void testGetNbCandidatsDeleguesAffirmésWhenOnly1SiBesoinShouldReturn0() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.SI_BESOIN);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(0, classe.getNbCandidatsDeleguesAffirmés());

		support.verifyAll();
	}
	
	@Test
	public void testGetNbCandidatsDeleguesAffirmésOnly4OuiAnd1SiBesoinShouldReturn4() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue5 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue5.getEngagement()).andReturn(Engagement.SI_BESOIN).anyTimes();

		List<Delegue> expectedDelegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4, delegue5);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(4, classe.getNbCandidatsDeleguesAffirmés());

		support.verifyAll();
	}

	@Test
	public void testGetNbMinimumCandidatsDeleguesWhenOnly4OuiShouldReturn4() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		List<Delegue> expectedDelegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(4, classe.getNbMinimumCandidatsDelegues());

		support.verifyAll();
	}

	@Test
	public void testGetNbMinimumCandidatsDeleguesWhenOnly4OuiAnd1SiBesoinShouldReturn4() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.OUI).anyTimes();

		Delegue delegue5 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue5.getEngagement()).andReturn(Engagement.SI_BESOIN).anyTimes();

		List<Delegue> expectedDelegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4, delegue5);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(4, classe.getNbMinimumCandidatsDelegues());

		support.verifyAll();
	}

	@Test
	public void testGetCandidatsDeleguesWhenOnly1OuiShouldReturnThisOne() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(expectedDelegues, classe.getDeleguesRetenus());

		support.verifyAll();
	}

	@Test
	public void testGetCandidatsDeleguesWhenOnly1SiBesoinShouldReturn1() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.SI_BESOIN);

		List<Delegue> expectedDelegues = Arrays.asList(delegue1);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(expectedDelegues, classe.getDeleguesRetenus());

		support.verifyAll();
	}

	@Test
	public void testGetCandidatsDeleguesWhenOnly4OuiShouldReturn4() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();

		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue1.getResponsableLegal()).andReturn(responsableLegal1).anyTimes();
		EasyMock.expect(responsableLegal1.getNbEngagements()).andReturn(1).anyTimes();
		EasyMock.expect(responsableLegal1.getNom()).andReturn("Nom1").anyTimes();
		EasyMock.expect(responsableLegal1.getPrenom()).andReturn("Prenom1").anyTimes();

		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue2.getResponsableLegal()).andReturn(responsableLegal2).anyTimes();
		EasyMock.expect(responsableLegal2.getNbEngagements()).andReturn(2).anyTimes();
		EasyMock.expect(responsableLegal2.getNom()).andReturn("Nom2").anyTimes();
		EasyMock.expect(responsableLegal2.getPrenom()).andReturn("Prenom2").anyTimes();

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue3.getResponsableLegal()).andReturn(responsableLegal3).anyTimes();
		EasyMock.expect(responsableLegal3.getNbEngagements()).andReturn(3).anyTimes();
		EasyMock.expect(responsableLegal3.getNom()).andReturn("Nom3").anyTimes();
		EasyMock.expect(responsableLegal3.getPrenom()).andReturn("Prenom3").anyTimes();

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal4 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue4.getResponsableLegal()).andReturn(responsableLegal4).anyTimes();
		EasyMock.expect(responsableLegal4.getNbEngagements()).andReturn(4).anyTimes();
		EasyMock.expect(responsableLegal4.getNom()).andReturn("Nom4").anyTimes();
		EasyMock.expect(responsableLegal4.getPrenom()).andReturn("Prenom4").anyTimes();

		List<Delegue> expectedDelegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(expectedDelegues).anyTimes();

		support.replayAll();
		assertEquals(expectedDelegues, classe.getDeleguesRetenus());

		support.verifyAll();
	}

	@Test
	public void testGetCandidatsDeleguesWhenOnly4OuiAnd1SiBesoinShouldReturn4() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue1.getResponsableLegal()).andReturn(responsableLegal1).anyTimes();
		EasyMock.expect(responsableLegal1.getNbEngagements()).andReturn(1).anyTimes();
		EasyMock.expect(responsableLegal1.getNom()).andReturn("Nom1").anyTimes();
		EasyMock.expect(responsableLegal1.getPrenom()).andReturn("Prenom1").anyTimes();

		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue2.getResponsableLegal()).andReturn(responsableLegal2).anyTimes();
		EasyMock.expect(responsableLegal2.getNbEngagements()).andReturn(2).anyTimes();
		EasyMock.expect(responsableLegal2.getNom()).andReturn("Nom2").anyTimes();
		EasyMock.expect(responsableLegal2.getPrenom()).andReturn("Prenom2").anyTimes();

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue3.getResponsableLegal()).andReturn(responsableLegal3).anyTimes();
		EasyMock.expect(responsableLegal3.getNbEngagements()).andReturn(3).anyTimes();
		EasyMock.expect(responsableLegal3.getNom()).andReturn("Nom3").anyTimes();
		EasyMock.expect(responsableLegal3.getPrenom()).andReturn("Prenom3").anyTimes();

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.SI_BESOIN).anyTimes();
		ResponsableLegal responsableLegal4 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue4.getResponsableLegal()).andReturn(responsableLegal4).anyTimes();
		EasyMock.expect(responsableLegal4.getNbEngagements()).andReturn(4).anyTimes();
		EasyMock.expect(responsableLegal4.getNom()).andReturn("Nom4").anyTimes();
		EasyMock.expect(responsableLegal4.getPrenom()).andReturn("Prenom4").anyTimes();

		Delegue delegue5 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue5.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal5 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue5.getResponsableLegal()).andReturn(responsableLegal5).anyTimes();
		EasyMock.expect(responsableLegal5.getNbEngagements()).andReturn(5).anyTimes();
		EasyMock.expect(responsableLegal5.getNom()).andReturn("Nom5").anyTimes();
		EasyMock.expect(responsableLegal5.getPrenom()).andReturn("Prenom5").anyTimes();

		List<Delegue> delegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4, delegue5);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(delegues).anyTimes();

		List<Delegue> expectedDelegues = Arrays.asList(delegue1, delegue2, delegue3, delegue5);
		support.replayAll();
		assertEquals(expectedDelegues, classe.getDeleguesRetenus());

		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielsDesDelegues() {
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue1.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue1.getResponsableLegal()).andReturn(responsableLegal1).anyTimes();
		EasyMock.expect(responsableLegal1.getNbEngagements()).andReturn(1).anyTimes();
		EasyMock.expect(responsableLegal1.getNom()).andReturn("Nom1").anyTimes();
		EasyMock.expect(responsableLegal1.getPrenom()).andReturn("Prenom1").anyTimes();
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("delegue@1");
		
		Delegue delegue2 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue2.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue2.getResponsableLegal()).andReturn(responsableLegal2).anyTimes();
		EasyMock.expect(responsableLegal2.getNbEngagements()).andReturn(2).anyTimes();
		EasyMock.expect(responsableLegal2.getNom()).andReturn("Nom2").anyTimes();
		EasyMock.expect(responsableLegal2.getPrenom()).andReturn("Prenom2").anyTimes();
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn("delegue@2");

		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue3.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue3.getResponsableLegal()).andReturn(responsableLegal3).anyTimes();
		EasyMock.expect(responsableLegal3.getNbEngagements()).andReturn(3).anyTimes();
		EasyMock.expect(responsableLegal3.getNom()).andReturn("Nom3").anyTimes();
		EasyMock.expect(responsableLegal3.getPrenom()).andReturn("Prenom3").anyTimes();
		EasyMock.expect(responsableLegal3.getCourriel()).andReturn("delegue@3");

		Delegue delegue4 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue4.getEngagement()).andReturn(Engagement.SI_BESOIN).anyTimes();
		ResponsableLegal responsableLegal4 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue4.getResponsableLegal()).andReturn(responsableLegal4).anyTimes();
		EasyMock.expect(responsableLegal4.getNbEngagements()).andReturn(4).anyTimes();
		EasyMock.expect(responsableLegal4.getNom()).andReturn("Nom4").anyTimes();
		EasyMock.expect(responsableLegal4.getPrenom()).andReturn("Prenom4").anyTimes();
		

		Delegue delegue5 = ctrl.createMock(Delegue.class);
		EasyMock.expect(delegue5.getEngagement()).andReturn(Engagement.OUI).anyTimes();
		ResponsableLegal responsableLegal5 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue5.getResponsableLegal()).andReturn(responsableLegal5).anyTimes();
		EasyMock.expect(responsableLegal5.getNbEngagements()).andReturn(5).anyTimes();
		EasyMock.expect(responsableLegal5.getNom()).andReturn("Nom5").anyTimes();
		EasyMock.expect(responsableLegal5.getPrenom()).andReturn("Prenom5").anyTimes();
		EasyMock.expect(responsableLegal5.getCourriel()).andReturn("delegue@5");

		List<Delegue> delegues = Arrays.asList(delegue1, delegue2, delegue3, delegue4, delegue5);
		EasyMock.expect(conseilLocal.getDelegues(classe)).andReturn(delegues).anyTimes();

		List<String> expectedMails = Arrays.asList("delegue@1", "delegue@2", "delegue@3", "delegue@5");
		support.replayAll();
		assertEquals(expectedMails, classe.getCourrielsDesDelegues());

		support.verifyAll();
	}

}
