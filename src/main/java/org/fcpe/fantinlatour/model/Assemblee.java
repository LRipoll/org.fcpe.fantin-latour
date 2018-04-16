package org.fcpe.fantinlatour.model;

public class Assemblee extends GroupeDeResponsablesLegaux {

	private String accronyme;
	public Assemblee(String nom, int priorite, String accronyme) {
		super(nom, priorite);

		this.accronyme = accronyme;
	}
	public String getAccronyme() {
		return accronyme;
	}
	
	public void setAccronyme(String accronyme) {
		this.accronyme = accronyme;
	}
	
	

}
