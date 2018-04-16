package org.fcpe.fantinlatour.model;

public enum Engagement {
	NON, OUI, SI_BESOIN;
	
	public static Engagement parse(String engagement) {
		Engagement result = Engagement.SI_BESOIN;
		if (engagement.equalsIgnoreCase(Engagement.OUI.toString())) {
			result = Engagement.OUI;
		} else if (engagement.equalsIgnoreCase(Engagement.NON.toString())) {
			result = Engagement.NON;
		} else if (engagement.length() == 0) {
			result = Engagement.NON;
		}
		return result;
	}
}
