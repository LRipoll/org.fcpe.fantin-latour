package org.fcpe.fantinlatour.dao.instance;

import java.util.ArrayList;
import java.util.List;

import org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory;
import org.fcpe.fantinlatour.model.Assemblee;
import org.fcpe.fantinlatour.model.GroupeDeResponsablesLegaux;

public class GroupeDeResponsablesLegauxConseilLocalFactory implements GroupeDeResponsablesLegauxFactory {

	

	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.dao.GroupeDeResponsablesLegauxFactory#create()
	 */
	@Override
	public List<GroupeDeResponsablesLegaux> create() {
		List<GroupeDeResponsablesLegaux> result = new ArrayList<GroupeDeResponsablesLegaux>();

		GroupeDeResponsablesLegaux bureau = new Assemblee("Bureau", PRIORITE_BUREAU, "BU");
		result.add(bureau);
		
		GroupeDeResponsablesLegaux adherents = new GroupeDeResponsablesLegaux("Adhérents", PRIORITE_ADHERENTS);
		result.add(adherents);

		return result;
	}

}
