package org.fcpe.fantinlatour.util;

import java.util.Comparator;

import org.fcpe.fantinlatour.model.Entite;

public class EntiteComparator implements Comparator<Entite> {

	

	@Override
	public int compare(Entite entite1, Entite entite2) {
		return entite1.getPriorite() - entite2.getPriorite();
	}

}
