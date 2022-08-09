package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

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
	public AnchorPane backgroundRegister;


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
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ENTER)
		{
			backgroundRegister.requestFocus();
			saveData();
		}
	}


	@FXML
	public void saveClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);

		if (Objects.equals(usernameTextField.getText(), ""))
		{
			alertLabel.setVisible(true);
			return;
		}
		saveData();
	}


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
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, backgroundRegister);

	}
}
