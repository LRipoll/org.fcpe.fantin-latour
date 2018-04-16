package org.fcpe.fantinlatour.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ResponsableLegal;

public class AdhesionsParser extends AbstractCSVParser<ResponsableLegal> {

	private static final String COURRIEL_RESPONSABLE_LEGAL_COLUMN = "Courriel";
	private static final String ADHESION_COLUMN = "Adhesion";
	private static final String MONTANT_COLUMN = "Montant";

	private AnneeScolaire anneeScolaire;
	
	
	public AdhesionsParser(AnneeScolaire anneeScolaire) {
		super();
		this.anneeScolaire = anneeScolaire;
	}

	@Override
	protected ResponsableLegal parse(CSVRecord record) {
		String courriel = StringUtils.lowerCase(record.get(COURRIEL_RESPONSABLE_LEGAL_COLUMN));
		ResponsableLegal adherent = anneeScolaire.getConseilLocal().getAdherent(courriel);
		if (adherent != null) {
			if (record.isSet(ADHESION_COLUMN)) {
				adherent.setAdhesionTransmise(record.get(ADHESION_COLUMN).equals("1"));
			}
			if (record.isSet(MONTANT_COLUMN) && record.get(MONTANT_COLUMN).length() > 0) {
				adherent.setMontantAdhesion(Float.parseFloat(record.get(MONTANT_COLUMN).replaceAll(",", ".")));
			}
		} else {
			System.err.println(String.format("Aucun adherent trouvé avec ce courriel : %s", courriel));
		}
		return adherent;
	}

	@Override
	protected CSVFormat getCVSFormat() {
		return super.getCVSFormat().withHeader();
	}



}
