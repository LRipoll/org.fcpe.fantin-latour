/**
 * 
 */
package org.fcpe.fantinlatour.app;

import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author mathieuripoll
 *
 */
public class ConseilLocalApp extends Application {

	public static final String NOUVEAU_CONSEIL_LOCAL = "NOUVEAU_CONSEIL_LOCAL";
	private ViewFactory viewFactory;

	/**
	 * 
	 */
	public ConseilLocalApp() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringFactory.setResource("spring/applicationContext.xml");
		launch(args);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {

		viewFactory = SpringFactory.getService(ViewFactory.ID);
		viewFactory.createScene(stage, "main");
		ConseilLocalEtablissementManager conseilLocalEtablissementManager = SpringFactory
				.getService(ConseilLocalEtablissementManager.ID);
		conseilLocalEtablissementManager.init();
		stage.show();
		// TODO Afficher la fenêtre du mot de passe en fonction du fait qu'il y ait ou non conseil par défaut
		// TODO Ajouter un test sur les CombinedPasswordValidator.
	}

}
