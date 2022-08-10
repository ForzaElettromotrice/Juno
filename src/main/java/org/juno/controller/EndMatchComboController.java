package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.juno.datapackage.PointsData;
import org.juno.model.table.TurnOrder;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

/**
 * Defines EndMatchComboController class,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class EndMatchComboController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	@FXML
	public AnchorPane anchorPane;

	@FXML
	public Label labelPlayer;
	@FXML
	public Label labelBot1;
	@FXML
	public Label labelBot2;
	@FXML
	public Label labelBot3;

	@FXML
	public Circle avatarPlayer;
	@FXML
	public Circle avatarBot1;
	@FXML
	public Circle avatarBot2;
	@FXML
	public Circle avatarBot3;


	@FXML
	public void exitClicked()
	{

		buttonClick();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit? You will lose the match by default!", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirm");
		alert.setHeaderText("Confirm");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.NO) return;
		GEN_VIEW.changeScene(GenView.SCENES.ENDGAME, anchorPane);
	}

	@FXML
	public void nextMatchClicked()
	{
		buttonClick();
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCOMBO, anchorPane);

		GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.COMBO);
		((GameplayComboController) GEN_VIEW.getGameplayCombo().getUserData()).reset();
	}

	public void load(PointsData pointsData)
	{
		avatarPlayer.setFill(new ImagePattern(new Image(User.getInstance().getAvatar())));
		labelPlayer.setText(String.format("%s: %d points", User.getInstance().getNickname(), pointsData.playerPoints()));
		avatarBot1.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot1.png", System.getProperty("user.dir")))));
		labelBot1.setText(String.format("Bot 1: %d points", pointsData.bot1Points()));
		avatarBot2.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot2.png", System.getProperty("user.dir")))));
		labelBot2.setText(String.format("Bot 2: %d points", pointsData.bot2Points()));
		avatarBot3.setFill(new ImagePattern(new Image(String.format("file:\\%s\\src\\main\\resources\\org\\juno\\images\\iconBot3.png", System.getProperty("user.dir")))));
		labelBot3.setText(String.format("Bot 3: %d points", pointsData.bot3Points()));
	}

	protected void buttonClick()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
	}
}
