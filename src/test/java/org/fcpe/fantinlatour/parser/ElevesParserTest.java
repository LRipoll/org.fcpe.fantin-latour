package org.fcpe.fantinlatour.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.Eleve;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.fcpe.fantinlatour.model.ResponsableLegalFactory;
import org.junit.Before;
import org.junit.Test;

public class ElevesParserTest {

	private static final String HEADER = "Nom de famille;Prénom 1;Division;Nom de famille resp. légal1;Prénom resp. légal1;Courriel resp. légal1;Nom de famille resp. légal2;Prénom resp. légal2;Courriel resp. légal2";
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	private ElevesParser elevesParser;
	private AnneeScolaire anneeScolaire;
	private ResponsableLegalFactory responsableLegalFactory;

	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		responsableLegalFactory = ctrl.createMock(ResponsableLegalFactory.class);

		elevesParser = new ElevesParser(anneeScolaire, responsableLegalFactory);

	}

	@Test
	public void testParseWhenNoDataThenReturnEmptyList() throws IOException {
		String input = "";
		Reader reader = new StringReader(input);
		List<Eleve> expectedList = new ArrayList<Eleve>();
		assertEquals(expectedList, elevesParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderThenReturnEmptyList() throws IOException {
		String input = HEADER;

		Reader reader = new StringReader(input);
		List<Eleve> expectedList = new ArrayList<Eleve>();
		assertEquals(expectedList, elevesParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderAndAnIgnoredLineBeforeThenReturnEmptyList() throws IOException {
		String input = "Ignore\nIgnore\n" + HEADER;

		Reader reader = new StringReader(input);
		try {
			elevesParser.parse(reader);
			fail();
		} catch (IllegalArgumentException anIllegalArgumentException) {
			assertEquals("Mapping for Division not found, expected one of [Ignore]",
					anIllegalArgumentException.getMessage());
		}
	}

	@Test
	public void testParseWhenARowHasBeenDefinedThenReturnCourriel() throws IOException {
		String input = HEADER + "\n"
				+ "NOM_ELEVE1;PRENOM_ELEVE1;63;NOM_RESP1;PRENOM_RESP1;;NOM_RESP2;PRENOM_RESP2;RESP@MAIL" + "\n"
				+ "NOM_ELEVE2;PRENOM_ELEVE2;55;NOM_RESP3;PRENOM_RESP3;RESP3@MAIL;;;;" + "\n";

		Eleve eleve1 = ctrl.createMock(Eleve.class);

		EasyMock.expect(anneeScolaire.addEleve("63", "Nom_eleve1", "Prenom_eleve1")).andReturn(eleve1);

		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegalFactory.createResponsableLegal("Nom_resp1", "Prenom_resp1", ""))
				.andReturn(responsableLegal1);
		eleve1.setResponsableLegal1(responsableLegal1);

		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegalFactory.createResponsableLegal("Nom_resp2", "Prenom_resp2", "resp@mail"))
				.andReturn(responsableLegal2);
		eleve1.setResponsableLegal2(responsableLegal2);

		Eleve eleve2 = ctrl.createMock(Eleve.class);
		EasyMock.expect(anneeScolaire.addEleve("55", "Nom_eleve2", "Prenom_eleve2")).andReturn(eleve2);

		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(responsableLegalFactory.createResponsableLegal("Nom_resp3", "Prenom_resp3", "resp3@mail"))
				.andReturn(responsableLegal3);
		eleve2.setResponsableLegal1(responsableLegal3);

		EasyMock.expect(responsableLegalFactory.createResponsableLegal("", "", "")).andReturn(null);
		eleve2.setResponsableLegal2(null);

		Reader reader = new StringReader(input);
		support.replayAll();

		List<Eleve> expectedList = new ArrayList<Eleve>();
		expectedList.add(eleve1);
		expectedList.add(eleve2);
		assertEquals(expectedList, elevesParser.parse(reader));

		support.verifyAll();
	}
}
