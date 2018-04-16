package org.fcpe.fantinlatour.dao.instance;

import java.util.ArrayList;
import java.util.List;

import org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory;
import org.fcpe.fantinlatour.model.EntiteRacine;
import org.fcpe.fantinlatour.model.EntitesRacineFactory;

public abstract class AbstractEntitesRacineFactory implements EntitesRacineFactory {

	private GroupeDeResponsablesLegauxFactory groupeDeResponsablesLegauxFactory;

	public AbstractEntitesRacineFactory(GroupeDeResponsablesLegauxFactory groupeDeResponsablesLegauxFactory) {
		this.groupeDeResponsablesLegauxFactory =groupeDeResponsablesLegauxFactory;
	}

	@Override
	public List<EntiteRacine> create() {
		List<EntiteRacine> result = new ArrayList<EntiteRacine>();
		
		EntiteRacine conseiLocal = new EntiteRacine("Conseil local",PRIORITE_CONSEIL_LOCAL);
		conseiLocal.addGroupes(groupeDeResponsablesLegauxFactory.create());
		
		result.add(conseiLocal);
		EntiteRacine parents = new EntiteRacine("Parents",PRIORITE_PARENTS);
		result.add(parents);
		
		return result;
		
	}

}
