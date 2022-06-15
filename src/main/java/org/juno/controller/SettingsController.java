package org.juno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.NonexistingSceneException;
import org.juno.view.SettingsView;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final SettingsView SETTINGS_VIEW = SettingsView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	@FXML
	public Slider effectsBar;
	@FXML
	public Slider musicBar;
	@FXML
	public Button reset;
	@FXML
	public Button delete;
	@FXML
	public Button save;
	@FXML
	public Button back;

	@FXML
	public void savePressed() throws NonexistingSceneException
	{
		AUDIO_PLAYER.setMusicVolume(musicBar.getValue() / 100);
		AUDIO_PLAYER.setEffectsVolume(effectsBar.getValue() / 100);
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
	@FXML
	public void resetClicked(ActionEvent actionEvent) throws NonexistingSceneException
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reset your data?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Warning!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{
			User.getINSTANCE().reset();
		} else
			alert.close();
		alert = new Alert(Alert.AlertType.INFORMATION, "Data resetted!");
		alert.setTitle("Success");
		alert.setHeaderText("Success!");
		alert.showAndWait();
	}
	@FXML
	public void deleteClicked(ActionEvent actionEvent) throws NonexistingSceneException
	{
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete your data?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Warning!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{
			SETTINGS_VIEW.changeScene(-1);
		} else
			alert.close();
	}
	@FXML
	public void resetEntered(MouseEvent mouseEvent)
	{
		reset.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 5;");
	}
	@FXML
	public void deleteEntered(MouseEvent mouseEvent)
	{
		delete.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 5;");
	}
	@FXML
	public void resetExited(MouseEvent mouseEvent)
	{
		reset.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 5;");
	}
	@FXML
	public void deleteExited(MouseEvent mouseEvent)
	{
		delete.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 5;");
	}
	public void load()
	{
		musicBar.setValue(AUDIO_PLAYER.getMusicVolume() * 100);
		effectsBar.setValue(AUDIO_PLAYER.getEffectsVolume() * 100);
	}

}
