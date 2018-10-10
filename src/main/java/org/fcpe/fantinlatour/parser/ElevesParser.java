package org.fcpe.fantinlatour.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.Eleve;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.fcpe.fantinlatour.model.ResponsableLegalFactory;

public class ElevesParser extends AbstractCSVParser<Eleve> {

	private static final String PRENOM_ELEVE_COLUMN = "Prénom 1";
	private static final String NOM_ELEVE_COLUMN = "Nom de famille";
	private static final String CLASSE_COLUMN = "Division";
	private static final String NOM_RESPONSABLE_LEGAL1_COLUMN = "Nom de famille resp. légal1";
	private static final String PRENOM_RESPONSABLE_LEGAL1_COLUMN = "Prénom resp. légal1";
	private static final String COURRIEL_RESPONSABLE_LEGAL1_COLUMN = "Courriel resp. légal1";
	private static final String NOM_RESPONSABLE_LEGAL2_COLUMN = "Nom de famille resp. légal2";
	private static final String PRENOM_RESPONSABLE_LEGAL2_COLUMN = "Prénom resp. légal2";
	private static final String COURRIEL_RESPONSABLE_LEGAL2_COLUMN = "Courriel resp. légal2";

	private AnneeScolaire anneeScolaire;
	private ResponsableLegalFactory responsableLegalFactory;

	public ElevesParser(AnneeScolaire anneeScolaire, ResponsableLegalFactory responsableLegalFactory) {
		super();
		this.anneeScolaire = anneeScolaire;
		this.responsableLegalFactory = responsableLegalFactory;
	}

	@Override
	protected Eleve parse(CSVRecord record) {
		Eleve result = anneeScolaire.addEleve(record.get(CLASSE_COLUMN), capitalize(record.get(NOM_ELEVE_COLUMN)),
				capitalize(record.get(PRENOM_ELEVE_COLUMN)));

		result.setResponsableLegal1(getResponsableLegal(record, NOM_RESPONSABLE_LEGAL1_COLUMN,
				PRENOM_RESPONSABLE_LEGAL1_COLUMN, COURRIEL_RESPONSABLE_LEGAL1_COLUMN));

		result.setResponsableLegal2(getResponsableLegal(record, NOM_RESPONSABLE_LEGAL2_COLUMN,
				PRENOM_RESPONSABLE_LEGAL2_COLUMN, COURRIEL_RESPONSABLE_LEGAL2_COLUMN));

		return result;
	}

	private ResponsableLegal getResponsableLegal(CSVRecord record, String nomColumn, String prenomColumn,
			String courrielColumn) {

		String nom = capitalize(record.get(nomColumn));

		return responsableLegalFactory.createResponsableLegal(nom, capitalize(record.get(prenomColumn)),
				StringUtils.lowerCase(record.get(courrielColumn)));

	}

	@Override
	protected CSVFormat getCVSFormat() {
		return super.getCVSFormat().withHeader();
	}

	protected char getDelimiter() {
		return ';';
	}
	
	@Override
	protected String getEncoding() {
		
		return "UTF-8";
	}

}
