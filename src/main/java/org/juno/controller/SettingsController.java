package org.juno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import org.juno.view.NonexistingSceneException;
import org.juno.view.SettingsView;


/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final SettingsView SETTINGS_VIEW = SettingsView.getINSTANCE();
	@FXML
	public Button save;
	@FXML
	public Button back;

	@FXML
	public void savePressed() throws NonexistingSceneException {
		//TODO: save settings
		SETTINGS_VIEW.changeScene(0);
	}

	@FXML
	public void saveEntered()
	{
		save.setStyle("-fx-border-color: BLACK; -fx-background-color: transparent; -fx-border-radius: 90;");
	}

	@FXML
	public void saveExited()
	{
		save.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
	}

	@FXML
	public void backPressed() throws NonexistingSceneException
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit without saving?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.showAndWait();
		if (alert.getResult()==ButtonType.YES)
			SETTINGS_VIEW.changeScene(0);
		else
			alert.close();
	}

	@FXML
	public void backEntered()
	{
		back.setStyle("-fx-border-color: BLACK; -fx-background-color: transparent; -fx-border-radius: 90;");
	}

	@FXML
	public void backExited()
	{
		back.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
	}
}
