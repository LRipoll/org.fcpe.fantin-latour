package org.fcpe.fantinlatour.model;

import org.aeonbits.owner.Config;

public interface ConseilLocalConfig extends Config {
	String ID = "conseilLocalConfig";

	@DefaultValue("4")
	@Key("college.conseilLocal.classe.nbMaxDeleguesParClasse")
	int getNombreMaximumDeleguesParClasse();
	
	@DefaultValue("CE")
	@Key("college.conseilLocal.commissionEducative.sigle")
	String getSigleCommissionEducative();
	
	@DefaultValue("CA")
	@Key("college.conseilLocal.conseilAdministration.sigle")
	String getSigleConseilAdministration();
	
	@DefaultValue("BU")
	@Key("college.conseilLocal.bureau.sigle")
	String getSigleBureau();

	@DefaultValue("/Users/mathieuripoll/eclipse-workspace/AnneeScolaire/target/exports/delegues.html")
	@Key("college.fichiers.delegues")
	String getDeleguesFilename();

	@DefaultValue("UTF-8")
	@Key("college.fichiers.encodage")
	String getEncodage();

}
