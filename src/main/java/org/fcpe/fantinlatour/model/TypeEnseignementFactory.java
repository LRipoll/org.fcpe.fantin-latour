package org.fcpe.fantinlatour.model;

public class TypeEnseignementFactory {

public TypeEnseignement create(TypeEtablissement typeEtablissement) {
		
		TypeEnseignement result = null;
		switch (typeEtablissement) {
		case MATERNELLE:
		case PRIMAIRE :
		case ELEMENTAIRE :
			result = TypeEnseignement.ELEMENTAIRE;
			break;
		case COLLEGE :
		case LYCEE :
			result = TypeEnseignement.SECONDAIRE;
		default:
			break;
		}
		
		return result;
	}

}
