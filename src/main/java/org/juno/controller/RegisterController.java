package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * Defines RegisterController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class RegisterController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();


	@FXML
	public AnchorPane anchor;


	@FXML
	public TextField usernameTextField;


	@FXML
	public ToggleGroup avatar;


	@FXML
	public RadioButton avatarRadioButton1;
	@FXML
	public RadioButton avatarRadioButton2;
	@FXML
	public RadioButton avatarRadioButton3;


	@FXML
	public Label alertLabel;


	@FXML
	public Button saveButton;
	@FXML
	public Button loginButton;

	/**
	 * called when the user press a key on the keyboard
	 *
	 * @param keyEvent the key pressed
	 */
	@FXML
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ENTER)
		{
			anchor.requestFocus();
			saveData();
		}
	}

	/**
	 * called when the user clicks on "save" button
	 */
	@FXML
	public void saveClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);

		File dir = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\model\\user");
		String erroMessage;
		if (!Objects.equals(usernameTextField.getText(), ""))
		{
			if (Arrays.stream(Objects.requireNonNull(dir.list())).findAny().filter(x -> x.equals(usernameTextField.getText() + ".txt")).isPresent())
			{
				erroMessage = "Username already taken";
				alertLabel.setText(erroMessage);
				alertLabel.setVisible(true);
				return;
			}
		} else
		{
			erroMessage = "You have to choose a username!";
			alertLabel.setText(erroMessage);
			alertLabel.setVisible(true);
			return;
		}

		saveData();
	}

	/**
	 * save the data of the user in the User class
	 */
	public void saveData()
	{
		User.getInstance().reset();
		User.getInstance().setNickname(usernameTextField.getText());

		StringBuilder path = new StringBuilder("file:\\").append(System.getProperty("user.dir")).append("\\src\\main\\resources\\org\\juno\\images\\");

		if (avatarRadioButton1.isSelected()) path.append("icon1.png");
		else if (avatarRadioButton2.isSelected()) path.append("icon2.png");
		else path.append("icon3.png");

		User.getInstance().setAvatar(path.toString());
		User.save();

		AUDIO_PLAYER.stop(AudioPlayer.Sounds.LOGINMUSIC);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.MENUMUSIC);
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);

	}
	/**
	 * called when the user clicks on "login" button
	 */
	@FXML
	public void loginClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		((LoginController) GEN_VIEW.getLogin().getUserData()).load();
		GEN_VIEW.changeScene(GenView.SCENES.LOGIN, anchor);
	}

	/**
	 * called when the scene is shown
	 * it initialize the scene
	 */
	public void load()
	{
		anchor.requestFocus();
		usernameTextField.clear();
		alertLabel.setVisible(false);
		avatarRadioButton1.setSelected(true);
	}

	/**
	 * called when the mouse enter the "save" button
	 */
	@FXML
	public void saveEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}

	/**
	 * called when the user clicks on the avatar
	 */
	@FXML
	public void avatarClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
	}
}
