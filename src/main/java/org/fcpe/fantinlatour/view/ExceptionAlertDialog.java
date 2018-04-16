package org.fcpe.fantinlatour.view;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class ExceptionAlertDialog extends AlertDialog {

	private static final String HEADER_TEXT = "org.fcpe.fantinlatour.view.exceptionAlert.headerText";
	private static final String NAME = "exceptionAlert";
	private static final String STACKTRACE_TEXT = "org.fcpe.fantinlatour.view.exceptionAlert.stackTrace";
	

	public ExceptionAlertDialog(Alert alert, Exception exception) {
		super(alert,NAME,HEADER_TEXT,exception.getMessage());
		
		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label(SpringFactory.getMessage(STACKTRACE_TEXT));

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);
		

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().getExpandableContent().setCache(true);
	}

	

	

}
