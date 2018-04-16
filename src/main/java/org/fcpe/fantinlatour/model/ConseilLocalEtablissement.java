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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConseilLocalEtablissement other = (ConseilLocalEtablissement) obj;
		if (etablissement == null) {
			if (other.etablissement != null)
				return false;
		} else if (!etablissement.equals(other.etablissement))
			return false;
		return true;
	}

	public Etablissement getEtablissement() {
		return etablissement;
	}

}
