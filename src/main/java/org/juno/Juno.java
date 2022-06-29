package org.juno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.user.DataCorruptedException;
import org.juno.model.user.User;
import org.juno.view.AudioPlayer;
import org.juno.view.GenView;
import org.juno.view.NotExistingSoundException;


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


	public static void main(String[] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage stage) throws IOException, NotExistingSoundException
	{
		stage.setResizable(false);
		GEN_VIEW.setWindow(stage);
		boolean login;
		try
		{
			User.getINSTANCE().load();
			login = false;

		} catch (DataCorruptedException e)
		{
			login = true;
		}

		GEN_VIEW.load();

		stage.setScene(login ? GEN_VIEW.getLogin() : GEN_VIEW.getStartMenu());

		stage.setTitle("JUno");
		stage.getIcons().add(new Image(Objects.requireNonNull(Juno.class.getResourceAsStream("images/logo.png"))));

		Table.getINSTANCE().addObserver(GenView.getINSTANCE());
		for (Player player : Table.getINSTANCE().getPlayers())
		{
			player.addObserver(GenView.getINSTANCE());
		}
		AudioPlayer.getINSTANCE().play(AudioPlayer.Sounds.MENUMUSIC);

		stage.setOnCloseRequest(x -> User.getINSTANCE().save());

		stage.show();

	}
}
