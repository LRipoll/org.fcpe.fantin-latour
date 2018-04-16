package org.fcpe.fantinlatour.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

public class GoogleGroupParser extends AbstractCSVParser<String> implements GroupeDeDiscussionParser {

	private static final String COURRIEL_COLUMN = "Adresse e-mail";

	@Override
	protected CSVFormat getCVSFormat() {
		CSVFormat result = super.getCVSFormat().withHeader(COURRIEL_COLUMN, "Alias", "État du groupe",
				"Statut d'e-mail", "Préférences relatives aux e-mails :", "Droits de publication", "Année d'abonnement",
				"Mois d'abonnement", "Jour d'abonnement", "Heure d'abonnement", "Minute d'abonnement",
				"Seconde d'abonnement", "Fuseau horaire").withSkipHeaderRecord(true);
		return result;
	}

	public GoogleGroupParser() {
		super();

	}

	@Override
	protected char getDelimiter() {
		return ',';
	}

	@Override
	protected String parse(CSVRecord record) {
		String result = null;
		String courriel = record.get(COURRIEL_COLUMN);
		if (courriel.contains("@")) {
			result = StringUtils.lowerCase(StringUtils.trim(courriel));
		}

		return result;

	}

}
