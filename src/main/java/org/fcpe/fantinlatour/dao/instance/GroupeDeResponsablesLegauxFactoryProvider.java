package org.fcpe.fantinlatour.dao.instance;

import org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory;
import org.fcpe.fantinlatour.model.TypeEnseignement;

public class GroupeDeResponsablesLegauxFactoryProvider {

	public GroupeDeResponsablesLegauxFactory getFactory(TypeEnseignement typeEnseignement) {
		GroupeDeResponsablesLegauxFactory result = null;

		switch (typeEnseignement) {
		case ELEMENTAIRE:
			result = new GroupeDeResponsablesLegauxElementaireFactory();
			break;
		case SECONDAIRE:
			result = new GroupeDeResponsablesLegauxSecondaireFactory();
			break;
		}
		return result;
	}

}
