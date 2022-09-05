package org.juno.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
	private static final String STYLE = "-fx-border-color: BLACK; -fx-border-width: 0px 3px 0px 0px";
	private static final String GARBAGE = "file:\\" + System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\garbage.png";


	private String accountChosen;

	@FXML
	public AnchorPane backgroundLogin;
	@FXML
	public VBox boxAccount;
	@FXML
	public Button commitButton;
	@FXML
	public ToggleButton minusToggle;

	/**
	 * called when the user press a key on the keyboard
	 *
	 * @param keyEvent the key pressed
	 */
	@FXML
	public void anchorKeyPressed(KeyEvent keyEvent)
	{
		if (keyEvent.getCode() == KeyCode.ESCAPE)
		{
			boxAccount.getChildren().forEach(child -> ((HBox) child).getChildren().get(5).setVisible(false));
			minusToggle.setSelected(false);
			((ImageView) minusToggle.getChildrenUnmodifiable().get(0)).setImage(new Image("file:\\" + System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\" + "minus.png"));
		} else if (keyEvent.getCode() == KeyCode.ENTER)
			commit();
	}

	/**
	 * called when the user clicks on the "minus" button
	 */
	@FXML
	public void minusClicked()
	{
		commitButton.setDisable(true);
		click();
		accountChosen = "";
		boxAccount.getChildren().forEach(child ->
		{
			child.setUserData(false);
			child.setStyle("-fx-border-color: BLACK; -fx-border-width: 0px 0px 3px 0px;");
			((HBox) child).getChildren().get(5).setVisible(minusToggle.isSelected());
		});
		((ImageView) minusToggle.getChildrenUnmodifiable().get(0)).setImage(new Image("file:\\" + System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\" + (minusToggle.isSelected() ? "check.png" : "minus.png")));

	}

	/**
	 * called when the user clicks on the "commit" button
	 */
	@FXML
	public void commit()
	{
		click();
		if (accountChosen.equals("")) return;

		User.load(accountChosen + ".txt");
		AUDIO_PLAYER.setMusicVolume(User.getInstance().getMusicVolume());
		AUDIO_PLAYER.setEffectsVolume(User.getInstance().getEffectsVolume());
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.LOGINMUSIC);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.MENUMUSIC);
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, backgroundLogin);

	}

	/**
	 * called when the user clicks on the "plus" button
	 */
	@FXML
	public void plusClicked()
	{
		click();
		minusToggle.setSelected(false);
		((ImageView) minusToggle.getChildrenUnmodifiable().get(0)).setImage(new Image("file:\\" + System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\images\\minus.png"));
		((RegisterController) GEN_VIEW.getRegister().getUserData()).load();
		GEN_VIEW.changeScene(GenView.SCENES.REGISTER, backgroundLogin);
	}

	/**
	 * play button entered sound
	 */
	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}

	/**
	 * called when the mouse clicks on the "garbage" button
	 *
	 * @param event the mouse event
	 */
	@FXML
	public void garbageClicked(MouseEvent event)
	{
		HBox toRemove = ((HBox) ((StackPane) event.getSource()).getParent());

		try
		{
			Files.delete(Path.of(System.getProperty("user.dir") + "/src/main/resources/org/juno/model/user/" + ((Label) toRemove.getChildren().get(1)).getText() + ".txt"));
		} catch (IOException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
		}


		((VBox) toRemove.getParent()).getChildren().remove(toRemove);
	}

	/**
	 * called when the user clicks on an account
	 *
	 * @param event the mouse event
	 */
	@FXML
	public void hBoxClicked(MouseEvent event)
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.ALERTBEEP);
		if (!minusToggle.isSelected())
		{
			HBox box = ((HBox) event.getSource());

			if (accountChosen.equals(((Label) box.getChildren().get(1)).getText()))
				commit();

			boxAccount.getChildren().stream().filter(child -> !child.equals(box)).forEach(child ->
			{
				child.setUserData(false);
				child.setStyle("-fx-border-color: BLACK; -fx-border-width: 0px 0px 3px 0px;");
			});
			box.setUserData(true);

			accountChosen = ((Label) box.getChildren().get(1)).getText();

			commitButton.setDisable(false);
		}

	}

	/**
	 * called when the mouse enters an account
	 *
	 * @param event the mouse event
	 */
	@FXML
	public void hBoxEntered(MouseEvent event)
	{
		((HBox) event.getSource()).setStyle("-fx-border-color: BLACK; -fx-border-width: 0px 0px 3px 0px; -fx-background-color: YELLOW");
	}

	/**
	 * called when the mouse exits an account
	 *
	 * @param event the mouse event
	 */
	@FXML
	public void hBoxExited(MouseEvent event)
	{
		HBox box = (HBox) event.getSource();
		if (!(boolean) box.getUserData())
		{
			box.setStyle("-fx-border-color: BLACK; -fx-border-width: 0px 0px 3px 0px;");
		}
	}

	/**
	 * called when the scene is shown
	 */
	public void load()
	{
		backgroundLogin.requestFocus();
		accountChosen = "";
		boxAccount.getChildren().clear();
		File dir = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\org\\juno\\model\\user");

		if (Objects.requireNonNull(dir.list()).length == 0)
		{
			((RegisterController) GEN_VIEW.getRegister().getUserData()).load();
			GEN_VIEW.changeScene(GenView.SCENES.REGISTER, backgroundLogin);
		}

		HBox hBox;

		User user;

		for (String name : Objects.requireNonNull(dir.list()))
		{
			hBox = new HBox();

			user = User.load(name);


			ImageView avatar = new ImageView(new Image(user.getAvatar()));
			avatar.setFitWidth(65);
			avatar.setFitHeight(65);

			StackPane stackPane = new StackPane();
			stackPane.setAlignment(Pos.CENTER);
			stackPane.setPrefWidth(75);
			stackPane.setPrefHeight(75);
			stackPane.getChildren().add(avatar);


			Label nickname = new Label(user.getNickname());
			nickname.setAlignment(Pos.CENTER_LEFT);
			nickname.setFont(new Font(40));
			nickname.setPrefWidth(300);
			nickname.setPrefHeight(75);
			nickname.setStyle(STYLE);

			Label lvl = new Label("" + user.getLevel());
			lvl.setAlignment(Pos.CENTER);
			lvl.setFont(new Font(40));
			lvl.setPrefWidth(374);
			lvl.setPrefHeight(75);
			lvl.setStyle(STYLE);

			Label totalExp = new Label("" + user.getTotalExp());
			totalExp.setAlignment(Pos.CENTER);
			totalExp.setFont(new Font(40));
			totalExp.setPrefWidth(375);
			totalExp.setPrefHeight(75);
			totalExp.setStyle(STYLE);

			Label totalMatches = new Label("" + user.getTotalMatches());
			totalMatches.setAlignment(Pos.CENTER);
			totalMatches.setFont(new Font(40));
			totalMatches.setPrefWidth(300);
			totalMatches.setPrefHeight(75);
			totalMatches.setPadding(new Insets(0, 0, 0, 75));

			ImageView minusImage = new ImageView(new Image(GARBAGE));
			minusImage.setFitWidth(65);
			minusImage.setFitHeight(65);

			StackPane minus = new StackPane();
			minus.setAlignment(Pos.CENTER);
			minus.setPrefWidth(75);
			minus.setPrefHeight(75);
			minus.setLayoutX(-65);
			minus.getChildren().add(minusImage);
			minus.setVisible(false);
			minus.setOnMouseClicked(this::garbageClicked);


			hBox.getChildren().addAll(stackPane, nickname, lvl, totalExp, totalMatches, minus);


			hBox.setStyle("-fx-border-color: BLACK; -fx-border-width: 0px 0px 3px 0px;");
			hBox.setUserData(false);

			hBox.setOnMouseEntered(this::hBoxEntered);
			hBox.setOnMouseExited(this::hBoxExited);

			hBox.setOnMouseClicked(this::hBoxClicked);

			boxAccount.getChildren().add(hBox);
			AUDIO_PLAYER.setMusicVolume(10);
		}
	}

	/**
	 * plays the click sound
	 */
	public void click()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
	}
}
