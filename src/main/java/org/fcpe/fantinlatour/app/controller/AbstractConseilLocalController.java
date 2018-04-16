package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.UniqueNameListener;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class AbstractConseilLocalController extends AbstractController  {

	static final String NAME_INVALID = "org.fcpe.fantinlatour.view.newconseillocal.name.tooltip.invalid";
	static final String NAME_ALREADY_EXIST = "org.fcpe.fantinlatour.view.newconseillocal.name.tooltip.alreadyExist";

	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	
	@FXML
	protected TextField nameTextField;

	UniqueNameListener uniqueNameListener;
	public AbstractConseilLocalController() {
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		
		uniqueNameListener = new UniqueNameListener(sceneValidator, conseilLocalEtablissementManager, nameTextField, "",
				resources.getString(NAME_ALREADY_EXIST), resources.getString(NAME_INVALID));
		nameTextField.textProperty().addListener(uniqueNameListener);
	
	}

	

}
