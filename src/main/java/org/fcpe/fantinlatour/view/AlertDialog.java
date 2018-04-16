package org.fcpe.fantinlatour.view;

import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.scene.control.Alert;

public class AlertDialog {

	protected Alert alert;
	public AlertDialog(Alert alert, String name, String headerText, String contentText) {
		super();
		this.alert = alert;
		alert.setTitle(SpringFactory.getMessage(String.format(ViewFactory.FORMAT_TITLE, name)));
		alert.setHeaderText(SpringFactory.getMessage(headerText));
		alert.setContentText(contentText);
	}

	public void showAndWait() {
		alert.showAndWait();
		
	}
}