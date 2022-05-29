package org.juno;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


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
	public void start(Stage stage)
	{
		stage.setTitle("Prova");

		Button button = new Button();
		button.setText("Test");

		StackPane layout = new StackPane();
		layout.getChildren().add(button);

		stage.setScene(new Scene(layout, 500, 500));
		stage.show();

	}
}
