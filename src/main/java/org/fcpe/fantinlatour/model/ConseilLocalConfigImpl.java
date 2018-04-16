package org.fcpe.fantinlatour.model;

public class ConseilLocalConfigImpl implements ConseilLocalConfig {

	private int nombreMaximumDeleguesParClasse;
	private String sigleCommissionEducative;
	private String sigleConseilAdministration;
	private String sigleBureau;
	private String encodage ;
	private String deleguesFilename;
	public ConseilLocalConfigImpl() {
	}

	@Override
	public int getNombreMaximumDeleguesParClasse() {
		
		return nombreMaximumDeleguesParClasse;
	}

	@Override
	public String getSigleCommissionEducative() {
		
		return sigleCommissionEducative;
	}

	@Override
	public String getSigleConseilAdministration() {
		return sigleConseilAdministration;
	}

	@Override
	public String getSigleBureau() {
		return sigleBureau;
	}

	@Override
	public String getDeleguesFilename() {
		return deleguesFilename;
	}

	@Override
	public String getEncodage() {
		return encodage;
	}

	public void setNombreMaximumDeleguesParClasse(int nombreMaximumDeleguesParClasse) {
		this.nombreMaximumDeleguesParClasse = nombreMaximumDeleguesParClasse;
	}

	public void setSigleCommissionEducative(String sigleCommissionEducative) {
		this.sigleCommissionEducative = sigleCommissionEducative;
	}

	public void setSigleConseilAdministration(String sigleConseilAdministration) {
		this.sigleConseilAdministration = sigleConseilAdministration;
	}

	public void setSigleBureau(String sigleSigleBureau) {
		this.sigleBureau = sigleSigleBureau;
	}

	public void setEncodage(String encodage) {
		this.encodage = encodage;
	}

	

	public void setDeleguesFilename(String deleguesFilename) {
		this.deleguesFilename = deleguesFilename;
	}
	
	

}
