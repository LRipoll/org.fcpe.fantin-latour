package org.fcpe.fantinlatour.model;

import java.util.List;

public interface EntitesRacineFactory {

	
	public static final int PRIORITE_PARENTS = 3;
	public static final int PRIORITE_DELEGUES = 2;
	public static final int PRIORITE_CONSEIL_LOCAL = 1;
	
	List<EntiteRacine> create();
}
