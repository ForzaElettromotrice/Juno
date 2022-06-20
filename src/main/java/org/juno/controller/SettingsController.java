package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;


/**
 * Defines SettingsController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
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
    public AnchorPane settingsAnchor;

    @FXML
	public void savePressed() throws NonexistingSceneException
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
		AUDIO_PLAYER.setMusicVolume(musicBar.getValue() / 100);
		AUDIO_PLAYER.setEffectsVolume(effectsBar.getValue() / 100);
		GEN_VIEW.changeScene(0, settingsAnchor);
	}

	@FXML
	public void saveEntered()
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
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
		AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit without saving?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Confirm");
		alert.showAndWait();
		if (alert.getResult()==ButtonType.YES)
			GEN_VIEW.changeScene(0, settingsAnchor);
		else
			alert.close();
	}

	@FXML
	public void backEntered()
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
		back.setStyle("-fx-border-color: BLACK; -fx-background-color: transparent; -fx-border-radius: 90;");
	}

	@FXML
	public void backExited()
	{
		back.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
	}
	@FXML
	public void resetClicked() throws NonexistingSceneException
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reset your data?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Warning!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{
			User.getINSTANCE().reset();
			alert = new Alert(Alert.AlertType.INFORMATION, "Data resetted!");
			alert.setTitle("Success");
			alert.setHeaderText("Success!");
			alert.showAndWait();
		} else
			alert.close();
	}
	@FXML
	public void deleteClicked() throws NonexistingSceneException
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.BUTTONCLICK);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete your data?", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Warning!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES)
		{
			GEN_VIEW.changeScene(-1, settingsAnchor);
		} else
			alert.close();
	}
	@FXML
	public void resetEntered()
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
		reset.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 5;");
	}
	@FXML
	public void deleteEntered()
	{
		AudioPlayer.playSound(AudioPlayer.Sounds.CURSORSELECT);
		delete.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-width: 5;");
	}
	@FXML
	public void resetExited()
	{
		reset.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 5;");
	}
	@FXML
	public void deleteExited()
	{
		delete.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-border-width: 5;");
	}
	public void load()
	{
		musicBar.setValue(AUDIO_PLAYER.getMusicVolume() * 100);
		effectsBar.setValue(AUDIO_PLAYER.getEffectsVolume() * 100);
	}

}
