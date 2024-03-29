package org.juno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.controller.LoginController;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.table.trade.TableTrade;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;


import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;


/**
 * Defines: Juno class,
 *
 * @author ForzaElettromotrice
 */
public class Juno extends Application
{
	private static final GenView GEN_VIEW = GenView.getINSTANCE();
	private static final AudioPlayer AUDIO_PLAYER = AudioPlayer.getINSTANCE();
	private static final TableClassic TABLE_CLASSIC = TableClassic.getINSTANCE();
	private static final TableCombo TABLE_COMBO = TableCombo.getINSTANCE();
	private static final TableTrade TABLE_TRADE = TableTrade.getINSTANCE();

	@Override
	public void start(Stage stage)
	{
		//default stuff
		stage.setTitle("JUno");
		stage.getIcons().add(new Image(Objects.requireNonNull(Juno.class.getResourceAsStream("images/icona.png"))));
		stage.setResizable(false);
		stage.setOnCloseRequest(x -> GEN_VIEW.closeWindow());


		//initializing GenView
		GEN_VIEW.setWindow(stage);
		try
		{
			GEN_VIEW.load();
		} catch (IOException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
			return;
		}

		AUDIO_PLAYER.load();

		GEN_VIEW.changeScene(GenView.SCENES.LOGIN, null);
		((LoginController) GEN_VIEW.getLogin().getUserData()).load();


		//adding the observers
		//CLASSIC
		TABLE_CLASSIC.addObserver(GEN_VIEW);

		Arrays.stream(TABLE_CLASSIC.getPlayers()).forEach(player -> player.addObserver(GEN_VIEW));


		//COMBO
		TABLE_COMBO.addObserver(GEN_VIEW);

		Arrays.stream(TABLE_COMBO.getPlayers()).forEach(player -> player.addObserver(GEN_VIEW));


		//TRADE
		TABLE_TRADE.addObserver(GEN_VIEW);

		Arrays.stream(TABLE_TRADE.getPlayers()).forEach(player -> player.addObserver(GEN_VIEW));


		//Starting the music!
		AUDIO_PLAYER.play(AudioPlayer.Sounds.LOGINMUSIC);


		stage.show();

	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
