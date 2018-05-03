package org.fcpe.fantinlatour.app.controller;

import org.fcpe.fantinlatour.app.context.AppContext;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OpenConseilLocalEtablissementController extends AbstractSecureController {


	public OpenConseilLocalEtablissementController() {
		
	}

	@Override
	protected void execute(ActionEvent event) {
		try {
			AppContext appContext = SpringFactory.getService(AppContext.ID);
			getEncryptHelper().setPassword(passwordTextField.getText());
			conseilLocalEtablissementManager.open(appContext.getConseiLocalToBeOpened());
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
