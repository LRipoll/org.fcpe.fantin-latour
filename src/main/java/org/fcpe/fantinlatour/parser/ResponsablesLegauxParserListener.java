package org.fcpe.fantinlatour.parser;

import org.apache.log4j.Logger;
import org.fcpe.fantinlatour.model.ResponsableLegal;

public class ResponsablesLegauxParserListener {
	private static final Logger logger = Logger.getLogger(ResponsablesLegauxParserListener.class);

	public void responsableLegalInconnu(String prenom, String nom, String classes) {
		logger.error(
				String.format("Le responsable légal suivant %s %s n'a pas été trouvé dans les classes suivantes : %s",
						prenom, nom, classes));

	}

	public void responsableLegalSansCourriel(ResponsableLegal result) {
		logger.error(String.format("%s souhaite s'engager mais n'a pas de courriel defini", result.toString()));

	}

}
