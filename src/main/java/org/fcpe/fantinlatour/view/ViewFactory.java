package org.fcpe.fantinlatour.view;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ViewFactory {

	private static final String MESSAGES = "messages";
	private static final String FORMAT_FXML = "%s.fxml";
	static final String FORMAT_TITLE = "org.fcpe.fantinlatour.view.%s.title";
	private static final String FORMAT_CSS = "%s.css";
	public static final String ID = "viewFactory";

	public ViewFactory() {
	}

	public Scene createScene(Stage stage, String name) {
		Scene result = null;

		FXMLLoader loader = new FXMLLoader();
		Parent fxmlRoot;

		try {

			
			// TODO Lire le bundle dans Spring via ReloadableResourceBundleMessageSource
			// resourceBundleMessageSource = SpringFactory.getService("messageSource");
			loader.setResources(ResourceBundle.getBundle(MESSAGES, Locale.getDefault()));
			fxmlRoot = loader.load(getClass().getResourceAsStream(String.format(FORMAT_FXML, name)));

			result = new Scene(fxmlRoot);

			String css = this.getClass().getResource(String.format(FORMAT_CSS, name)).toExternalForm();
			result.getStylesheets().add(css);

			stage.setTitle(SpringFactory.getMessage(String.format(FORMAT_TITLE, name)));
			stage.setScene(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public Stage createStage(String name, Modality modality) {
		Stage stage = new Stage();

		stage.initModality(modality);
		stage.initStyle(StageStyle.UTILITY);
		createScene(stage, name);

		return stage;
	}

}
