package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class ConseilLocalTest {
	
	private ConseilLocalConfig config;
	private ConseilLocal conseilLocal;
	
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	@Before
	public void setup() {
		ctrl = support.createControl();
		config = ctrl.createMock(ConseilLocalConfig.class);
		conseilLocal = new ConseilLocal(config);
		
	}
	
	@Test
	public void testAddCommissionEducative() {
		String sigle = "CE";
		EasyMock.expect(config.getSigleCommissionEducative()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreCommissionEducative(responsableLegal,Engagement.OUI);
		assertEquals(1,conseilLocal.getMembresCommissionEducative().size());
		assertSame(responsableLegal,conseilLocal.getMembresCommissionEducative().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testAddNouveauAdherent() {
	
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		support.replayAll();
		conseilLocal.addNouveauAdherent(responsableLegal);
		assertEquals(1,conseilLocal.getAdherents().size());
		assertSame(responsableLegal,conseilLocal.getAdherents().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielsDesAdherents() {
	
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("responsable@1");
		
		support.replayAll();
		conseilLocal.addNouveauAdherent(responsableLegal);
		assertEquals(1,conseilLocal.getAdherents().size());
		Set<String> expectedCourriels = new HashSet<String>(Arrays.asList("responsable@1"));
		assertEquals(expectedCourriels,conseilLocal.getCourrielsDesAdherents());
		
		support.verifyAll();
	}
	
	@Test
	public void testGetAdherents() {
	
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("responsable@1").anyTimes();
		
		support.replayAll();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		
		assertSame(responsableLegal,conseilLocal.getAdherent("responsable@1"));
		assertNull(conseilLocal.getAdherent("inconnu@bataillon"));
		support.verifyAll();
	}
	
	@Test
	public void testAddMembreConseilAdministration() {
		String sigle = "CA";
		EasyMock.expect(config.getSigleConseilAdministration()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreConseilAdministration(responsableLegal,Engagement.OUI);
		assertEquals(1,conseilLocal.getMembresConseilAdministration().size());
		assertSame(responsableLegal,conseilLocal.getMembresConseilAdministration().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielsDesMembresCommissionEducative() {
		String sigle = "CE";
		EasyMock.expect(config.getSigleCommissionEducative()).andReturn(sigle).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("responsable@1");
		responsableLegal1.estMembreDe(sigle);
		
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn("responsable@2");
		responsableLegal2.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreCommissionEducative(responsableLegal1,Engagement.OUI);
		conseilLocal.addMembreCommissionEducative(responsableLegal2,Engagement.OUI);
		Set<String> expectedCourriels = new HashSet<String>(Arrays.asList("responsable@1", "responsable@2"));
		assertEquals(expectedCourriels,conseilLocal.getCourrielsDesMembresCommissionEducative());
		
		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielsDesMembresConseilAdministration() {
		String sigle = "CA";
		EasyMock.expect(config.getSigleConseilAdministration()).andReturn(sigle).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("responsable@1");
		responsableLegal1.estMembreDe(sigle);
		
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn("responsable@2");
		responsableLegal2.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreConseilAdministration(responsableLegal1,Engagement.OUI);
		conseilLocal.addMembreConseilAdministration(responsableLegal2,Engagement.OUI);
		Set<String> expectedCourriels = new HashSet<String>(Arrays.asList("responsable@1", "responsable@2"));
		assertEquals(expectedCourriels,conseilLocal.getCourrielsDesMembresConseilAdministration());
		
		support.verifyAll();
	}
	
	@Test
	public void testAddMembreBureau() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal,Titre.Membre);
		assertEquals(1,conseilLocal.getMembresBureau().size());
		assertSame(responsableLegal,conseilLocal.getMembresBureau().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetPresident() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal,Titre.Président);
		assertSame(responsableLegal,conseilLocal.getPresident());
		
		support.verifyAll();
	}
	
	@Test
	public void testGetSecretaires() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal,Titre.Secrétaire);
		assertSame(responsableLegal,conseilLocal.getSecretaires().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetTresoriers() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal,Titre.Trésorier);
		assertSame(responsableLegal,conseilLocal.getTresoriers().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetMembresBureau() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		responsableLegal.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal,Titre.Membre);
		assertSame(responsableLegal,conseilLocal.getMembresBureau().get(0));
		
		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielsDesMembresDuBureau() {
		String sigle = "BU";
		EasyMock.expect(config.getSigleBureau()).andReturn(sigle).anyTimes();
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("responsable@1");
		responsableLegal1.estMembreDe(sigle);
		
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn("responsable@2");
		responsableLegal2.estMembreDe(sigle);
		
		support.replayAll();
		conseilLocal.addMembreBureau(responsableLegal1,Titre.Membre);
		conseilLocal.addMembreBureau(responsableLegal2,Titre.Président);
		Set<String> expectedCourriels = new HashSet<String>(Arrays.asList("responsable@1", "responsable@2"));
		assertEquals(expectedCourriels,conseilLocal.getCourrielsDesMembresDuBureau());
		
		support.verifyAll();
	}
	
	@Test
	public void testAddDelegue() {
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		Classe classe =ctrl.createMock(Classe.class);
		support.replayAll();
		conseilLocal.addDelegue(classe, responsableLegal, Engagement.OUI);
		assertSame(responsableLegal,conseilLocal.getDelegues(classe).get(0).getResponsableLegal());
		support.verifyAll();
		
	}
	
	@Test
	public void testAddDelegueWhen2DeleguesForAClassShuouldReturn2() {
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		
		Classe classe =ctrl.createMock(Classe.class);
		support.replayAll();
		conseilLocal.addDelegue(classe, responsableLegal1, Engagement.OUI);
		conseilLocal.addDelegue(classe, responsableLegal2, Engagement.SI_BESOIN);
		assertEquals(2,conseilLocal.getDelegues(classe).size());
		support.verifyAll();
		
	}
	
	@Test
	public void testGetNbMaximumCandidatsDeleguesParClasse() {
		
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		
		Classe classe1 =ctrl.createMock(Classe.class);
		EasyMock.expect(classe1.getNbMinimumCandidatsDelegues()).andReturn(1);
		
		Classe classe2 =ctrl.createMock(Classe.class);
		EasyMock.expect(classe2.getNbMinimumCandidatsDelegues()).andReturn(2);
		
		support.replayAll();
		
		conseilLocal.addDelegue(classe1, responsableLegal1, Engagement.OUI);
		conseilLocal.addDelegue(classe2, responsableLegal2, Engagement.SI_BESOIN);
		
		assertEquals(2,conseilLocal.getNbMaximumCandidatsDeleguesParClasse());
		support.verifyAll();
		
	}
	
}
