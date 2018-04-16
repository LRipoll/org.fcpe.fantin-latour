package org.fcpe.fantinlatour.courriel;

import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.springframework.stereotype.Component;

@Component
public class DeleguesService extends AbstractAnneeScolaireVelocityService {

	public DeleguesService(AnneeScolaire anneeScolaire, ConseilLocalConfig config, AnneeScolaireServiceListener anneeScolaireServiceListener) {
		super(anneeScolaire, anneeScolaireServiceListener, config);
	}

	@Override
	protected String getTemplateName() {
		return "velocity/delegues.vm";
	}
}
