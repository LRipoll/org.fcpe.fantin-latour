package org.fcpe.fantinlatour.app.controller;

import java.util.Optional;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ConfirmationAlertDialog;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ExportConseilLocalEtablissementController extends AbstractSecureController {


	private static final String DELETEEXPORT_HEADER_TEXT = "org.fcpe.fantinlatour.view.exportconseillocal.delete.headerText";
	private static final String DELETEEXPORT_TEXT = "org.fcpe.fantinlatour.view.exportconseillocal.delete.text";

	public ExportConseilLocalEtablissementController() {
		
	}

	@Override
	protected void execute(ActionEvent event) {
		try {
			
			if (conseilLocalEtablissementManager.exportedArchiveAlreadyExists()) {
				ConfirmationAlertDialog confirmationAlertDialog = new ConfirmationAlertDialog(new Alert(AlertType.CONFIRMATION),
						SpringFactory.getMessage(DELETEEXPORT_HEADER_TEXT),
						SpringFactory.getMessage(DELETEEXPORT_TEXT,
								new Object[] {
										conseilLocalEtablissementManager.getExportedArchiveFilename()}));

				Optional<ButtonType> result = confirmationAlertDialog.showAndWait();
				if (result.get() == ButtonType.OK) {
					getEncryptHelper().setPassword(passwordTextField.getText());
					conseilLocalEtablissementManager.exportArchive(passwordTextField.getText());
					super.execute(event);
				}
			}
			
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
