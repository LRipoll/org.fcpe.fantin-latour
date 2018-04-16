package org.fcpe.fantinlatour.courriel;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fcpe.fantinlatour.parser.GoogleGroupParser;
import org.fcpe.fantinlatour.parser.GroupeDeDiscussionParser;

public class GoogleGroupService {

	private static final Logger logger = Logger.getLogger(GoogleGroupService.class);
	private String filename;
	private String googleGroup;
	private Set<String> nouveauxMembres;
	private ListingFactory listingFactory;
	private GroupeDeDiscussionParser googleGroupParser;
	private GoogleGroupServiceListener googleGroupServiceListener;

	public GoogleGroupService(Set<String> nouveauxMembres, String googleGroup, String filename,
			ListingFactory listingFactory, GroupeDeDiscussionParser googleGroupParser,
			GoogleGroupServiceListener googleGroupServiceListener) {
		super();
		this.filename = filename;
		this.nouveauxMembres = nouveauxMembres;
		this.googleGroup = googleGroup;
		this.listingFactory = listingFactory;
		this.googleGroupParser = googleGroupParser;
		this.googleGroupServiceListener = googleGroupServiceListener;
	}

	public GoogleGroupService(Set<String> nouveauxMembres, String googleGroup, String filename,
			ListingFactory listingFactory, GoogleGroupServiceListener googleGroupServiceListener) {
		this(nouveauxMembres, googleGroup, filename, listingFactory, new GoogleGroupParser(),
				googleGroupServiceListener);
	}

	public void run() {
		Set<String> anciensMembres = getAnciensMembres();

		if (anciensMembres != null) {
			Set<String> anciensMembresASupprimer = listingFactory.minus(anciensMembres, nouveauxMembres);
			googleGroupServiceListener.courrielsSupprimes(googleGroup, anciensMembresASupprimer);
			Set<String> nouveauxMembresAAJouter = listingFactory.minus(nouveauxMembres, anciensMembres);
			googleGroupServiceListener.courrielsAjoutes(googleGroup, nouveauxMembresAAJouter);

		}
	}

	protected Set<String> getAnciensMembres() {
		Set<String> result = null;

		try {
			result = new HashSet<String>(googleGroupParser.parse(filename));
		} catch (IOException e) {
			logger.error(String.format("Erreur lors du traitement du fichier suivant :%s", filename), e);

		}

		return result;
	}

}
