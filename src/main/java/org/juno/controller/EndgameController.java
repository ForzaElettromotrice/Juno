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
import org.juno.model.user.User;
import org.juno.view.GenView;
import org.juno.view.NonexistingSceneException;


/**
 * Defines EndgameController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class EndgameController
{

	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final Table TABLE = Table.getINSTANCE();
	private static final User USER = User.getINSTANCE();
	private static final TurnOrder TURN_ORDER = TurnOrder.getINSTANCE();

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
	public void mainMenu() throws NonexistingSceneException
	{
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchorPane);
	}


	@FXML
	public void newMatch() throws NonexistingSceneException
	{
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAY, anchorPane);
		new Thread(TABLE).start();

	}


	public void load()
	{
		int lvl = USER.getLevel();
		actualLevel.setText("" + lvl);
		nextLevel.setText("" + ++lvl);

		avatar.setFill(new ImagePattern(new Image(String.format("file:\\%s\\%s", System.getProperty("user.dir"), USER.getAvatar()))));
		progressBar.setProgress(USER.getProgress());


		int points = TURN_ORDER.getUser().getPoints();

		expMessage.setText(String.format("You gained %d exp!", points));


		if (!TABLE.getStopEarlier() && TABLE.getWinner().getID() == BuildMP.PG.PLAYER)
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
