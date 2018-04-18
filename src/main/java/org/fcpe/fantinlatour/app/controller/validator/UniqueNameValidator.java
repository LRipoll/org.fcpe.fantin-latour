package org.fcpe.fantinlatour.app.controller.validator;

import org.fcpe.fantinlatour.model.controller.UniqueNameManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class UniqueNameValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private UniqueNameManager uniqueNameManager;
	private String alreadyExistTootipText;
	private String tooltipText;

	public UniqueNameValidator(SceneValidator sceneValidator,
			UniqueNameManager uniqueNameManager, TextField nameTextField,
			String validTootipText, String alreadyExistTootipText, String invalidTootipText) {
		super(sceneValidator, nameTextField, validTootipText, invalidTootipText);

		this.uniqueNameManager = uniqueNameManager;
		this.alreadyExistTootipText = alreadyExistTootipText;

	}

	@Override
	public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

		updateTooltipText(newValue);
		updateControl();
	}

	private void updateTooltipText(String newValue) {
		tooltipText = validTootipText;
		if (uniqueNameManager.exists(newValue)) {

			tooltipText = alreadyExistTootipText;

		} else if (!uniqueNameManager.isValidName(newValue)) {

			tooltipText = invalidTootipText;

		}

	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

}
