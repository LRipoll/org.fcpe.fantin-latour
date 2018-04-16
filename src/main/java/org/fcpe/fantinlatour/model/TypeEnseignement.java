package org.fcpe.fantinlatour.model;

import org.fcpe.fantinlatour.service.SpringFactory;
public enum TypeEnseignement {
	ELEMENTAIRE(SpringFactory.getMessage("org.fcpe.fantinlatour.enseignement.elementaire")), 
	SECONDAIRE(SpringFactory.getMessage("org.fcpe.fantinlatour.enseignement.secondaire"));
	
	private String label;

	TypeEnseignement(String label) {
        this.label = label;
    }

    public String toString() {
        return label;
    }
}
