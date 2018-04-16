package org.fcpe.fantinlatour.dao.instance;

import java.util.List;

import org.fcpe.fantinlatour.model.Assemblee;
import org.fcpe.fantinlatour.model.GroupeDeResponsablesLegaux;

public class GroupeDeResponsablesLegauxElementaireFactory extends GroupeDeResponsablesLegauxConseilLocalFactory {

	public GroupeDeResponsablesLegauxElementaireFactory() {

	}

	@Override
	public List<GroupeDeResponsablesLegaux> create() {
		List<GroupeDeResponsablesLegaux> result = super.create();

		GroupeDeResponsablesLegaux elus = new Assemblee("Conseil d'école", PRIORITE_ELU, "CE");
		result.add(elus);
		return result;
	}

}
