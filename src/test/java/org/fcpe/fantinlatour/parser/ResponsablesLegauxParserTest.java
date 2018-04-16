package org.fcpe.fantinlatour.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocal;
import org.fcpe.fantinlatour.model.Delegue;
import org.fcpe.fantinlatour.model.Engagement;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.fcpe.fantinlatour.model.Titre;
import org.junit.Before;
import org.junit.Test;

public class ResponsablesLegauxParserTest {
	
	private static final String HEADER = "Timestamp;Votre nom;Votre prénom;Dans quelle(s) classe(s) sont inscrits vos enfants ?;Souhaitez vous adhérez au conseil local FCPE ?;Souhaitez vous participer au conseil d'administration du collège ?;Souhaitez vous être parent délégué des classes suivantes ? * [6ème1];Souhaitez vous être parent délégué des classes suivantes ? * [6ème2];Souhaitez vous être parent délégué des classes suivantes ? * [6ème3];Souhaitez vous être parent délégué des classes suivantes ? * [6ème4];Souhaitez vous être parent délégué des classes suivantes ? * [6ème5];Souhaitez vous être parent délégué des classes suivantes ? * [6ème6];Souhaitez vous être parent délégué des classes suivantes ? * [5ème1];Souhaitez vous être parent délégué des classes suivantes ? * [5ème2];Souhaitez vous être parent délégué des classes suivantes ? * [5ème3];Souhaitez vous être parent délégué des classes suivantes ? * [5ème4];Souhaitez vous être parent délégué des classes suivantes ? * [5ème5];Souhaitez vous être parent délégué des classes suivantes ? * [5ème6];Souhaitez vous être parent délégué des classes suivantes ? * [4ème1];Souhaitez vous être parent délégué des classes suivantes ? * [4ème2];Souhaitez vous être parent délégué des classes suivantes ? * [4ème3];Souhaitez vous être parent délégué des classes suivantes ? * [4ème4];Souhaitez vous être parent délégué des classes suivantes ? * [4ème5];Souhaitez vous être parent délégué des classes suivantes ? * [4ème6];Souhaitez vous être parent délégué des classes suivantes ? * [3ème1];Souhaitez vous être parent délégué des classes suivantes ? * [3ème2];Souhaitez vous être parent délégué des classes suivantes ? * [3ème3];Souhaitez vous être parent délégué des classes suivantes ? * [3ème4];Souhaitez vous être parent délégué des classes suivantes ? * [3ème5];Souhaitez vous être parent délégué des classes suivantes ? * [3ème dérog];Souhaitez vous être parent délégué des classes suivantes ? * [UPE2A];Souhaitez vous participer à la commission éducative ?;Commentaires\n";  
			
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	
	private ResponsablesLegauxParser responsablesLegauxParser;
	private ResponsablesLegauxParserListener responsablesLegauxParserListener;
	private AnneeScolaire anneeScolaire;
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		responsablesLegauxParserListener  = ctrl.createMock(ResponsablesLegauxParserListener.class);
		responsablesLegauxParser = new ResponsablesLegauxParser(anneeScolaire, responsablesLegauxParserListener);

	}
	
	@Test
	public void testParseWhenNoDataThenReturnEmptyList() throws IOException {
		String input = "";
		Reader reader = new StringReader(input);
		List<ResponsableLegal> expectedList = new ArrayList<ResponsableLegal>();
		assertEquals(expectedList, responsablesLegauxParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderThenReturnEmptyList() throws IOException {
		String input = HEADER;

		Reader reader = new StringReader(input);
		List<ResponsableLegal> expectedList = new ArrayList<ResponsableLegal>();
		assertEquals(expectedList, responsablesLegauxParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderAndAnIgnoredLineBeforeThenReturnEmptyList() throws IOException {
		String input = "Ignore\nIgnore\n" + HEADER;

		Reader reader = new StringReader(input);
		try {
			responsablesLegauxParser.parse(reader);
			fail();
		} catch (IllegalArgumentException anIllegalArgumentException) {
			assertEquals("Mapping for Dans quelle(s) classe(s) sont inscrits vos enfants ? not found, expected one of [Ignore]",
					anIllegalArgumentException.getMessage());
		}
	}
	
	@Test
	public void testParseWhenTheResponsableLegalIsUnknownShouldCallListener() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;Oui;;;;Oui;;;;;;;;;;;;;;;;;;;;;;Oui;";

		Reader reader = new StringReader(input);
		
		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(null);
		responsablesLegauxParserListener.responsableLegalInconnu("John", "Doe", "6ème4");
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(0,responsablesLegaux.size());
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalIsKnownButNotCourrielShouldCallListener() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		
		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("");
		
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		responsablesLegauxParserListener.responsableLegalSansCourriel(responsableLegal);
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalHasMailShouldBeAddedAsNouveauAdherent() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal);
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalWantToBeCAMemberShouldBeAddedAsCAMember() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;Oui;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		conseilLocal.addMembreConseilAdministration(responsableLegal, Engagement.OUI);
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalWantToBeCASiBesoinMemberShouldBeAddedAsCAMember() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;Si besoin;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		conseilLocal.addMembreConseilAdministration(responsableLegal, Engagement.SI_BESOIN);
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalWantToBeCESiBesoinMemberShouldBeAddedAsCEMember() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Oui d'ailleurs notre famille a déjà transmis le bulletin d'adhésion et a réglé sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;Oui;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		conseilLocal.addMembreCommissionEducative(responsableLegal, Engagement.OUI);
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}

	@Test
	public void testParseWhenTheResponsableLegalWantToBeMembreDuBureauSiBesoinMemberShouldBeAddedAsCEMembert() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème4;Président;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		conseilLocal.addMembreBureau(responsableLegal, Titre.Président);
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalWantToBeDelegueOfAClasseShouldBeAddedAsDelegue() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6ème6;Oui mais nous n'avons pas encore fait de démarche en ce sens cette année;;;;;;;Oui;;;;;;;;;;;;;;;;;;;;Non;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6ème6", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		Classe classe = ctrl.createMock(Classe.class);
		EasyMock.expect(classe.getNiveau()).andReturn("6");
		EasyMock.expect(classe.getSection()).andReturn("6");
		
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.singletonList(classe));
		Delegue delegue = ctrl.createMock(Delegue.class);
		
		EasyMock.expect(conseilLocal.addDelegue(classe, responsableLegal, Engagement.OUI)).andReturn(delegue);
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	
}
