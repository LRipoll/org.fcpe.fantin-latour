package org.fcpe.fantinlatour.app.controller.validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;

public class PasswordValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private String tooltipText;

	public PasswordValidator(SceneValidator sceneValidator, PasswordField password, String validTootipText,
			String invalidTootipText) {
		super(sceneValidator, password, validTootipText, invalidTootipText);

	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		updateTooltipText(oldValue, newValue);
		updateControl();
	}

	private void updateTooltipText(String oldValue, String newValue) {
		tooltipText = validTootipText;
		if (oldValue == null) {
			tooltipText = invalidTootipText;
		}
		
	}

}
