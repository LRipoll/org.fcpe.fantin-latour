package org.fcpe.fantinlatour.courriel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ResponsableLegal;

public class ListingFactory {

	private static final String COMMISSION_EDUC_FANTINLATOUR = "commission-educ--fantinlatour";
	private static final String CA_FCPE_FANTINLATOUR = "ca-fcpe-fantinlatour";
	public static final String FCPE_FANTIN_INFO = "fcpe-fantin-info";
	public static final String FCPE_FANTIN = "fcpe-fantin";

	private AnneeScolaire anneeScolaire;
	
	
	public ListingFactory(AnneeScolaire anneeScolaire) {
		super();
		this.anneeScolaire = anneeScolaire;
	}

	public Set<String> getCourriels(String googlegroup) {
		Set<String> result = null;
		if (googlegroup.equalsIgnoreCase(FCPE_FANTIN)) {
			result = anneeScolaire.getConseilLocal().getCourrielsDesAdherents();
		} else if (googlegroup.equalsIgnoreCase(FCPE_FANTIN_INFO)) {
			result = anneeScolaire.getCourrielNouveauxResponsablesLegaux();
		} else if (googlegroup.equalsIgnoreCase(CA_FCPE_FANTINLATOUR)) {
			result = union(anneeScolaire.getConseilLocal().getCourrielsDesMembresDuBureau(),
					anneeScolaire.getConseilLocal().getCourrielsDesMembresConseilAdministration());
		} else if (googlegroup.equalsIgnoreCase(COMMISSION_EDUC_FANTINLATOUR)) {
			result = anneeScolaire.getConseilLocal().getCourrielsDesMembresCommissionEducative();
		}
		return result;
	}

	public String getGoogleGroup(String filename) {
		return FilenameUtils.removeExtension(FilenameUtils.getBaseName(filename));
	}

	public Set<String> minus(Set<String> membres, Set<String> membresASupprimer) {
		Set<String> result = new HashSet<String>(membres);

		result.removeAll(membresASupprimer);
		return result;
	}

	public Set<String> union(Set<String> membres, Set<String> membres2) {
		Set<String> result = new HashSet<String>(membres);

		result.addAll(membres2);

		return result;
	}
	
	/* TODO A déplacer*/
	public static Set<String> getCourriels(List<ResponsableLegal> responsablesLegaux) {
		Set<String> result = new HashSet<String>();
		for (ResponsableLegal membre : responsablesLegaux) {
			result.add(normaliseCourriel(membre.getCourriel()));
		}
		return result;
	}

	public  static String normaliseCourriel(String courriel) {
		return StringUtils.lowerCase(StringUtils.trim(courriel));
	}
}
