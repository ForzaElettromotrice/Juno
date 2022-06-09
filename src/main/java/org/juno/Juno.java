package org.juno;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.juno.view.GenView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;


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
		GenView.setWindow(stage);
		GenView.load();
		try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/org/juno/model/user/user.txt")))
		{
			if (br.readLine() == null)
				stage.setScene(GenView.getLogin());
			else
				stage.setScene(GenView.getStartMenu());
		}
		stage.setTitle("JUno");
		stage.getIcons().add(new Image(Juno.class.getResourceAsStream("images/icon.png")));
		stage.show();
	}
}
