package org.juno.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import org.juno.datapackage.BuildMP;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;


/**
 * Defines EndgameController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class EndgameController
{

	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	private Table table;

	@FXML
	public AnchorPane backgroundEndagame;
	@FXML
	public ProgressBar progressBar;
	@FXML
	public Circle avatar;
	@FXML
	public Label actualLevel;
	@FXML
	public Label nextLevel;
	@FXML
	public Label resultsMessage;
	@FXML
	public Label levelUp;


	/**
	 * called when the user clicks on the "main menu" button
	 */
	@FXML
	public void mainMenu()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, backgroundEndagame);
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.GAMEMUSIC);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.MENUMUSIC);
	}

	/**
	 * called when the user clicks on the "new match" button
	 */
	@FXML
	public void newMatch()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);

		if (table instanceof TableClassic)
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.CLASSIC);
			GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCLASSIC, backgroundEndagame);
		} else if (table instanceof TableCombo)
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.COMBO);
			GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCOMBO, backgroundEndagame);
		} else
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.TRADE);
			GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYTRADE, backgroundEndagame);
		}
		new Thread(table).start();
	}

	/**
	 * plays "button entered" sound
	 */
	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}

	/**
	 * load the exp earned by the user in the game
	 *
	 * @param currentTable the table of the game
	 */
	public void load(Table currentTable)
	{
		int points = currentTable.getUser().getPoints();

		if (!currentTable.getStopEarlier() && currentTable.getWinner().getId() == BuildMP.PG.PLAYER)
		{
			User.getInstance().addVictories();
			resultsMessage.setText(String.format("You won! You gained %d exp!", points));
		} else {
			User.getInstance().addDefeats();
			resultsMessage.setText(String.format("You lost! You gained %d exp!", points));
		}

		levelUp.setVisible(false);
		table = currentTable;

		avatar.setFill(new ImagePattern(new Image(String.format(User.getInstance().getAvatar()))));
		progressBar.setProgress(User.getInstance().getProgress());

		int prevLvl = User.getInstance().getLevel();

		User.getInstance().addExp(points);

		int lvl = User.getInstance().getLevel();
		actualLevel.setText("" + lvl);
		nextLevel.setText("" + (lvl + 1));

		if (prevLvl != lvl)
		{
			progressBar.setProgress(0);
			levelUp.setVisible(true);
		}
		Platform.runLater(this::animation);
	}

	/**
	 * animation of the exp bar
	 */
	public void animation()
	{
		Timeline timeline = new Timeline();

		KeyValue keyValue = new KeyValue(progressBar.progressProperty(), User.getInstance().getProgress());
		KeyFrame keyFrame = new KeyFrame(new Duration(3000), keyValue);
		timeline.getKeyFrames().add(keyFrame);

		timeline.play();

	}
}
