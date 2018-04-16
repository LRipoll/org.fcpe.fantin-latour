package org.fcpe.fantinlatour.dao.instance;

import java.util.List;

import org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory;
import org.fcpe.fantinlatour.model.EntiteRacine;

public class EntitesRacineSecondaireFactory extends AbstractEntitesRacineFactory {

	public EntitesRacineSecondaireFactory(GroupeDeResponsablesLegauxFactory groupeDeResponsablesLegauxFactory) {
		super(groupeDeResponsablesLegauxFactory);
	}

	@Override
	public List<EntiteRacine> create() {
		List<EntiteRacine> result = super.create();

		EntiteRacine delegues = new EntiteRacine("Délégués", PRIORITE_DELEGUES);
		result.add(delegues);

		return result;
	}

}
