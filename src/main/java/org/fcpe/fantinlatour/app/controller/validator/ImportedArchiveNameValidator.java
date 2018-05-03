package org.fcpe.fantinlatour.app.controller.validator;

import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ImportedArchiveNameValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	private String alreadyExistTootipText;
	private String tooltipText;

	public ImportedArchiveNameValidator(SceneValidator sceneValidator,
			ConseilLocalEtablissementManager conseilLocalEtablissementManager, TextField nameTextField,
			String validTootipText, String alreadyExistTootipText, String invalidTootipText) {
		super(sceneValidator, nameTextField, validTootipText, invalidTootipText);

		this.conseilLocalEtablissementManager = conseilLocalEtablissementManager;
		this.alreadyExistTootipText = alreadyExistTootipText;

	}

	@Override
	public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

		updateTooltipText(newValue);
		updateControl();
	}

	private void updateTooltipText(String newValue) {
		tooltipText = validTootipText;
		if (conseilLocalEtablissementManager.existsFromArchiveFilename(newValue)) {

			tooltipText = alreadyExistTootipText;
		} else if (!conseilLocalEtablissementManager.isValidFromArchiveFilename(newValue)) {

			tooltipText = invalidTootipText;

		}

	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}
	
}
