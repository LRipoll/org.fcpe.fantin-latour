package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertSame;

import org.fcpe.fantinlatour.model.Engagement;
import org.junit.Test;

public class EngagementTest {

	@Test
	public void test() {
		assertSame(Engagement.OUI,Engagement.parse("oui"));
		assertSame(Engagement.SI_BESOIN,Engagement.parse("si besoin"));
		assertSame(Engagement.NON,Engagement.parse("non"));
		assertSame(Engagement.NON,Engagement.parse(""));
	}
}
