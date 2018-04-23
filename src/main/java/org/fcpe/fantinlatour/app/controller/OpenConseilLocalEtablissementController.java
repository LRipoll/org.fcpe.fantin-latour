package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.context.AppContext;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.AlertDialog;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class OpenConseilLocalEtablissementController extends AbstractController {

	private static final String PASSWORD_ALERT_TEXT = "org.fcpe.fantinlatour.view.passwordAlert.text";

	private static final String PASSWORD_ALERT_HEADER_TEXT = "org.fcpe.fantinlatour.view.passwordAlert.headerText";

	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	
	@FXML
	private PasswordField passwordTextField;

	public OpenConseilLocalEtablissementController() {
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@Override
	protected void execute(ActionEvent event) {
		try {
			AppContext appContext = SpringFactory.getService(AppContext.ID);
			getEncryptHelper().setPassword(passwordTextField.getText());
			conseilLocalEtablissementManager.open(appContext.getConseiLocalToBeOpened());

			super.execute(event);

		} catch (PasswordException e) {

			AlertDialog exceptionAlertDialog = new AlertDialog(new Alert(AlertType.ERROR), "passwordAlert",SpringFactory.getMessage(PASSWORD_ALERT_HEADER_TEXT),SpringFactory.getMessage(PASSWORD_ALERT_TEXT));
			exceptionAlertDialog.showAndWait();

		} 
		catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
		}

	}

	private EncryptHelper getEncryptHelper() {
		EncryptHelper encryptHelper = SpringFactory.getService(EncryptHelper.ID);
		return encryptHelper;
	}

}