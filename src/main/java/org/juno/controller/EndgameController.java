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
	private static final User USER = User.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();

	private Table table;

	@FXML
	public AnchorPane anchorPane;
	@FXML
	public ProgressBar progressBar;
	@FXML
	public Circle avatar;
	@FXML
	public Label actualLevel;
	@FXML
	public Label nextLevel;
	@FXML
	public Label expMessage;


	@FXML
	public void mainMenu()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchorPane);
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.GAMEMUSIC);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.MENUMUSIC);
	}


	@FXML
	public void newMatch()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);

		if (table instanceof TableClassic)
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.CLASSIC);
			GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCLASSIC, anchorPane);
		} else if (table instanceof TableCombo)
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.COMBO);
			//TODO
		} else
		{
			GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.TRADE);
			//TODO
		}
		new Thread(table).start();
	}

	public void load(Table currentTable)
	{
		table = currentTable;

		int lvl = USER.getLevel();
		actualLevel.setText("" + lvl);
		nextLevel.setText("" + ++lvl);

		avatar.setFill(new ImagePattern(new Image(String.format(USER.getAvatar()))));
		progressBar.setProgress(USER.getProgress());


		int points = currentTable.getUser().getPoints();

		expMessage.setText(String.format("You gained %d exp!", points));


		if (!currentTable.getStopEarlier() && currentTable.getWinner().getId() == BuildMP.PG.PLAYER)
			USER.addVictories();
		else
			USER.addDefeats();
		

		USER.addExp(points);

		Platform.runLater(this::animation);
	}

	public void animation()
	{

		Timeline timeline = new Timeline();

		KeyValue keyValue = new KeyValue(progressBar.progressProperty(), USER.getProgress());
		KeyFrame keyFrame = new KeyFrame(new Duration(3000), keyValue);
		timeline.getKeyFrames().add(keyFrame);

		timeline.play();

	}
}
