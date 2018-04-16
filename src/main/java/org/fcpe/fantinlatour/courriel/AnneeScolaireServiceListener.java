package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public interface AnneeScolaireServiceListener {

	public void process(String text) throws FileNotFoundException, UnsupportedEncodingException;

}
