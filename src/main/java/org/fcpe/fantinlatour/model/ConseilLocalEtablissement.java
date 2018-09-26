package org.fcpe.fantinlatour.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Organisation")
public class ConseilLocalEtablissement {

	@XStreamAlias("Etablissement")
	private Etablissement etablissement;
	@XStreamAlias("Courriel")
	private MailSenderAccount mailSenderAccount;

	public ConseilLocalEtablissement(Etablissement etablissement) {
		super();
		this.etablissement = etablissement;
		mailSenderAccount  = new MailSenderAccount();
	}


	public Etablissement getEtablissement() {
		return etablissement;
	}

}
