package org.fcpe.fantinlatour.app.controller.validator;

import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public abstract class AbstractControlValidatorListener implements ControlValidator {
	static final String TEXT_FIELD_ERROR = "textFieldError";
	
	protected String validTootipText;
	protected String invalidTootipText;
	protected boolean isValid;
	private Control control;

	private SceneValidator sceneValidator;

	public AbstractControlValidatorListener(SceneValidator sceneValidator, Control control, String validTootipText, String invalidTootipText) {
		super();
		this.control = control;
		this.validTootipText = validTootipText;
		this.invalidTootipText = invalidTootipText;
		this.sceneValidator = sceneValidator;
		sceneValidator.add(this);
		isValid = false;
	}


	protected abstract String getTooltipText();

	protected void updateControl() {
		updateTooltipText();
		updateStyle();
	}
	
	private void updateStyle() {
		if (!isValid) {
			control.getStyleClass().add(TEXT_FIELD_ERROR);
		} else {
			control.getStyleClass().remove(TEXT_FIELD_ERROR);
		}
	}
	

	private void updateTooltipText() {
		String tooltipText = getTooltipText();

		boolean wasValid = isValid;
		
		isValid = tooltipText.equals(validTootipText);
		if (isValid!=wasValid) {
			sceneValidator.onStateChange(this);
		}
		control.setTooltip(new Tooltip(tooltipText));
	}
	/* (non-Javadoc)
	 * @see org.fcpe.fantinlatour.app.controller.validator.ControlValidator#isValid()
	 */
	@Override
	public boolean isValid() {
		return isValid;
	}


	

}