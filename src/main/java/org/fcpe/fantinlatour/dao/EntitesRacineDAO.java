package org.fcpe.fantinlatour.dao;

import java.util.List;

import org.fcpe.fantinlatour.model.EntiteRacine;
import org.fcpe.fantinlatour.model.TypeEnseignement;

public interface EntitesRacineDAO {

	public static final String ID = "entitesRacineDAO";
	List<EntiteRacine> getEntitesRacines(TypeEnseignement typeEnseignement);

}