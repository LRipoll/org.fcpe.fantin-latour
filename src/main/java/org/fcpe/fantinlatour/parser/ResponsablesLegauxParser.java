package org.fcpe.fantinlatour.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.Engagement;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.fcpe.fantinlatour.model.Titre;

public class ResponsablesLegauxParser extends AbstractCSVParser<ResponsableLegal> {

	private static final String PRENOM_RESPONSABLE_LEGAL_COLUMN = "Votre prénom";
	private static final String NOM_RESPONSABLE_LEGAL_COLUMN = "Votre nom";
	private static final String CLASSE_COLUMN = "Dans quelle(s) classe(s) sont inscrits vos enfants ?";
	private static final String DELEGUES_COLUMN = "Souhaitez vous être parent délégué des classes suivantes ? * [%sème%s]";
	private static final String CA_COLUMN = "Souhaitez vous participer au conseil d'administration du collège ?";
	private static final String COMMISSION_EDUCATIVE_COLUMN = "Souhaitez vous participer à la commission éducative ?";
	private static final String MEMBRE_BUREAU_COLUMN = "Souhaitez vous adhérez au conseil local FCPE ?";

	private AnneeScolaire anneeScolaire;
	private ResponsablesLegauxParserListener responsablesLegauxParserListener;

	public ResponsablesLegauxParser(AnneeScolaire anneeScolaire, ResponsablesLegauxParserListener responsablesLegauxParserListener) {
		super();
		this.anneeScolaire = anneeScolaire;
		this.responsablesLegauxParserListener  = responsablesLegauxParserListener;
	}

	@Override
	protected ResponsableLegal parse(CSVRecord record) {
		String classes = record.get(CLASSE_COLUMN);
		String nom = capitalize(record.get(NOM_RESPONSABLE_LEGAL_COLUMN));
		String prenom = capitalize(record.get(PRENOM_RESPONSABLE_LEGAL_COLUMN));
		ResponsableLegal result = anneeScolaire.getResponsableLegal(classes, nom, prenom);
		if (result == null) {
			responsablesLegauxParserListener.responsableLegalInconnu(prenom,
					nom, classes);
			
		} else {
			if (result.getCourriel().length() == 0) {
				responsablesLegauxParserListener.responsableLegalSansCourriel(result);
				
			} else {
				anneeScolaire.getConseilLocal().addNouveauAdherent(result);
			}

			Engagement engagementCA = Engagement.parse(record.get(CA_COLUMN));
			if (engagementCA != Engagement.NON) {
				anneeScolaire.getConseilLocal().addMembreConseilAdministration(result, engagementCA);
			}

			Titre membreBureau = Titre.parse(record.get(MEMBRE_BUREAU_COLUMN));
			if (membreBureau != null) {
				anneeScolaire.getConseilLocal().addMembreBureau(result, membreBureau);
			}

			Engagement engagementCommissionEducative = Engagement.parse(record.get(COMMISSION_EDUCATIVE_COLUMN));
			if (engagementCommissionEducative != Engagement.NON) {
				anneeScolaire.getConseilLocal().addMembreCommissionEducative(result, engagementCommissionEducative);
			}

			for (Classe classe : anneeScolaire.getClasses()) {
				String delegueColumn = String.format(DELEGUES_COLUMN, classe.getNiveau(), classe.getSection());

				Engagement engagementDelegue = Engagement.parse(record.get(delegueColumn));
				if (engagementDelegue != Engagement.NON) {

					anneeScolaire.getConseilLocal().addDelegue(classe, result, engagementDelegue);

				}

			}

		}
		return result;

	}

	

	@Override
	protected CSVFormat getCVSFormat() {
		return super.getCVSFormat().withHeader();
	}

}
