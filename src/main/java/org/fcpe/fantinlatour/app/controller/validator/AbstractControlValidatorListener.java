package org.fcpe.fantinlatour.app.controller.validator;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public abstract class AbstractControlValidatorListener extends AbstractValidatorListener {
	private Control control;

	public AbstractControlValidatorListener(SceneValidator sceneValidator, Control control, String validTootipText, String invalidTootipText) {
		super(sceneValidator, validTootipText, invalidTootipText);
		this.control = control;
		
	}

	@Override
	void updateStyle() {
		if (!isValid) {
			control.getStyleClass().add(TEXT_FIELD_ERROR);
		} else {
			control.getStyleClass().remove(TEXT_FIELD_ERROR);
		}
	}
	
	@Override
	String updateTooltipText() {
		String tooltipText = super.updateTooltipText();
		control.setTooltip(new Tooltip(tooltipText));
		return tooltipText;
	}


	

}