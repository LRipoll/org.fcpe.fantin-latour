package org.fcpe.fantinlatour.dao.instance;

import java.util.List;

import org.fcpe.fantinlatour.dao.EntitesRacineDAO;
import org.fcpe.fantinlatour.model.EntiteRacine;
import org.fcpe.fantinlatour.model.EntitesRacineFactory;
import org.fcpe.fantinlatour.model.EntitesRacineFactoryProvider;
import org.fcpe.fantinlatour.model.TypeEnseignement;

public class EntitesRacineDAOImpl implements EntitesRacineDAO {

	private EntitesRacineFactoryProvider entitesRacineFactoryProviderImpl;

	public EntitesRacineDAOImpl(EntitesRacineFactoryProvider entitesRacineFactoryProviderImpl) {
		this.entitesRacineFactoryProviderImpl = entitesRacineFactoryProviderImpl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fcpe.fantinlatour.dao.EntitesRacineDAO#getEntitesRacines(org.fcpe.
	 * fantinlatour.model.TypeEnseignement)
	 */
	@Override
	public List<EntiteRacine> getEntitesRacines(TypeEnseignement typeEnseignement) {
		EntitesRacineFactory factory = entitesRacineFactoryProviderImpl.getFactory(typeEnseignement);
		return factory.create();
	}

}
