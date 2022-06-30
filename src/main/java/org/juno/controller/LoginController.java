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
 * Defines LoginController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class LoginController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	private static final User USER = User.getINSTANCE();


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
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ENTER)
		{
			anchor.requestFocus();
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
		USER.reset();
		USER.setNickname(usernameTextField.getText());

		StringBuilder path = new StringBuilder("file:\\").append(System.getProperty("user.dir")).append("\\src\\main\\resources\\org\\juno\\images\\");

		if (avatarRadioButton1.isSelected()) path.append("icon1.png");
		else if (avatarRadioButton2.isSelected()) path.append("icon2.png");
		else path.append("icon3.png");

		USER.setAvatar(path.toString());
		USER.save();

		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);

	}
}
