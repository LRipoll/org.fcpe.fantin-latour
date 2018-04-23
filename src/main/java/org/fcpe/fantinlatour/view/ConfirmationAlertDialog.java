package org.fcpe.fantinlatour.view;

import javafx.scene.control.Alert;

public class ConfirmationAlertDialog extends AlertDialog {

	private static final String NAME = "confirmationAlert";

	public ConfirmationAlertDialog(Alert alert, String headerText, String contentText) {
		super(alert, NAME, headerText, contentText);

	}

}
