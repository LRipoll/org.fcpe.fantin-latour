package org.fcpe.fantinlatour.app.controller;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ExportConseilLocalEtablissementController extends AbstractSecureController {


	public ExportConseilLocalEtablissementController() {
		
	}

	@Override
	protected void execute(ActionEvent event) {
		try {
			
			getEncryptHelper().setPassword(passwordTextField.getText());
			conseilLocalEtablissementManager.exportAsZip();
			super.execute(event);

		} catch (PasswordException e) {
			passwordTextField.setText(null);
		} 
		catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
		}

	}

}
