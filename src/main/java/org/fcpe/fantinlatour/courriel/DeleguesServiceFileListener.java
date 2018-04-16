package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.fcpe.fantinlatour.model.ConseilLocalConfig;

public class DeleguesServiceFileListener implements AnneeScolaireServiceListener {

	private ConseilLocalConfig config;
	
	
	public DeleguesServiceFileListener(ConseilLocalConfig config) {
		super();
		this.config = config;
	}


	@Override
	public void process(String text) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(config.getDeleguesFilename(), config.getEncodage());

		writer.println(text);
		writer.close();

	}

}
