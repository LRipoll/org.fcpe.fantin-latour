package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.ClasseFactory;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.fcpe.fantinlatour.model.Eleve;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.Before;
import org.junit.Test;


public class CollegeTest {
	private EasyMockSupport support = new EasyMockSupport();

	private ClasseFactory classeFactory;
	private Calendar calendar;
	private AnneeScolaire anneeScolaire;
	private ConseilLocalConfig config;

	private IMocksControl ctrl;

	@Before
	public void setup() {
		ctrl = support.createControl();
		classeFactory = ctrl.createMock(ClasseFactory.class);
		calendar = ctrl.createMock(Calendar.class);
		config = ctrl.createMock(ConseilLocalConfig.class);

		anneeScolaire = new AnneeScolaire(classeFactory, calendar,config);
	}

	@Test
	public void testConstructor() {

		support.replayAll();

		assertNotNull(anneeScolaire.getConseilLocal());

		support.verifyAll();
	}

	@Test
	public void testGetAnneeScolaire() {

		EasyMock.expect(calendar.get(Calendar.YEAR)).andReturn(2017);
		support.replayAll();

		assertEquals("2017-2018", anneeScolaire.getAnneeScolaire());

		support.verifyAll();
	}

	@Test
	public void testGetClasseWhenItsTheFirstTimeShouldReturnANewClasse() {

		Classe classe = ctrl.createMock(Classe.class);
		String nom = "Test";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, nom,config)).andReturn(classe);

		support.replayAll();

		assertSame(classe, anneeScolaire.getClasse(nom));

		support.verifyAll();
	}

	@Test
	public void testGetResponsableLegalWhenTheFirstClasseDefinesTheResponsableShouldReturnThisOne() {

		Classe classe = ctrl.createMock(Classe.class);
		String section = "6ème4";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "64",config)).andReturn(classe);

		String nom = "Nom";
		String prenom = "Prenom";

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);

		EasyMock.expect(classe.getResponsableLegal(nom, prenom)).andReturn(responsableLegal);

		support.replayAll();

		assertSame(responsableLegal, anneeScolaire.getResponsableLegal(section, nom, prenom));

		support.verifyAll();
	}

	@Test
	public void testGetResponsableLegalWhenTheSecondClasseDefinesTheResponsableShouldReturnThisOne() {

		Classe classe64 = ctrl.createMock(Classe.class);
		Classe classe52 = ctrl.createMock(Classe.class);
		String section = "5ème2,6ème4";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "52", config)).andReturn(classe52);
		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "64", config)).andReturn(classe64);

		String nom = "Nom";
		String prenom = "Prenom";

		ResponsableLegal responsableLegal = ctrl.createMock(ResponsableLegal.class);

		EasyMock.expect(classe52.getResponsableLegal(nom, prenom)).andReturn(null);
		EasyMock.expect(classe64.getResponsableLegal(nom, prenom)).andReturn(responsableLegal);

		support.replayAll();

		assertSame(responsableLegal, anneeScolaire.getResponsableLegal(section, nom, prenom));

		support.verifyAll();
	}

	@Test
	public void testGetResponsableLegalWhenNoResponsableLegalIsDefinedTheResponsableShouldReturnNull() {

		Classe classe64 = ctrl.createMock(Classe.class);
		Classe classe52 = ctrl.createMock(Classe.class);
		String section = "5ème2,6ème4";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "52", config)).andReturn(classe52);
		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "64", config)).andReturn(classe64);

		String nom = "Nom";
		String prenom = "Prenom";

		EasyMock.expect(classe52.getResponsableLegal(nom, prenom)).andReturn(null);
		EasyMock.expect(classe64.getResponsableLegal(nom, prenom)).andReturn(null);

		support.replayAll();

		assertNull(anneeScolaire.getResponsableLegal(section, nom, prenom));

		support.verifyAll();
	}

	@Test
	public void testGetClasseSansCandidatWhenNoClasseIsDefineShouldReturnEmpty() {

		support.replayAll();

		assertEquals("", anneeScolaire.getClassesSansCandidat());

		support.verifyAll();
	}
	
	@Test
	public void testGetClasseAvec1CandidatWhenNoClasseIsDefineShouldReturnEmpty() {

		support.replayAll();

		assertEquals("", anneeScolaire.getClassesAvecUnSeulCandidat());

		support.verifyAll();
	}
	
	@Test
	public void testGetClassesAvecTropDeCandidatsWhenNoClasseIsDefineShouldReturnEmpty() {

		support.replayAll();

		assertEquals("", anneeScolaire.getClassesAvecTropDeCandidats());

		support.verifyAll();
	}
	

	@Test
	public void testGetClasseSansCandidatWhenAClasseIsDefineWithADelegueShouldReturnEmpty() {

		createClasseWithDeleguesVolontaireInattendus(1);
		assertEquals("", anneeScolaire.getClassesSansCandidat());

		support.verifyAll();
	}

	@Test
	public void testGetClasseAvec1CandidatWhenAClasseIsDefineWithNoDelegueShouldReturnEmpty() {

		createClasseWithDeleguesVolontaireInattendus(2);
		assertEquals("", anneeScolaire.getClassesAvecUnSeulCandidat());

		support.verifyAll();
	}
	
	@Test
	public void testGetClassesAvecTropDeCandidatsWhenAClasseIsDefineWithNoDelegueShouldReturnEmpty() {
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		createClasseWithDeleguesVolontaireInattendus(4);
		assertEquals("", anneeScolaire.getClassesAvecTropDeCandidats());

		support.verifyAll();
	}

	@Test
	public void testGetClasseSansCandidatWhenAClasseIsDefineWithNoDelegueShouldReturnThisOne() {

		String section = createClasseWithDeleguesVolontaireAttendus(0);
		assertEquals(section, anneeScolaire.getClassesSansCandidat());

		support.verifyAll();
	}

	@Test
	public void testGetClasseSansCandidatWhenAClasseIsDefineWithOnlyOneDelegueShouldReturnThisOne() {

		String section = createClasseWithDeleguesVolontaireAttendus(1);
		assertEquals(section, anneeScolaire.getClassesAvecUnSeulCandidat());

		support.verifyAll();
	}
	
	@Test
	public void testGetClassesAvecTropDeCandidatsWhenAClasseIsDefineWith5DelegueShouldReturnThisOne() {
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		String section = createClasseWithDeleguesVolontaireAttendus(5);
		assertEquals(section, anneeScolaire.getClassesAvecTropDeCandidats());

		support.verifyAll();
	}

	@Test
	public void testGetClasseSansCandidatWhenTwoClasseIsDefineWithNoDelegueShouldReturnTheseClasses() {

		createTwoClassesWithDeleguesVolontaireAttendus(0);
		assertEquals("6ème1, 6ème4", anneeScolaire.getClassesSansCandidat());

		support.verifyAll();
	}

	@Test
	public void testGetClasseSansCandidatWhenTwoClasseIsDefineWithOnlyOneDelegueShouldReturnTheseClasses() {

		createTwoClassesWithDeleguesVolontaireAttendus(1);
		assertEquals("6ème1, 6ème4", anneeScolaire.getClassesAvecUnSeulCandidat());

		support.verifyAll();
	}
	
	@Test
	public void testGetClassesAvecTropDeCandidatsWhenTwoClasseIsDefineWith5DelegueShouldReturnTheseClasses() {
		EasyMock.expect(config.getNombreMaximumDeleguesParClasse()).andReturn(4).anyTimes();
		createTwoClassesWithDeleguesVolontaireAttendus(5);
		assertEquals("6ème1, 6ème4", anneeScolaire.getClassesAvecTropDeCandidats());

		support.verifyAll();
	}

	private void createClasseWithDeleguesVolontaireInattendus(int nbDeleguesVolontaires) {
		Classe classe = ctrl.createMock(Classe.class);
		String section = "6ème4";
		EasyMock.expect(classeFactory.createClasse(anneeScolaire, section, config)).andReturn(classe);
		EasyMock.expect(classe.getNbCandidatsDeleguesAffirm�s()).andReturn(nbDeleguesVolontaires);
		support.replayAll();
		anneeScolaire.getClasse(section);
	}

	private String createClasseWithDeleguesVolontaireAttendus(int nbDeleguesVolontaires) {
		Classe classe = ctrl.createMock(Classe.class);
		String section = "6ème4";
		EasyMock.expect(classeFactory.createClasse(anneeScolaire, section, config)).andReturn(classe);

		EasyMock.expect(classe.getNbCandidatsDeleguesAffirm�s()).andReturn(nbDeleguesVolontaires);
		EasyMock.expect(classe.getNomComplet()).andReturn(section);
		support.replayAll();
		anneeScolaire.getClasse(section);
		return section;
	}

	private void createTwoClassesWithDeleguesVolontaireAttendus(int nbDeleguesVolontaires) {
		Classe uneClasse = ctrl.createMock(Classe.class);
		String uneSection = "6ème4";

		Classe uneAutreClasse = ctrl.createMock(Classe.class);
		String uneAutreSection = "6ème1";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, uneSection, config)).andReturn(uneClasse);
		EasyMock.expect(uneClasse.getNiveau()).andReturn("6").anyTimes();
		EasyMock.expect(uneClasse.getSection()).andReturn("4").anyTimes();

		EasyMock.expect(uneClasse.getNbCandidatsDeleguesAffirm�s()).andReturn(nbDeleguesVolontaires);
		EasyMock.expect(uneClasse.getNomComplet()).andReturn(uneSection);

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, uneAutreSection, config)).andReturn(uneAutreClasse);
		EasyMock.expect(uneAutreClasse.getNiveau()).andReturn("6").anyTimes();
		EasyMock.expect(uneAutreClasse.getSection()).andReturn("1").anyTimes();
		EasyMock.expect(uneAutreClasse.getNbCandidatsDeleguesAffirm�s()).andReturn(nbDeleguesVolontaires);
		EasyMock.expect(uneAutreClasse.getNomComplet()).andReturn(uneAutreSection);

		support.replayAll();

		anneeScolaire.getClasse(uneSection);
		anneeScolaire.getClasse(uneAutreSection);
	}

	@Test
	public void testAddEleve() {

		Classe classe = ctrl.createMock(Classe.class);
		String section = "Test";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, section, config)).andReturn(classe);

		String nom = "Nom";
		String prenom = "Prenom";
		Eleve eleve = ctrl.createMock(Eleve.class);
		EasyMock.expect(classe.addEleve(nom, prenom)).andReturn(eleve);

		support.replayAll();

		assertSame(eleve, anneeScolaire.addEleve(section, nom, prenom));

		support.verifyAll();
	}

	@Test
	public void testGetClasseWhenItsTheSecondTimeShouldReturnTheSameClasse() {

		Classe classe = ctrl.createMock(Classe.class);
		String nom = "Test";

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, nom, config)).andReturn(classe);

		support.replayAll();

		assertSame(classe, anneeScolaire.getClasse(nom));
		assertSame(classe, anneeScolaire.getClasse(nom));

		support.verifyAll();
	}
	
	@Test
	public void testGetCourrielNouveauxResponsablesLegaux() {

		Classe classe64 = ctrl.createMock(Classe.class);
		Classe classe52 = ctrl.createMock(Classe.class);
	

		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "52", config)).andReturn(classe52);
		EasyMock.expect(classeFactory.createClasse(anneeScolaire, "64", config)).andReturn(classe64);

	
		

		EasyMock.expect(classe52.getNiveau()).andReturn("5");
		EasyMock.expect(classe64.getNiveau()).andReturn("6");

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(classe52.getResponsablesLegaux()).andReturn(Arrays.asList(responsableLegal1));
		EasyMock.expect(responsableLegal1.getCourriel()).andReturn("a");
		
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(classe64.getResponsablesLegaux()).andReturn(Arrays.asList(responsableLegal2,responsableLegal3));
		EasyMock.expect(responsableLegal2.getCourriel()).andReturn("a");
		EasyMock.expect(responsableLegal3.getCourriel()).andReturn("b");
		
		support.replayAll();
		anneeScolaire.getClasse("52");
		anneeScolaire.getClasse("64");
		Set<String> expected = new HashSet<String>(Arrays.asList("a", "b"));
		assertEquals(expected, anneeScolaire.getCourrielNouveauxResponsablesLegaux());

		support.verifyAll();
	}
}
