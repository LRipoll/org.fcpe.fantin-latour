package org.fcpe.fantinlatour.model;

public enum Titre {
	Pr�sident, Tr�sorier, Secr�taire, Membre, Vice_Pr�sident;

	private static final String VICE_PRESIDENT = "vice-pr�sident";

	public static Titre parse(String titre) {
		Titre result = null;
		if (titre.equalsIgnoreCase(Titre.Pr�sident.toString())) {
			result = Titre.Pr�sident;
		} else if (titre.equalsIgnoreCase(Titre.Tr�sorier.toString())) {
			result = Titre.Tr�sorier;
		} else if (titre.equalsIgnoreCase(Titre.Secr�taire.toString())) {
			result = Titre.Secr�taire;
		} else if (titre.equalsIgnoreCase(Titre.Membre.toString())) {
			result = Titre.Membre;
		}
		else if (titre.equalsIgnoreCase(VICE_PRESIDENT.toString())) {
			result = Titre.Vice_Pr�sident;
		}
		return result;
	}
}
