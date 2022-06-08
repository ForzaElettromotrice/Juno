package org.juno;

import javafx.application.Application;
import javafx.stage.Stage;
import org.juno.view.GenView;

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
		stage.setScene(GenView.getStartMenu());
		stage.setTitle("JUno");
		stage.show();
	}
}
