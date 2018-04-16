package org.fcpe.fantinlatour.model;

public class ConseilLocalEtablissement {

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
