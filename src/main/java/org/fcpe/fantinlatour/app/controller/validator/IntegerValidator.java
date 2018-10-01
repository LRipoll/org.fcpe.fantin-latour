package org.fcpe.fantinlatour.app.controller.validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class IntegerValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private String tooltipText;
	private int min;
	private int max;
	
	public IntegerValidator(SceneValidator sceneValidator, TextField nameTextField, String validTootipText,
			String invalidTootipText, int min, int max) {
		super(sceneValidator, nameTextField, validTootipText, invalidTootipText);
		this.min = min;
		this.max = max;
	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		updateTooltipText(newValue);
		updateControl();
		
	}
	
	private void updateTooltipText(String newValue) {
		tooltipText = validTootipText;
		
		if (!org.apache.commons.validator.routines.IntegerValidator.getInstance().isValid(newValue)) {

			tooltipText = invalidTootipText;

		} else {
			int value = org.apache.commons.validator.routines.IntegerValidator.getInstance().validate(newValue);
			if (!org.apache.commons.validator.routines.IntegerValidator.getInstance().isInRange(value, min, max))  {
				tooltipText = invalidTootipText;
			}
		}

	}
	
	

}
