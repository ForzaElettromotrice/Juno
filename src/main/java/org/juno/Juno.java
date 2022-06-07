package org.juno;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(Juno.class.getResource("startMenu.fxml"));
		Scene scene = new Scene(loader.load());
		stage.setTitle("JUno");
		stage.setScene(scene);
		stage.show();
	}
}
