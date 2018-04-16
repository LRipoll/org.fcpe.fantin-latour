package org.fcpe.fantinlatour.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fcpe.fantinlatour.courriel.ListingFactory;
import org.fcpe.fantinlatour.util.PersonneComparator;

public class ConseilLocal {

	private Map<Classe, List<Delegue>> delegues;
	private List<MembreBureau> membresBureau;
	private List<Candidat> membresConseilAdministration;
	private List<Candidat> membresCommissionEducative;
	private List<ResponsableLegal> nouveauxAdherents;
	private ConseilLocalConfig config;

	public ConseilLocal(ConseilLocalConfig config) {
		this.config = config;
		delegues = new HashMap<Classe, List<Delegue>>();
		membresBureau = new ArrayList<MembreBureau>();
		membresConseilAdministration = new ArrayList<Candidat>();
		membresCommissionEducative = new ArrayList<Candidat>();
		nouveauxAdherents = new ArrayList<ResponsableLegal>();

	}

	public void addMembreCommissionEducative(ResponsableLegal responsableLegal, Engagement engagement) {
		responsableLegal.estMembreDe(config.getSigleCommissionEducative());
		addCandidat(membresCommissionEducative, responsableLegal, engagement);
	}

	public void addMembreBureau(ResponsableLegal responsableLegal, Titre titre) {
		responsableLegal.estMembreDe(config.getSigleBureau());
		membresBureau.add(new MembreBureau(responsableLegal, titre));
	}

	public void addMembreConseilAdministration(ResponsableLegal responsableLegal, Engagement engagement) {
		responsableLegal.estMembreDe(config.getSigleConseilAdministration());
		addCandidat(membresConseilAdministration, responsableLegal, engagement);

	}

	public Delegue addDelegue(Classe classe, ResponsableLegal responsableLegal, Engagement engagement) {
		Delegue result = null;
		List<Delegue> deleguesDeLaClasse = getDelegues(classe);
		Delegue delegue = new Delegue(classe, responsableLegal, engagement);

		deleguesDeLaClasse.add(delegue);

		return result;
	}

	public int getNbMaximumCandidatsDeleguesParClasse() {
		int result = 0;
		for (Classe classe : delegues.keySet()) {
			int nbMinimumCandidatsDelegues = classe.getNbMinimumCandidatsDelegues();
			if (nbMinimumCandidatsDelegues > result) {
				result = nbMinimumCandidatsDelegues;
			}
		}
		return result;
	}

	public ResponsableLegal getPresident() {
		return getMembresBureau(Titre.Président).get(0);
	}

	public List<ResponsableLegal> getSecretaires() {
		return getMembresBureau(Titre.Secrétaire);
	}

	public List<ResponsableLegal> getTresoriers() {
		return getMembresBureau(Titre.Trésorier);
	}

	public List<ResponsableLegal> getMembresBureau() {
		return getMembresBureau(Titre.Membre);
	}

	public List<Delegue> getDelegues(Classe classe) {
		List<Delegue> result = delegues.get(classe);
		if (result == null) {
			result = new ArrayList<Delegue>();
			delegues.put(classe, result);

		}
		return result;
	}

	public Set<String> getCourrielsDesMembresDuBureau() {
		return getCourriels(getMembresBureau(null, false));
	}

	private void addCandidat(List<Candidat> candidats, ResponsableLegal responsableLegal, Engagement engagement) {

		candidats.add(new Candidat(responsableLegal, engagement));
	}

	public void addNouveauAdherent(ResponsableLegal responsableLegal) {

		nouveauxAdherents.add(responsableLegal);

	}

	public ResponsableLegal getAdherent(String courriel) {
		ResponsableLegal result = null;
		Iterator<ResponsableLegal> adherents = nouveauxAdherents.iterator();
		while (result == null && adherents.hasNext()) {
			ResponsableLegal adherent = adherents.next();
			if (adherent.getCourriel().equalsIgnoreCase(courriel)) {
				result = adherent;
			}

		}
		return result;

	}

	public Set<String> getCourrielsDesAdherents() {
		return getCourriels(nouveauxAdherents);
	}

	public List<ResponsableLegal> getAdherents() {

		Collections.sort(nouveauxAdherents, new PersonneComparator());
		return nouveauxAdherents;

	}

	public Set<String> getCourrielsDesMembresConseilAdministration() {
		return getCourriels(getResponsablesLegaux(membresConseilAdministration));
	}

	public List<ResponsableLegal> getMembresConseilAdministration() {

		List<ResponsableLegal> result = getResponsablesLegaux(membresConseilAdministration);

		Collections.sort(result, new PersonneComparator());
		return result;
	}

	public Set<String> getCourrielsDesMembresCommissionEducative() {
		return getCourriels(getResponsablesLegaux(membresCommissionEducative));
	}

	public List<ResponsableLegal> getMembresCommissionEducative() {

		List<ResponsableLegal> result = getResponsablesLegaux(membresCommissionEducative);

		Collections.sort(result, new PersonneComparator());
		return result;
	}

	private List<ResponsableLegal> getResponsablesLegaux(List<Candidat> candidats) {
		List<ResponsableLegal> result = new ArrayList<ResponsableLegal>(candidats.size());
		for (Candidat candidat : candidats) {
			result.add(candidat.getResponsableLegal());
		}
		return result;
	}

	private List<ResponsableLegal> getMembresBureau(Titre titre) {
		return getMembresBureau(titre, true);

	}

	private List<ResponsableLegal> getMembresBureau(Titre titre, boolean sort) {
		List<ResponsableLegal> result = new ArrayList<ResponsableLegal>();
		for (MembreBureau membre : membresBureau) {
			if (membre.getTitre() == titre || titre == null) {
				result.add(membre.getResponsableLegal());
			}
		}
		if (sort)
			Collections.sort(result, new PersonneComparator());

		return result;
	}

	private Set<String> getCourriels(List<ResponsableLegal> responsablesLegaux) {

		return ListingFactory.getCourriels(responsablesLegaux);
	}

}
