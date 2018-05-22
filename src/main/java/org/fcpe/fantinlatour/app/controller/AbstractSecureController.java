package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.context.AppContext;
import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.event.ActionEvent;
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
		String conseiLocalToBeOpened = null;
		if (conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement() != null) {
			conseiLocalToBeOpened = conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement()
					.getEtablissement().getNom();
		}
		getAppContext().setCurrentConseiLocal(conseiLocalToBeOpened);

		PasswordValidator passwordValidator = new PasswordValidator(sceneValidator, passwordTextField,
				resources.getString(PASSWORD_VALID), resources.getString(PASSWORD_INVALID));
		passwordTextField.textProperty().addListener(passwordValidator);
	}

	@Override
	protected void cancel(ActionEvent event) {
		try {
			if (getAppContext().getCurrentConseiLocal() != null) {
				conseilLocalEtablissementManager.open(getAppContext().getCurrentConseiLocal());
			}
		} catch (DataException e) {

		}
		super.cancel(event);
	}

	protected EncryptHelper getEncryptHelper() {
		return SpringFactory.getService(EncryptHelper.ID);
	}

	private AppContext getAppContext() {
		return SpringFactory.getService(AppContext.ID);
	}

}