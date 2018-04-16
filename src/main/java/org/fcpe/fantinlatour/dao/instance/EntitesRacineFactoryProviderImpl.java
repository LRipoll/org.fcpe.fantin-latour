package org.fcpe.fantinlatour.dao.instance;

import org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory;
import org.fcpe.fantinlatour.model.EntitesRacineFactory;
import org.fcpe.fantinlatour.model.EntitesRacineFactoryProvider;
import org.fcpe.fantinlatour.model.TypeEnseignement;

public class EntitesRacineFactoryProviderImpl implements EntitesRacineFactoryProvider {

	private GroupeDeResponsablesLegauxFactoryProvider groupeDeResponsablesLegauxFactoryProvider;

	public EntitesRacineFactoryProviderImpl(
			GroupeDeResponsablesLegauxFactoryProvider groupeDeResponsablesLegauxFactoryProvider) {
		this.groupeDeResponsablesLegauxFactoryProvider = groupeDeResponsablesLegauxFactoryProvider;
	}

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.instance.EntitesRacineFactoryProvider#getFactory(org.fcpe.fantinlatour.model.TypeEnseignement)
	 */
	@Override
	public EntitesRacineFactory getFactory(TypeEnseignement typeEnseignement) {
		
		EntitesRacineFactory result = null;
		
		GroupeDeResponsablesLegauxFactory groupeDeResponsablesLegauxFactory = groupeDeResponsablesLegauxFactoryProvider.getFactory(typeEnseignement);
		
	
		switch(typeEnseignement) {
		case ELEMENTAIRE:
			result = new EntitesRacineElementaireFactory(groupeDeResponsablesLegauxFactory);
			break;
		case SECONDAIRE:
			result = new EntitesRacineSecondaireFactory(groupeDeResponsablesLegauxFactory);
			break;
		}
		return result;
	}

}
