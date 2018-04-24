package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.UniqueNameValidator;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class AbstractConseilLocalController extends AbstractController {

	static final String NAME_INVALID = "org.fcpe.fantinlatour.view.newconseillocal.name.tooltip.invalid";
	static final String NAME_ALREADY_EXIST = "org.fcpe.fantinlatour.view.newconseillocal.name.tooltip.alreadyExist";
	static final String NAME_VALID = "org.fcpe.fantinlatour.view.newconseillocal.name.tooltip.valid";

	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@FXML
	protected TextField nameTextField;

	UniqueNameValidator uniqueNameValidator;

	public AbstractConseilLocalController() {
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		uniqueNameValidator = new UniqueNameValidator(sceneValidator, conseilLocalEtablissementManager, nameTextField,
				resources.getString(NAME_VALID), resources.getString(NAME_ALREADY_EXIST),
				resources.getString(NAME_INVALID));
		nameTextField.textProperty().addListener(uniqueNameValidator);

	}

}
