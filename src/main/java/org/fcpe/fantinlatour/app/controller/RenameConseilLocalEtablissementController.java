package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RenameConseilLocalEtablissementController extends AbstractConseilLocalController {

	public RenameConseilLocalEtablissementController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);
		

	}

	@Override
	protected void execute(ActionEvent event) {
		try {

			conseilLocalEtablissementManager.rename(nameTextField.getText());
			super.execute(event);
		} catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		}

	}
	

}
