package org.fcpe.fantinlatour.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.fcpe.fantinlatour.util.ClasseComparator;
import org.springframework.stereotype.Component;

@Component
public class AnneeScolaire {

	private ConseilLocal conseilLocal;
	private Map<String, Classe> classes;
	private ClasseFactory classeFactory;
	private Calendar calendar;
	private ConseilLocalConfig config;

	public AnneeScolaire(ClasseFactory classeFactory, Calendar calendar, ConseilLocalConfig config) {
		super();

		conseilLocal = new ConseilLocal(config);

		classes = new HashMap<String, Classe>();

		this.classeFactory = classeFactory;
		this.calendar = calendar;
		this.config = config;

	}

	public String getAnneeScolaire() {

		int year = calendar.get(Calendar.YEAR);
		return String.format("%d-%d", year, ++year);
	}

	public Classe getClasse(String nom) {
		Classe result = classes.get(nom);
		if (result == null) {
			result = classeFactory.createClasse(this, nom, config);
			classes.put(nom, result);
		}

		return result;
	}

	public Eleve addEleve(String classe, String nom, String prenom) {
		return getClasse(classe).addEleve(nom, prenom);

	}

	public ResponsableLegal getResponsableLegal(String classe, String nom, String prenom) {
		ResponsableLegal result = null;
		String[] classes = classe.split(",");
		int indexClasse = 0;
		while (result == null && indexClasse < classes.length) {
			String division = getDivision(classes[indexClasse]);
			result = getClasse(division).getResponsableLegal(nom, prenom);
			indexClasse++;
		}

		return result;

	}

	private String getDivision(String classe) {
		return classe.replaceAll("\\D+", "");
	}

	/**
	 * @return the conseilLocal
	 */
	public ConseilLocal getConseilLocal() {
		return conseilLocal;
	}

	public String getClassesSansCandidat() {
		return getClasseAvecXCandidats(0);
	}

	public String getClassesAvecUnSeulCandidat() {
		return getClasseAvecXCandidats(1);
	}

	public String getClassesAvecTropDeCandidats() {
		List<Classe> classes = new ArrayList<Classe>();
		for (Classe classe : getClasses()) {
			if (classe.getNbCandidatsDeleguesAffirmés() > config.getNombreMaximumDeleguesParClasse()) {
				classes.add(classe);
			}
		}
		;
		return getClasses(classes);
	}

	private String getClasseAvecXCandidats(int i) {

		List<Classe> classes = new ArrayList<Classe>();
		for (Classe classe : getClasses()) {
			if (classe.getNbCandidatsDeleguesAffirmés() == i) {
				classes.add(classe);
			}
		}

		return getClasses(classes);
	}

	private String getClasses(List<Classe> classes) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < classes.size(); i++) {
			if (i > 0) {
				result.append(", ");
			}
			result.append(classes.get(i).getNomComplet());
		}
		return result.toString();
	}

	public Collection<Classe> getClasses() {
		List<Classe> result = new ArrayList<Classe>(classes.values());
		Collections.sort(result, new ClasseComparator());
		return result;
	}

	public Set<String> getCourrielNouveauxResponsablesLegaux() {

		Set<String> result = new HashSet<String>();
		for (Classe classe : getClasses()) {
			for (ResponsableLegal responsableLegal : classe.getResponsablesLegaux()) {

				result.add(responsableLegal.getCourriel());
			}
		}
		return result;
	}

}
