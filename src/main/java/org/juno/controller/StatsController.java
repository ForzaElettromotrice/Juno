package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Defines StatsNew ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class StatsController
{

	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

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
	public RadioButton avatar1;
	@FXML
	public RadioButton avatar2;
	@FXML
	public RadioButton avatar3;


	@FXML
	public Button backButton;
	@FXML
	public Button changeAvatarButton;
	@FXML
	public Button openButton;

	@FXML
	public ToggleButton changeName;


	@FXML
	public void anchorClicked()
	{
		changeName.setSelected(false);
		nameTextfield.setEditable(false);
		anchor.requestFocus();
	}


	@FXML
	public void openEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
		openButton.setUnderline(true);
	}
	@FXML
	public void changeAvatarEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
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
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
		circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\icon1.png", USER_DIR))));
		avatarBox.setVisible(false);
	}
	@FXML
	public void avatar2Clicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
		circle.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\icon2.png", USER_DIR))));
		avatarBox.setVisible(false);
	}
	@FXML
	public void avatar3Clicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
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

			String[] name = path.getName().split("\\.");
			String extension = name[name.length - 1];

			File dest = new File(USER_DIR + "\\src\\main\\resources\\org\\juno\\images\\" + Objects.hash(User.getInstance().getNickname()) + "_imported." + extension);

			try
			{
				Files.deleteIfExists(Path.of(dest.getPath()));
				Files.copy(Path.of(path.getPath()), Path.of(dest.getPath()));
			} catch (IOException err)
			{
				System.out.println(err.getMessage());
				err.printStackTrace();
			}

			circle.setFill(new ImagePattern(new Image(String.format("file:\\%s", dest.getPath()))));
			saveData();
		}

		avatarBox.setVisible(false);
	}
	@FXML
	public void changeAvatarClicked()
	{
		String oldAvatar = ((ImagePattern) circle.getFill()).getImage().getUrl();
		User.getInstance().setAvatar(oldAvatar);
		anchor.requestFocus();
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		avatarBox.setVisible(!avatarBox.isVisible());

		if (oldAvatar.endsWith(new File(((ImageView) avatar1.getChildrenUnmodifiable().get(0)).getImage().getUrl()).getName()))
			avatar.selectToggle(avatar1);
		else if (oldAvatar.endsWith(new File(((ImageView) avatar2.getChildrenUnmodifiable().get(0)).getImage().getUrl()).getName()))
			avatar.selectToggle(avatar2);
		else if (oldAvatar.endsWith(new File(((ImageView) avatar3.getChildrenUnmodifiable().get(0)).getImage().getUrl()).getName()))
			avatar.selectToggle(avatar3);
		else
			avatar.selectToggle(null);
	}
	@FXML
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ESCAPE)
			avatarBox.setVisible(false);
		else if (keyEvent.getCode() == KeyCode.ENTER)
			anchorClicked();
	}
	@FXML
	public void changeNameClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		if (changeName.isSelected())
		{
			nameTextfield.setEditable(true);
			nameTextfield.requestFocus();
		} else
		{
			anchorClicked();
		}
	}

	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}


	private void saveData()
	{
		try
		{
			Files.delete(Path.of(USER_DIR + "\\src\\main\\resources\\org\\juno\\model\\user\\" + User.getInstance().getNickname() + ".txt"));
		} catch (IOException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}
		User.getInstance().setNickname(nameTextfield.getText());
		User.getInstance().setAvatar(((ImagePattern) circle.getFill()).getImage().getUrl());
		User.save();
	}

	public void load()
	{
		avatarBox.setVisible(false);

		nameTextfield.setText(User.getInstance().getNickname());
		nameTextfield.setEditable(false);
		changeName.setSelected(false);
		victoriesCountLabel.setText("" + User.getInstance().getVictories());
		defeatsCountLabel.setText("" + User.getInstance().getDefeats());
		matchesCountLabel.setText("" + User.getInstance().getTotalMatches());

		levelLabel.setText("" + User.getInstance().getLevel());
		levelProgressBar.setProgress(User.getInstance().getProgress());


		circle.setFill(new ImagePattern(new Image(User.getInstance().getAvatar())));
	}
}
