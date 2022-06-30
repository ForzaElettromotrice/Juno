package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;

/**
 * Defines StatsNew ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class StatsController
{

	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	private static final User USER = User.getINSTANCE();

	private static final String USER_DIR = System.getProperty("user.dir");

	@FXML
	public AnchorPane anchor;


	@FXML
	public VBox avatarBox;


	@FXML
	public Circle circle;
	@FXML
	public Circle circleOpaque;


	@FXML
	public Label victoriesCountLabel;
	@FXML
	public Label defeatsCountLabel;
	@FXML
	public Label matchesCountLabel;
	@FXML
	public Label levelLabel;


	@FXML
	public ProgressBar levelProgressBar;


	@FXML
	public TextField nameTextfield;


	@FXML
	public ToggleGroup avatar;


	@FXML
	public Button backButton;
	@FXML
	public Button changeAvatarButton;
	@FXML
	public Button openButton;


	@FXML
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ENTER)
		{
			saveData();
			anchor.requestFocus();
		}
	}


	@FXML
	public void openEntered()
	{
		openButton.setUnderline(true);
	}
	@FXML
	public void changeAvatarEntered()
	{
		circleOpaque.setVisible(true);
		changeAvatarButton.setVisible(true);
	}


	@FXML
	public void openExited()
	{
		openButton.setUnderline(false);
	}
	@FXML
	public void changeAvatarExited()
	{
		circleOpaque.setVisible(false);
		changeAvatarButton.setVisible(false);
	}


	@FXML
	public void backClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		saveData();
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);
	}
	@FXML
	public void avatar1Clicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\icon1.png", USER_DIR))));
		avatarBox.setVisible(false);
	}
	@FXML
	public void avatar2Clicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\icon2.png", USER_DIR))));
		avatarBox.setVisible(false);
	}
	@FXML
	public void avatar3Clicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\icon3.png", USER_DIR))));
		avatarBox.setVisible(false);
	}
	@FXML
	public void openClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		final FileChooser fc = new FileChooser();
		fc.setTitle("Apri...");
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Image Files", "*.jpeg", "*.bmp", "*.png", "*.webmp", "*.gif", "*.jpg"),
				new FileChooser.ExtensionFilter("All Files", "*.*"));
		File path = fc.showOpenDialog(GEN_VIEW.getWindow());

		if (path != null)
		{
			circle.setFill(new ImagePattern(new Image(String.format("file:\\%s", path.getPath()))));
			saveData();
		}

		avatarBox.setVisible(false);
	}
	@FXML
	public void changeAvatarClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		avatarBox.setVisible(true);
	}


	private void saveData()
	{
		USER.setNickname(nameTextfield.getText());
		USER.setAvatar(((ImagePattern) circle.getFill()).getImage().getUrl());
	}

	public void load()
	{
		avatarBox.setVisible(false);

		nameTextfield.setText(USER.getNickname());
		victoriesCountLabel.setText("" + USER.getVictories());
		defeatsCountLabel.setText("" + USER.getDefeats());
		matchesCountLabel.setText("" + USER.getTotalMatches());

		levelLabel.setText("" + USER.getLevel());
		levelProgressBar.setProgress(USER.getProgress());


		circle.setFill(new ImagePattern(new Image(USER.getAvatar())));
	}

}
