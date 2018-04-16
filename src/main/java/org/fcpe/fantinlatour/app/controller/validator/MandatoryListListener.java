package org.fcpe.fantinlatour.app.controller.validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;

public class MandatoryListListener<T> extends AbstractControlValidatorListener implements ChangeListener<T> {


	private String tooltipText;

	public MandatoryListListener(SceneValidator sceneValidator, ComboBox<T> typeComboBox, String validTootipText,
			String invalidTootipText) {
		super(sceneValidator, typeComboBox, validTootipText, invalidTootipText);

	}

	private void updateTooltipText(T newValue) {
		tooltipText = validTootipText;
		
		if (newValue == null) {
			tooltipText = invalidTootipText;
		}

	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
		updateTooltipText(newValue);
		updateControl();
		
	}

}
