package org.fcpe.fantinlatour.model;

public interface EntitesRacineFactoryProvider {

	public static final String ID = "entitesRacineFactoryProvider";
	EntitesRacineFactory getFactory(TypeEnseignement typeEnseignement);

}