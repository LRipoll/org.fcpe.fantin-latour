package org.fcpe.fantinlatour.model;

public enum Titre {
	Président, Trésorier, Secrétaire, Membre, Vice_Président;

	private static final String VICE_PRESIDENT = "vice-président";

	public static Titre parse(String titre) {
		Titre result = null;
		if (titre.equalsIgnoreCase(Titre.Président.toString())) {
			result = Titre.Président;
		} else if (titre.equalsIgnoreCase(Titre.Trésorier.toString())) {
			result = Titre.Trésorier;
		} else if (titre.equalsIgnoreCase(Titre.Secrétaire.toString())) {
			result = Titre.Secrétaire;
		} else if (titre.equalsIgnoreCase(Titre.Membre.toString())) {
			result = Titre.Membre;
		}
		else if (titre.equalsIgnoreCase(VICE_PRESIDENT.toString())) {
			result = Titre.Vice_Président;
		}
		return result;
	}
}
