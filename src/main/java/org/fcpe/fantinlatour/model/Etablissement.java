package org.fcpe.fantinlatour.model;

public class Etablissement {

	private String nom;
	private TypeEtablissement typeEtablissement;

	public Etablissement(String nom, TypeEtablissement typeEtablissement) {
		super();
		this.nom = nom;
		this.typeEtablissement = typeEtablissement;

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Etablissement other = (Etablissement) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	public String getNom() {
		return nom;
	}

	public TypeEtablissement getTypeEtablissement() {
		return typeEtablissement;
	}

}
