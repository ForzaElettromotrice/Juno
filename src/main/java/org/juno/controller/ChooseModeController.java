package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;
import org.juno.model.table.combo.TableCombo;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;

/**
 * Defines ChooseModeController ,
 *
 * @author ForzaElettromotrice, R0n3l
 */
public class ChooseModeController
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	private static final Table TABLE = Table.getINSTANCE();
	private static final TableCombo TABLE_COMBO = TableCombo.getINSTANCE();


	@FXML
	public AnchorPane anchor;


	@FXML
	public HBox classicHBox;
	@FXML
	public HBox comboHBox;
	@FXML
	public HBox tradeHBox;


	@FXML
	public void classicEntered()
	{
		classicHBox.setVisible(true);
	}
	@FXML
	public void comboEntered()
	{
		comboHBox.setVisible(true);
	}
	@FXML
	public void tradeEntered()
	{
		tradeHBox.setVisible(true);
	}


	@FXML
	public void classicExited()
	{
		classicHBox.setVisible(false);
	}
	@FXML
	public void comboExited()
	{
		comboHBox.setVisible(false);
	}
	@FXML
	public void tradeExited()
	{
		tradeHBox.setVisible(false);
	}


	@FXML
	public void backClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		GEN_VIEW.changeScene(GenView.SCENES.STARTMENU, anchor);
	}
	@FXML
	public void classicClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		Thread thread = new Thread(TABLE);
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCLASSIC, anchor);

		GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.CLASSIC);
		thread.start();
	}
	@FXML
	public void comboClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		//TODO
	}
	@FXML
	public void tradeClicked()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		// TODO
	}
}
