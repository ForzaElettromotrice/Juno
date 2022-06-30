package org.juno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.classic.TableClassic;
import org.juno.model.table.combo.TableCombo;
import org.juno.model.table.trade.TableTrade;
import org.juno.model.user.DataCorruptedException;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;


import java.io.IOException;
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
		stage.getIcons().add(new Image(Objects.requireNonNull(Juno.class.getResourceAsStream("images/logo.png"))));
		stage.setResizable(false);
		stage.setOnCloseRequest(x -> User.getINSTANCE().save());


		//initializing GenView
		GEN_VIEW.setWindow(stage);
		try
		{
			GEN_VIEW.load();
		} catch (IOException err)
		{
			System.out.println(err.getMessage());
			err.printStackTrace();
			System.out.println("GAME ABORTED");
			return;
		}

		boolean login;

		try
		{
			User.getINSTANCE().load();
			login = false;

		} catch (DataCorruptedException e)
		{
			login = true;
		}

		GEN_VIEW.changeScene(login ? GenView.SCENES.LOGIN : GenView.SCENES.STARTMENU, null);


		//adding the observers
		//CLASSIC
		TABLE_CLASSIC.addObserver(GEN_VIEW);

		for (Player player : TABLE_CLASSIC.getPlayers())
		{
			player.addObserver(GEN_VIEW);
		}


		//COMBO
		for (Player player : TABLE_COMBO.getPlayers())
		{
			player.addObserver(GEN_VIEW);
		}


		//TRADE
		for (Player player : TABLE_TRADE.getPlayers())
		{
			player.addObserver(GEN_VIEW);
		}


		//Loading and starting the music!
		AUDIO_PLAYER.load();
		AUDIO_PLAYER.play(AudioPlayer.Sounds.MENUMUSIC);


		stage.show();

	}


	public static void main(String[] args)
	{
		launch(args);
	}
}
