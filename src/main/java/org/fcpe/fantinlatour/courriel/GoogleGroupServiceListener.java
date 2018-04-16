package org.fcpe.fantinlatour.courriel;

import java.util.Set;

public interface GoogleGroupServiceListener {
	public void courrielsSupprimes(String groupe, Set<String> courriels);
	public void courrielsAjoutes(String groupe, Set<String> courriels);
}
