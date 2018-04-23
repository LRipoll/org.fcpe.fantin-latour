package org.fcpe.fantinlatour.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Organisation")
public class ConseilLocalEtablissement {

	@XStreamAlias("Etablissement")
	private Etablissement etablissement;

	public ConseilLocalEtablissement(Etablissement etablissement) {
		super();
		this.etablissement = etablissement;
	}


	public Etablissement getEtablissement() {
		return etablissement;
	}

}
