package org.juno.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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

		expMessage.setText(String.format("You gained %d exp!", TURN_ORDER.getUser().getPoints()));

		animation();
	}

	public void animation()
	{
		//TODO: animazione dell'exp che sale
	}
}
