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
	
	private static final String HEADER = "Timestamp;Votre nom;Votre pr�nom;Dans quelle(s) classe(s) sont inscrits vos enfants ?;Souhaitez vous adh�rez au conseil local FCPE ?;Souhaitez vous participer au conseil d'administration du coll�ge ?;Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me1];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me2];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me3];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me4];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me5];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [6�me6];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me1];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me2];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me3];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me4];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me5];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [5�me6];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me1];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me2];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me3];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me4];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me5];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [4�me6];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me1];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me2];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me3];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me4];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me5];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [3�me d�rog];Souhaitez vous �tre parent d�l�gu� des classes suivantes ? * [UPE2A];Souhaitez vous participer � la commission �ducative ?;Commentaires\n";  
			
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
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;Oui;;;;Oui;;;;;;;;;;;;;;;;;;;;;;Oui;";

		Reader reader = new StringReader(input);
		
		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(null);
		responsablesLegauxParserListener.responsableLegalInconnu("John", "Doe", "6�me4");
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(0,responsablesLegaux.size());
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalIsKnownButNotCourrielShouldCallListener() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		
		
		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
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
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
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
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;Oui;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
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
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;Si besoin;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
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
				"08/09/17 08:47;Doe;John;6�me4;Oui d'ailleurs notre famille a d�j� transmis le bulletin d'adh�sion et a r�gl� sa cotisation;;;;;;;;;;;;;;;;;;;;;;;;;;;Oui;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
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
				"08/09/17 08:47;Doe;John;6�me4;Pr�sident;;;;;;;;;;;;;;;;;;;;;;;;;;;;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me4", "Doe", "John")).andReturn(responsableLegal);
		EasyMock.expect(responsableLegal.getCourriel()).andReturn("courriel@mail");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		conseilLocal.addNouveauAdherent(responsableLegal);
		
		conseilLocal.addMembreBureau(responsableLegal, Titre.Pr�sident);
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Collections.emptyList());
		
		support.replayAll();
			
		List<ResponsableLegal> responsablesLegaux = responsablesLegauxParser.parse(reader);
		assertEquals(responsableLegal,responsablesLegaux.get(0));
		support.verifyAll();
		
	}
	
	@Test
	public void testParseWhenTheResponsableLegalWantToBeDelegueOfAClasseShouldBeAddedAsDelegue() throws IOException {
		String input = HEADER+"\n"+
				"08/09/17 08:47;Doe;John;6�me6;Oui mais nous n'avons pas encore fait de d�marche en ce sens cette ann�e;;;;;;;Oui;;;;;;;;;;;;;;;;;;;;Non;";

		Reader reader = new StringReader(input);
		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);
		

		EasyMock.expect(anneeScolaire.getResponsableLegal("6�me6", "Doe", "John")).andReturn(responsableLegal);
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
