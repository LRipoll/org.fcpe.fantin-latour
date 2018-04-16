package org.fcpe.fantinlatour.courriel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class GoogleGroupServiceConsoleListener implements GoogleGroupServiceListener {

	private static final Logger logger = Logger.getLogger(GoogleGroupServiceConsoleListener.class);
	
	@Override
	public void courrielsSupprimes(String groupe, Set<String> courriels) {
		logger.warn(String.format("Les membres à supprimer du googlegroup %s sont %s", groupe,
				getMembres(courriels)));
		
	}

	@Override
	public void courrielsAjoutes(String groupe, Set<String> courriels) {
		logger.warn(String.format("Les membres à ajouter au googlegroup %s sont %s", groupe,
				getMembres(courriels)));

	}
	
	private String getMembres(Set<String> membres) {
		StringBuffer result = new StringBuffer();
		int i = 0;
		List<String> membresOrdonnes = new ArrayList<String>(membres);
		Collections.sort(membresOrdonnes);
		for (String membre : membresOrdonnes) {
			if ((i % 10) == 0) {
				result.append("\n");
			} else if (i > 0) {
				result.append(", ");
			}
			result.append(membre);
			i++;
		}
		return result.toString();
	}

}
