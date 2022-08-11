package org.juno.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.juno.model.table.TurnOrder;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.table.trade.TableTrade;
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
	private static final TableClassic TABLE_CLASSIC = TableClassic.getINSTANCE();
	private static final TableCombo TABLE_COMBO = TableCombo.getINSTANCE();
	private static final TableTrade TABLE_TRADE = TableTrade.getINSTANCE();


	@FXML
	public AnchorPane anchor;

	@FXML
	public void classicEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}
	@FXML
	public void comboEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}
	@FXML
	public void tradeEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}


	@FXML
	public void classicExited()
	{
	}
	@FXML
	public void comboExited()
	{

	}
	@FXML
	public void tradeExited()
	{

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
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.MENUMUSIC);

		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.GAMEMUSIC);

		Thread thread = new Thread(TABLE_CLASSIC);
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCLASSIC, anchor);

		GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.CLASSIC);
		thread.start();
	}
	@FXML
	public void comboClicked()
	{
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.MENUMUSIC);

		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.GAMEMUSIC);

		Thread thread = new Thread(TABLE_COMBO);
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYCOMBO, anchor);

		GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.COMBO);
		thread.start();
	}
	@FXML
	public void tradeClicked()
	{
		AUDIO_PLAYER.stop(AudioPlayer.Sounds.MENUMUSIC);

		AUDIO_PLAYER.play(AudioPlayer.Sounds.BUTTONCLICK);
		AUDIO_PLAYER.play(AudioPlayer.Sounds.GAMEMUSIC);

		Thread thread = new Thread(TABLE_TRADE);
		GEN_VIEW.changeScene(GenView.SCENES.GAMEPLAYTRADE, anchor);

		GEN_VIEW.setCurrentGameController(TurnOrder.MODALITY.TRADE);
		thread.start();
	}

	@FXML
	public void buttonEntered()
	{
		AUDIO_PLAYER.play(AudioPlayer.Sounds.CURSORSELECT);
	}
}
