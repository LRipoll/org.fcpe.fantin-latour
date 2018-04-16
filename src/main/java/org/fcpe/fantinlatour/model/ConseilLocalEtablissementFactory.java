package org.fcpe.fantinlatour.model;

public class ConseilLocalEtablissementFactory {

	private EtablissementFactory etablissementFactory;

	public ConseilLocalEtablissementFactory(EtablissementFactory etablissementFactory) {
		super();
		this.etablissementFactory = etablissementFactory;

	}

	public ConseilLocalEtablissement create(String name, TypeEtablissement typeEtablissement) {
		Etablissement etablissement = etablissementFactory.create(name, typeEtablissement);
		return new ConseilLocalEtablissement(etablissement);
	}

}
