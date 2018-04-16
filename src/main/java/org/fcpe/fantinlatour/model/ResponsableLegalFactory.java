package org.fcpe.fantinlatour.model;

import org.apache.commons.lang.StringUtils;

public class ResponsableLegalFactory {
	public ResponsableLegalFactory() {
		super();

	}

	public ResponsableLegal createResponsableLegal(String nom, String prenom, String courriel) {
		ResponsableLegal result = null;
		if (!StringUtils.isEmpty(nom)) {
			result = new ResponsableLegal(nom, prenom, courriel);
		}
		return result;
	}
}
