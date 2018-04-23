package org.fcpe.fantinlatour.view;

import java.util.Optional;

import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AlertDialog {

	protected Alert alert;
	public AlertDialog(Alert alert, String name, String headerText, String contentText) {
		super();
		this.alert = alert;
		alert.setTitle(SpringFactory.getMessage(String.format(ViewFactory.FORMAT_TITLE, name)));
		alert.setHeaderText(SpringFactory.getMessage(headerText));
		alert.setContentText(contentText);
	}

	public Optional<ButtonType> showAndWait() {
		Stage stage = getStage();
		 stage.setAlwaysOnTop(true);
		return alert.showAndWait();
		
		
	}

	protected Stage getStage() {
		return (Stage) alert.getDialogPane().getScene().getWindow();
	}
}