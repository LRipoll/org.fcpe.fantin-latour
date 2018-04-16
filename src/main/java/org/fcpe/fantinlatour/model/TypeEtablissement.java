package org.fcpe.fantinlatour.model;

import org.fcpe.fantinlatour.service.SpringFactory;

public enum TypeEtablissement {

	MATERNELLE(SpringFactory.getMessage("org.fcpe.fantinlatour.etablissement.maternelle")), 
	PRIMAIRE(SpringFactory.getMessage("org.fcpe.fantinlatour.etablissement.primaire")), 
	ELEMENTAIRE(SpringFactory.getMessage("org.fcpe.fantinlatour.etablissement.elementaire")), 
	COLLEGE(SpringFactory.getMessage("org.fcpe.fantinlatour.etablissement.college")), 
	LYCEE(SpringFactory.getMessage("org.fcpe.fantinlatour.etablissement.lycee"));

	private String label;

	TypeEtablissement(String label) {
		this.label = label;
	}

	public String toString() {
		return label;
	}
}
