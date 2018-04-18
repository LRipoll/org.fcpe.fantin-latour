package org.fcpe.fantinlatour.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.fcpe.fantinlatour.courriel.ListingFactory;
import org.fcpe.fantinlatour.util.DelegueComparator;

public class Classe {

	private String nom;
	private List<Eleve> eleves;
	private AnneeScolaire anneeScolaire;
	private EleveFactory eleveFactory;
	private ConseilLocalConfig config;

	Classe(AnneeScolaire anneeScolaire, String nom, EleveFactory eleveFactory, ConseilLocalConfig config) {
		super();
		this.anneeScolaire = anneeScolaire;
		this.nom = nom;
		this.eleveFactory = eleveFactory;
		this.config = config;
		eleves = new ArrayList<Eleve>();
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	public String getNomComplet() {
		return getNiveau() + "ème" + getSection();
	}

	public String getNiveau() {

		return nom.substring(0, 1);
	}

	public String getSection() {
		return nom.substring(1);
	}

	public List<Eleve> getEleves() {
		return eleves;
	}

	public Eleve addEleve(String nom, String prenom) {
		Eleve result = eleveFactory.createEleve(this, nom, prenom);
		eleves.add(result);

		return result;
	}

	public ResponsableLegal getResponsableLegal(String nom, String prenom) {
		ResponsableLegal result = null;
		int indexEleves = 0;
		while (result == null && indexEleves < eleves.size()) {
			Eleve eleve = eleves.get(indexEleves);
			result = eleve.getResponsableLegal(nom, prenom);
			indexEleves++;
		}
		return result;
	}

	public List<ResponsableLegal> getResponsablesLegaux() {
		List<ResponsableLegal> result = new ArrayList<ResponsableLegal>();
		for (Eleve eleve : eleves) {
			addResposableLegal(result, eleve.getResponsableLegal1());
			addResposableLegal(result, eleve.getResponsableLegal2());

		}
		return result;
	}

	public List<String> getCourrielsDesResponsablesLegaux() {

		List<String> result = new ArrayList<String>();

		List<String> courrielsDelegues = getCourrielsDesDelegues();

		for (ResponsableLegal responsableLegal : getResponsablesLegaux()) {
			String courriel = ListingFactory.normaliseCourriel(responsableLegal.getCourriel());
			if (StringUtils.isNotEmpty(courriel) && !courrielsDelegues.contains(courriel)) {
				result.add(courriel);
			}

		}

		return result;
	}

	public List<Delegue> getCandidatsDelegues() {

		List<Delegue> result = getDeleguesFromConseilLocal();
		Collections.sort(result, new DelegueComparator());
		return result;

	}

	private List<Delegue> getDeleguesFromConseilLocal() {
		List<Delegue> result = anneeScolaire.getConseilLocal().getDelegues(this);
		return result;
	}

	public List<String> getCourrielsDesDelegues() {

		List<String> result = new ArrayList<String>();
		List<Delegue> delegues = getDeleguesRetenus();

		for (Delegue delegue : delegues) {
			result.add(ListingFactory.normaliseCourriel(delegue.getResponsableLegal().getCourriel()));
		}

		return result;
	}

	public List<Delegue> getDeleguesRetenus() {

		List<Delegue> result = getFilteredDeleguesVolontaires();

		Collections.sort(result, new DelegueComparator());
		return result;
	}

	public int getNbMinimumCandidatsDelegues() {

		return getFilteredDeleguesVolontaires().size();
	}
	
	public int getNbCandidatsDeleguesAffirmés() {
		List<Delegue> delegues = getDeleguesFromConseilLocal();
		int result = delegues.size();
		for (Delegue delegue : delegues) {
			if (delegue.getEngagement() == Engagement.SI_BESOIN) {
				result--;
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classe other = (Classe) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer listeEleves = new StringBuffer();
		for (Eleve eleve : eleves) {
			listeEleves.append("\n\t");
			listeEleves.append(eleve.toString());
		}

		return "Classe [nom=" + nom + listeEleves + "]";
	}

	

	private List<Delegue> getFilteredDeleguesVolontaires() {
		List<Delegue> result = new ArrayList<Delegue>();
		List<Delegue> delegues = getDeleguesFromConseilLocal();
		for (Delegue delegue : delegues) {
			if (delegue.getEngagement() == Engagement.OUI || delegues.size() <= config.getNombreMaximumDeleguesParClasse()) {
				result.add(delegue);
			}
		}
		return result;
	}

	private void addResposableLegal(List<ResponsableLegal> result, ResponsableLegal responsableLegal) {
		if (responsableLegal != null) {
			result.add(responsableLegal);
		}

	}

}
