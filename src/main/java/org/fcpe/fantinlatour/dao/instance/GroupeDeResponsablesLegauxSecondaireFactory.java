package org.fcpe.fantinlatour.dao.instance;

import java.util.List;

import org.fcpe.fantinlatour.model.Assemblee;
import org.fcpe.fantinlatour.model.GroupeDeResponsablesLegaux;

public class GroupeDeResponsablesLegauxSecondaireFactory extends GroupeDeResponsablesLegauxConseilLocalFactory {

	@Override
	public List<GroupeDeResponsablesLegaux> create() {
		List<GroupeDeResponsablesLegaux> result = super.create();
		
		GroupeDeResponsablesLegaux elus = new Assemblee("Conseil d'administration", PRIORITE_ELU, "CA");
		result.add(elus);
		
		GroupeDeResponsablesLegaux commissionEducative = new Assemblee("Commission éducative", PRIORITE_COMMISSION_EDUCATIVE, "CE");
		result.add(commissionEducative);
		return result ;
		
	}



}
