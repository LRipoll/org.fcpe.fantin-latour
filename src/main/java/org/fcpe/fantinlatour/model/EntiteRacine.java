package org.fcpe.fantinlatour.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntiteRacine extends Entite {

	private Map<String, GroupeDeResponsablesLegaux> groupesResponnsablesLegaux;

	public EntiteRacine(String nom, int priorite) {
		super(nom, priorite);
		groupesResponnsablesLegaux = new HashMap<String, GroupeDeResponsablesLegaux>();
	}

	public void addGroupe(GroupeDeResponsablesLegaux groupe) {
		groupesResponnsablesLegaux.put(groupe.getNom(), groupe);
	}

	public GroupeDeResponsablesLegaux getGroupe(String groupe) {
		return groupesResponnsablesLegaux.get(groupe);
	}

	public Collection<GroupeDeResponsablesLegaux> getGroupes() {
		return groupesResponnsablesLegaux.values();
	}

	public void addGroupes(List<GroupeDeResponsablesLegaux> groupes) {
		for (GroupeDeResponsablesLegaux groupe : groupes) {
			addGroupe(groupe);
		}

	}

}
