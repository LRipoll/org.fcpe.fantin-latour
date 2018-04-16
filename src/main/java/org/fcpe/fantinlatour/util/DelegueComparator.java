package org.fcpe.fantinlatour.util;

import java.util.Comparator;

import org.fcpe.fantinlatour.model.Delegue;

public class DelegueComparator implements Comparator<Delegue> {

	@Override
	public int compare(Delegue delegue1, Delegue delegue2) {
		int result = delegue1.getEngagement().compareTo(delegue2.getEngagement());
		if (result == 0) {
			result = getPoidsEngagement(delegue1.getResponsableLegal().getNbEngagements())
					.compareTo(getPoidsEngagement(delegue2.getResponsableLegal().getNbEngagements()));
			if (result == 0) {
				result = delegue1.getResponsableLegal().getNom().compareTo(delegue2.getResponsableLegal().getNom());
				if (result == 0) {
					result = delegue1.getResponsableLegal().getPrenom()
							.compareTo(delegue2.getResponsableLegal().getPrenom());
				}
			}

		}

		return result;
	}

	private Integer getPoidsEngagement(int nbEngagements) {
		int result = 1;
		if (nbEngagements > 0) {
			result = 0;
		}
		return result;

	}

}
