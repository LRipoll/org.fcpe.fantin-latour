package org.fcpe.fantinlatour.courriel;

import java.util.Set;

public class GoogleGroupServiceFactory {
	private ListingFactory factory;
	
	
	public GoogleGroupServiceFactory(ListingFactory factory) {
		super();
		this.factory = factory;
	}


	public GoogleGroupService create(String filename) {
		String googleGroup = factory.getGoogleGroup(filename);
		Set<String> nouveauxMembres = factory.getCourriels(googleGroup);
		
		return new GoogleGroupService(nouveauxMembres, googleGroup, filename, factory, new GoogleGroupServiceConsoleListener());
	}
}
