package org.fcpe.fantinlatour.util;

import java.util.Comparator;

import org.fcpe.fantinlatour.model.Classe;

public class ClasseComparator implements Comparator<Classe> {

	@Override
	public int compare(Classe classe1, Classe classe2) {
		int result = classe1.getNiveau().compareTo(classe2.getNiveau());
		if (result == 0) {
			result = classe1.getSection().compareTo(classe2.getSection());
		}

		return result;
	}

}
