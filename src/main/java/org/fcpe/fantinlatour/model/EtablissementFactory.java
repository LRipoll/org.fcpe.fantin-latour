package org.fcpe.fantinlatour.model;

public class EtablissementFactory {

	
	

	public EtablissementFactory() {
		super();
		
	}


	public Etablissement create(String name, TypeEtablissement typeEtablissement) {
		
		return new Etablissement(name, typeEtablissement);
	}

}
