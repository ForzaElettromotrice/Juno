package org.juno.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.User;
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
	public void musicMouseReleased()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
		haveToSave = true;
		saveButton.setDisable(false);
		AUDIO_PLAYER.setMusicVolume(musicSlider.getValue() / 100);
	}
	@FXML
	public void effectsMouseReleased()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
		haveToSave = true;
		saveButton.setDisable(false);
		AUDIO_PLAYER.setEffectsVolume(effectsSlider.getValue() / 100);
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

		saveData();
		
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);

	}
	@FXML
	public void switchClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.MENUMUSIC);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.LOGINMUSIC);
		((LoginController) GEN_VIEW.getLogin().getUserData()).load();
		GEN_VIEW.changeScene(GenView.SCENES.LOGIN, anchor);
	}
	@FXML
	public void saveClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		saveButton.setDisable(true);
		getData();
		saveData();
	}

	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}


	private void saveData()
	{
		AUDIO_PLAYER.setMusicVolume(musicActual);
		AUDIO_PLAYER.setEffectsVolume(effectsActual);
		User.getInstance().setMusicVolume(musicActual);
		User.getInstance().setEffectsVolume(effectsActual);

		User.save();

		haveToSave = false;
	}

	public void getData()
	{
		musicActual = musicSlider.getValue() / 100;
		effectsActual = effectsSlider.getValue() / 100;
	}

	public void load()
	{
		haveToSave = false;
		musicActual = AUDIO_PLAYER.getMusicVolume();
		effectsActual = AUDIO_PLAYER.getEffectsVolume();

		musicSlider.setValue(musicActual * 100);
		effectsSlider.setValue(effectsActual * 100);

		saveButton.setDisable(true);
	}
}
