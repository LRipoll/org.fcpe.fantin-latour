package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.context.AppContext;
import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;

public class OpenConseilLocalEtablissementController extends AbstractController {


	private static final String PASSWORD_INVALID = "org.fcpe.fantinlatour.view.openconseillocal.password.tooltip.invalid";
	private static final String PASSWORD_VALID = "org.fcpe.fantinlatour.view.openconseillocal.password.tooltip.valid";

	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	
	@FXML
	private PasswordField passwordTextField;

	public OpenConseilLocalEtablissementController() {
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		PasswordValidator passwordValidator = new PasswordValidator(sceneValidator, passwordTextField,
				resources.getString(PASSWORD_VALID), resources.getString(PASSWORD_INVALID));
		passwordTextField.textProperty().addListener(passwordValidator);
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

	private EncryptHelper getEncryptHelper() {
		EncryptHelper encryptHelper = SpringFactory.getService(EncryptHelper.ID);
		return encryptHelper;
	}

}
