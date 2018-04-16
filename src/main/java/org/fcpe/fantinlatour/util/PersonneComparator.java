package org.fcpe.fantinlatour.util;

import java.util.Comparator;

import org.fcpe.fantinlatour.model.Personne;

public class PersonneComparator implements Comparator<Personne> {

	@Override
	public int compare(Personne personne1, Personne personne2) {
		int result = personne1.getNom().compareTo(personne2.getNom());
		if (result == 0) {
			result = personne1.getPrenom().compareTo(personne2.getPrenom());
		}

		return result;
	}

}
