package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
//TODO: RIFARE LA GRAFICA DA SCENEBUILDER FATTA MEGLIO

/**
 * Defines SettingsNew ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class SettingsController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	private boolean haveToSave;


	@FXML
	public AnchorPane anchor;


	@FXML
	public Button backButton;
	@FXML
	public Button resetButton;
	@FXML
	public Button saveButton;


	@FXML
	public Slider musicSlider;
	@FXML
	public Slider effectsSlider;


	private double musicActual;
	private double effectsActual;


	@FXML
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ENTER)
		{
			anchor.requestFocus();
			saveData();
		}
	}


	@FXML
	public void resetEntered()
	{
//		resetButton.setStyle("-fx-background-color: RED;-fx-background-radius: 100; -fx-border-color: RED; -fx-border-radius: 100;");
	}


	@FXML
	public void resetExited()
	{
//		resetButton.setStyle("-fx-background-color: transparent; -fx-border-color: RED; -fx-border-radius: 100;");
	}


	@FXML
	public void musicMouseReleased()
	{
		haveToSave = true;
		saveButton.setDisable(false);
		AUDIO_PLAYER.setMusicVolume(musicSlider.getValue() / 100);
		//TODO: fare che quando lo muovi cambia colore
	}
	@FXML
	public void effectsMouseReleased()
	{
		haveToSave = true;
		saveButton.setDisable(false);
		AUDIO_PLAYER.setEffectsVolume(effectsSlider.getValue() / 100);
		//TODO: fare che quando lo muovi cambia colore
	}


	@FXML
	public void backClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		if (haveToSave)
		{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit without saving?", ButtonType.YES, ButtonType.NO);
			alert.setTitle("Confirm");
			alert.setHeaderText("Confirm");
			alert.showAndWait();
			if (alert.getResult() == ButtonType.NO) return;
		}

		AUDIO_PLAYER.setMusicVolume(musicActual);
		AUDIO_PLAYER.setEffectsVolume(effectsActual);

		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);

	}
	@FXML
	public void resetClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reset your account?\nYou will be redirected to the login page", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Confirm");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) return;

		GEN_VIEW.changeScene(GenView.SCENES.LOGIN, anchor);

	}
	@FXML
	public void saveClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		saveButton.setDisable(true);
		saveData();
	}


	private void saveData()
	{
		musicActual = musicSlider.getValue() / 100;
		effectsActual = effectsSlider.getValue() / 100;

		haveToSave = false;
	}
	public void load()
	{
		musicActual = AUDIO_PLAYER.getMusicVolume();
		effectsActual = AUDIO_PLAYER.getEffectsVolume();

		musicSlider.setValue(musicActual * 100);
		effectsSlider.setValue(effectsActual * 100);

		saveButton.setDisable(true);
	}

}
