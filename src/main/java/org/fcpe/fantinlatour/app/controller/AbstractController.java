package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.SceneValidator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Window;

public class AbstractController implements Initializable {

	@FXML
	protected Button okButton;
	protected SceneValidator sceneValidator;

	public AbstractController() {
		super();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sceneValidator = new SceneValidator(okButton);

	}

	@FXML
	protected void cancel(ActionEvent event) {
		getStage().hide();
	}

	protected Window getStage() {

		return okButton.getScene().getWindow();
	}

	@FXML
	protected void execute(ActionEvent event) {
		getStage().hide();
	}

}