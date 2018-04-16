package org.fcpe.fantinlatour.courriel;

import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;

public class AnneeScolaireVelocityTestService extends AbstractAnneeScolaireVelocityService {

	private String templateName;

	public AnneeScolaireVelocityTestService(AnneeScolaire anneeScolaire, AnneeScolaireServiceListener anneeScolaireServiceListener,
			ConseilLocalConfig config, String templateName) {
		super(anneeScolaire, anneeScolaireServiceListener, config);
		this.templateName = templateName;
	}

	@Override
	protected String getTemplateName() {

		return this.templateName;
	}

}
