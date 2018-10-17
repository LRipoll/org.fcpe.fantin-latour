package org.fcpe.fantinlatour.parser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GoogleGroupParserTest {

	private static final String HEADER = "Adresse e-mail,Alias,État du groupe,Statut d'e-mail,Préférences relatives aux e-mails :,Droits de publication,Année d'abonnement,Mois d'abonnement,Jour d'abonnement,Heure d'abonnement,Minute d'abonnement,Seconde d'abonnement,Fuseau horaire";
	private GoogleGroupParser googleGroupParser;

	@Before
	public void setup() {

		googleGroupParser = new GoogleGroupParser();

	}

	@Test
	public void testParseWhenNoDataThenReturnEmptyList() throws IOException {
		String input = "";
		Reader reader = new StringReader(input);
		List<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, googleGroupParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderThenReturnEmptyList() throws IOException {
		String input = HEADER;

		Reader reader = new StringReader(input);
		List<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, googleGroupParser.parse(reader));
	}

	@Test
	public void testParseWhenOnlyHeaderAndAnIgnoredLineBeforeThenReturnEmptyList() throws IOException {
		String input = "Ignore\nIgnore\nIgnore\n" + HEADER;

		Reader reader = new StringReader(input);
		List<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, googleGroupParser.parse(reader));
	}
	
	@Test
	public void testParseWhenARowHasBeenDefinedButExcluThenReturnCourriel() throws IOException {
		String input = HEADER + "\n"
				+ "A.MAIL@YELLOW.NET,AMAIL,exclu,,e-mail,autorisé,2016,11,4,22,9,7,Heure d'été d'Europe centrale";

		Reader reader = new StringReader(input);
		List<String> expectedList = new ArrayList<String>();
		assertEquals(expectedList, googleGroupParser.parse(reader));
	}

	@Test
	public void testParseWhenARowHasBeenDefinedThenReturnCourriel() throws IOException {
		String input = HEADER + "\n"
				+ "A.MAIL@YELLOW.NET,AMAIL,membre,,e-mail,autorisé,2016,11,4,22,9,7,Heure d'été d'Europe centrale";

		Reader reader = new StringReader(input);
		List<String> expectedList = new ArrayList<String>();
		expectedList.add("a.mail@yellow.net");
		assertEquals(expectedList, googleGroupParser.parse(reader));
	}

}
