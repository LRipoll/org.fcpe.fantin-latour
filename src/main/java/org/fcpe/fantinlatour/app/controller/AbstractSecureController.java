package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;

public class AbstractSecureController extends AbstractController {

	private static final String PASSWORD_INVALID = "org.fcpe.fantinlatour.view.openconseillocal.password.tooltip.invalid";
	private static final String PASSWORD_VALID = "org.fcpe.fantinlatour.view.openconseillocal.password.tooltip.valid";
	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	
	@FXML
	protected PasswordField passwordTextField;

	public AbstractSecureController() {
		super();
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		PasswordValidator passwordValidator = new PasswordValidator(sceneValidator, passwordTextField,
				resources.getString(PASSWORD_VALID), resources.getString(PASSWORD_INVALID));
		passwordTextField.textProperty().addListener(passwordValidator);
	}

	protected EncryptHelper getEncryptHelper() {
		EncryptHelper encryptHelper = SpringFactory.getService(EncryptHelper.ID);
		return encryptHelper;
	}

}