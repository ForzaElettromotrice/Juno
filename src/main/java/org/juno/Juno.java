package org.juno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.model.user.DataCorruptedException;
import org.juno.model.user.User;
import org.juno.view.GenView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
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
	public void start(Stage stage) throws IOException
	{
		stage.setResizable(false);
		
		GenView.setWindow(stage);
		GenView.load();
		try
		{
			User.getINSTANCE().load();
			stage.setScene(GenView.getStartMenu());
		} catch (DataCorruptedException e)
		{
			stage.setScene(GenView.getLogin());
		}
		stage.setTitle("JUno");
		stage.getIcons().add(new Image(Objects.requireNonNull(Juno.class.getResourceAsStream("images/icon.png"))));
		stage.show();
	}
}
