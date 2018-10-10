package org.fcpe.fantinlatour.model;

import org.apache.log4j.Logger;

public class Eleve extends Personne {

	private static final Logger logger = Logger.getLogger(Eleve.class);
	private Classe classe;
	private ResponsableLegal responsableLegal1;
	private ResponsableLegal responsableLegal2;

	protected Eleve(Classe classe, String nom, String prenom) {
		super(nom, prenom);
		this.classe = classe;
	}

	/**
	 * @return the classe
	 */
	public Classe getClasse() {
		return classe;
	}

	/**
	 * @return the responsableLegal1
	 */
	public ResponsableLegal getResponsableLegal1() {
		return responsableLegal1;
	}

	/**
	 * @param responsableLegal1
	 *            the responsableLegal1 to set
	 */
	public void setResponsableLegal1(ResponsableLegal responsableLegal1) {
		this.responsableLegal1 = responsableLegal1;
	}

	/**
	 * @return the responsableLegal2
	 */
	public ResponsableLegal getResponsableLegal2() {
		return responsableLegal2;
	}

	/**
	 * @param responsableLegal2
	 *            the responsableLegal2 to set
	 */
	public void setResponsableLegal2(ResponsableLegal responsableLegal2) {
		this.responsableLegal2 = responsableLegal2;
	}

	public ResponsableLegal getResponsableLegal(String nom, String prenom) {
		ResponsableLegal result = null;
		if (responsableLegal1 == null) {
			logger.info("Cet élève n'a pas de responsable légal défini"+this);
		} else if (responsableLegal1.isThisPersonne(nom, prenom)) {
			result = responsableLegal1;
		} else if (responsableLegal2 != null
				&& responsableLegal2.isThisPersonne(nom, prenom)) {
			result = responsableLegal2;
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Eleve [" + super.toString() + " classe=" + classe.getNom()
				+ ", responsableLegal1=" + responsableLegal1
				+ ", responsableLegal2=" + responsableLegal2 + "]";
	}

}
