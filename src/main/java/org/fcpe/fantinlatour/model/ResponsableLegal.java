package org.fcpe.fantinlatour.model;

import java.util.ArrayList;
import java.util.List;

public class ResponsableLegal extends Personne {

	private String courriel;
	private List<String> membreDe;
	private boolean adhesionTransmise;
	private float montantAdhesion;
	public ResponsableLegal(String nom, String prenom, String courriel) {
		super(nom, prenom);
		this.courriel = courriel;
		this.adhesionTransmise = false;
		this.montantAdhesion = 0;
		
		membreDe=new ArrayList<String>();
	}

	
	public boolean isAdhesionTransmise() {
		return adhesionTransmise;
	}


	public void setAdhesionTransmise(boolean adhesionTransmise) {
		this.adhesionTransmise = adhesionTransmise;
	}


	public float getMontantAdhesion() {
		return montantAdhesion;
	}


	public void setMontantAdhesion(float montantAdhesion) {
		this.montantAdhesion = montantAdhesion;
	}


	public void estMembreDe(String membre) {
		membreDe.add(membre);
	}
	public String getMembreDe() {
		StringBuffer result = new StringBuffer();
		for (int i=0; i< membreDe.size();i++) {
			if (i==0) {
				result.append( " (");
			} else {
				result.append( ", ");
			}
			result.append(membreDe.get(i));
		}
		if (membreDe.size()>0) {
			result.append(")**");
		}
		return result.toString();
	}
	/**
	 * @return the courriel
	 */
	public String getCourriel() {
		return courriel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResponsableLegal [" + super.toString() + " courriel="
				+ courriel + "]";
	}

	public int getNbEngagements() {
		return membreDe.size();
	}

}
