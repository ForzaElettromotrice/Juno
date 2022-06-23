package org.juno;

import javafx.application.Application;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.model.table.Player;
import org.juno.model.table.Table;
import org.juno.model.table.TurnOrder;
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
	public static void main(String[] args)
	{
		launch(args);
	}
	@Override
	public void start(Stage stage) throws IOException, NotExistingSoundException
	{
		stage.setResizable(false);
		GenView.setWindow(stage);
		boolean login;
		try
		{
			User.getINSTANCE().load();
			login = false;

		} catch (DataCorruptedException e)
		{
			login = true;
		}

		GenView.load();

		stage.setScene(login ? GenView.getLogin() : GenView.getStartMenu());

		stage.setTitle("JUno");
		stage.getIcons().add(new Image(Objects.requireNonNull(Juno.class.getResourceAsStream("images/logo.png"))));

		Table.getINSTANCE().addObserver(GenView.getINSTANCE());
		for (Player player : TurnOrder.getINSTANCE().getPlayers())
		{
			player.addObserver(GenView.getINSTANCE());
		}
		AudioPlayer.getINSTANCE().play(AudioPlayer.Sounds.MENUMUSIC);

		stage.setOnCloseRequest(x -> User.getINSTANCE().save());

		stage.show();


	}
}
