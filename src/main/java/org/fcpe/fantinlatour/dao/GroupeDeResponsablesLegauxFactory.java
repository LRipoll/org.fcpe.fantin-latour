package org.fcpe.fantinlatour.dao;

import java.util.List;

import org.fcpe.fantinlatour.model.GroupeDeResponsablesLegaux;

public interface GroupeDeResponsablesLegauxFactory {

	public static final int PRIORITE_BUREAU = 1;
	public static final int PRIORITE_ELU = 2;
	public static final int PRIORITE_COMMISSION_EDUCATIVE = 3;
	public static final int PRIORITE_ADHERENTS = 4;
	List<GroupeDeResponsablesLegaux> create();

}